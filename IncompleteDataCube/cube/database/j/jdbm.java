// jdbm.java
// $Id: jdbm.java,v 1.4 1996/09/27 16:52:36 abaird Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

//package w3c.tools.dbm ;

import java.io.* ;
import java.util.*;

/**
 * A single entry in the LRU list for buckets.
 */

class LRUEntry {
    jdbmBucket bucket = null ;
    LRUEntry   prev   = null ;
    LRUEntry   next   = null ;
 
    LRUEntry (jdbmBucket bucket) {
	this.bucket = bucket ;
    }
}

/**
 * List of loaded buckets.
 */

class LRUList {
    LRUEntry head = null ;
    LRUEntry tail = null ;
    
    synchronized void removeEntry (LRUEntry lru) {
	if ( lru == head ) {
	    head     = lru.next ;
	    lru.next = null ;
	    lru.prev = null ;
	    if ( tail == lru )
		tail = null ;
	} else if ( lru == tail ) {
	    tail      = lru.prev ;
	    tail.next = null ;
	    lru.next  = null ;
	    lru.prev  = null ;
	} else {
	    lru.prev.next = lru.next ;
	    lru.next.prev = lru.prev ;
	    lru.next      = null ;
	    lru.prev      = null ;
	}
    }

    private final synchronized void atTop (LRUEntry lru) {
	// Is it already at top ?
	if ( lru == head )
	    return ;
	// Update:
	removeEntry (lru) ;
	lru.next  = head ;
	lru.prev  = null ;
	head.prev = lru ;
	head      = lru ;
	return ;
    }

    protected final void notifyUses (LRUEntry lru) {
	// Bring it to the front of the queue:
	atTop (lru) ;
    }

    protected LRUEntry getLRU() {
	LRUEntry lru = tail ;
	if ( lru != null )
	    removeEntry (lru) ;
	return lru ;
    }

    protected synchronized LRUEntry addEntry (jdbmBucket bucket) {
	LRUEntry lru = new LRUEntry (bucket) ;
	// Add it to the head of the list:
	lru.next  = head ;
	if ( head != null )
	    head.prev = lru ;
	lru.prev  = null ;
	head      = lru ;
	if ( tail == null )
	    tail = head ;
	return lru ;
    }
    
    protected synchronized void removeBucket(jdbmBucket bucket) {
	LRUEntry lru = head ;
	while (lru != null) {
	    if (lru.bucket == bucket) {
		removeEntry(lru) ;
		return ;
	    }
	    lru = lru.next ;
	}
    }

    protected synchronized LRUEntry lookupBucket(int fileptr) {
	LRUEntry lru = head ;
	while ( lru != null ) {
	    if ( lru.bucket.fileptr == fileptr )
		return lru ;
	    lru = lru.next ;
	}
	return null ;
    }
    
    protected synchronized void saveModified(jdbm db)
	throws  IOException
    {
	LRUEntry lru = head ;
	while ( lru != null ) {
	    if ( lru.bucket.modified )
		db.saveBucket(lru.bucket) ;
	    lru = lru.next ;
	}
    }


    LRUList () {
	this.head = null ;
	this.tail = null ;
    }
}

/**
 * An dbm like database in Java.
 * This database is a transcription of what I have understood of gdbm into
 * Java. Some of the code here I don't understand, but it looks like it works.
 */

public class jdbm {
    private static final int     IGNORE_SIZE = 8 ;
    private static final boolean debug       = false ;
    /**
     * Mode for store - Replace any existing entry with the new provided one.
     */
    public final static int STORE_REPLACE = 1 ;
    /**
     * Mode for store - Only insert this element if it is not already defined.
     */
    public final static int STORE_INSERT = 2 ;
    /*
     * Default block size for this database.
     */
    protected final static int BLOCK_SIZE = 1024 ;
    /**
     * Default directory bits for this database.
     */
    protected final static int DIR_BITS = 3 ;
    /**
     * Default cache size.
     */
    protected final static int CACHE_SIZE = 32 ;
    /**
     * Size of the database header, when dumped to disk.
     */
    private final static int fsize = (4		// block size
				      + 4	// dir bits
				      + 4	// dir size
				      + 4	// dir adr
				      + 4	// cache size
				      + 4	// bucket elems
				      + 4	// next block
				      + 4	// avail length
				      + 4);	// avail count
    /**
     * The block size to manage this file.
     */
    int block_size = BLOCK_SIZE ;
    /**
     * Number of directory bits for this database.
     */
    int dir_bits = DIR_BITS ;
    /**
     * Directory size.
     */
    int dir_size = 0 ;
    /**
     * Directory address.
     */
    int dir_adr = 0 ;
    /**
     * Cache size for this database.
     */
    int cache_size = CACHE_SIZE ;
    /**
     * Number of elements per bucket.
     */
    int bucket_elems = 0 ;
    /**
     * Next block to be allocated for the file.
     */
    int next_block = 0 ;
    /**
     * Available block count.
     */
    int avail_count = 0 ;
    /**
     * Available block length.
     */
    int avail_length = 0 ;
    /**
     * Available block sizes.
     */
    int avail_size[] = null ;
    /**
     * Available bloc file pointers.
     */
    int avail_ptr[] = null ;
    /**
     * The DBM file name.
     */
    File file = null ;
    /**
     * The handle to the file.
     */
    RandomAccessFile fd   = null ;
    /**
     * The directory index, maps directory hash values to buckets file adr.
     */
    int diridx[] = null ;
    /**
     * IO buffer, for all read/write operations.
     */
    private byte buffer[] = null;
    /**
     * Has the directory been changed recently ?
     */
    private boolean dir_changed = false ;
    /**
     * Has the header changed recently ?
     */
    private boolean header_changed = false ;
    /**
     * List of loaded buckets.
     */
    private LRUList list = null ;
    /**
     * Number of loaded buckets.
     */
    private int loaded_buckets = 0 ;

    protected final void trace(String msg) {
	if ( debug )
	    System.out.println("jdbm: "+msg) ;
    }

    /**
     * This funny hash function has been stolen from gdbm implementation.
     * @param key The byte array to hash.
     * @return A 31 bit hash value for the given byte array.
     */

    private final static int hash(byte key[]) {
	int value = 0x238F13AF * key.length ;
	for (int i = 0 ; i < key.length ; i++) 
	    value = (value + (((int) key[i]) << (i*5 % 24))) & 0x7fffffff ;
	return (1103515243 * value + 12345) & 0x7FFFFFFF;
    }

    /**
     * Split a given bucket, whose content is too big.
     */

    private void splitBucket (int hashval, jdbmBucket bucket) 
	throws IOException
    {
	jdbmBucket select = null ;
trace("split bucket: " + bucket.fileptr) ;
	while (bucket.count == bucket_elems) {
	    // Remove bucket to be split from LRU list, and free the bucket:
	    list.removeBucket(bucket) ;
	    loaded_buckets-- ;
	    markAvailable(bucket.fileptr, block_size) ;
	    // Get two new buckets (should be allocated through the cache):
	    int a0 = allocateSpace(block_size) ;
	    int a1 = allocateSpace(block_size) ;
	    jdbmBucket b0   = new jdbmBucket(this, a0, -1) ;
	    jdbmBucket b1   = new jdbmBucket(this, a1, -1) ;
	    LRUEntry   lru0 = list.addEntry(b0) ;
	    LRUEntry   lru1 = list.addEntry(b1) ;
trace("splited b0="+a0) ;
trace("splited b1="+a1) ;
	    loaded_buckets += 2 ;
	    // Compute new bits, split the bucket:
	    int newbits = bucket.bits + 1 ;
	    b0.bits = newbits ;
	    b1.bits = newbits ;
	    // Double the directory if necessary:
	    if ( dir_bits == bucket.bits ) {
		dir_size <<= 1 ;
		dir_adr    = allocateSpace(dir_size*4) ;
		int      ndiridx[] = new int[dir_size] ;
		for (int i = 0 ; i < (dir_size/2) ; i++) {
		    ndiridx[2*i]   = diridx[i] ;
		    ndiridx[2*i+1] = diridx[i] ;
		}
		diridx      = ndiridx ;
		dir_bits    = newbits ;
		dir_changed = true ;
	    }
	    // Re-hash the splited bucket elements:
	    for (int i = 0 ; i < bucket_elems ; i++) {
		jdbmBucketElement el = bucket.elements[i] ;
		int nsel = (el.hashval >> (31-newbits)) & 1 ;
		int nloc = el.hashval % bucket_elems ;
		select   = (nsel == 0) ? b0 : b1 ;
		while (select.elements[nloc].hashval != -1)
		    nloc = (nloc+1) % bucket_elems ;
		select.elements[nloc] = el ;
		select.count++ ;
	    }
	    // Save the splited bucket free list:
	    b1.avail_count = bucket.avail_count ;
	    b1.avail_size  = bucket.avail_size ;
	    b1.avail_ptr   = bucket.avail_ptr ;
	    // Update the directory:
	    int dir_idx    = (hashval >>> (31-dir_bits)) ;
	    int dir_start1 = (dir_idx >> dir_bits - newbits) | 1 ;
	    int dir_end    = (dir_start1+1) << (dir_bits-newbits) ;
	    dir_start1     = (dir_start1 << (dir_bits - newbits)) ;
	    int dir_start0 = dir_start1 - (dir_end - dir_start1) ;
trace("updating dir from "+dir_start0+" to "+dir_start1) ;
	    for (int i = dir_start0 ; i < dir_start1 ; i++) {
		diridx[i] = a0 ;
	    }
trace("updating dir from "+dir_start1+" to "+dir_end) ;
	    for (int i = dir_start1 ; i < dir_end ; i++) {
		diridx[i] = a1 ;
	    }
	    b0.modified = true ;
	    b1.modified = true ;
	    dir_changed = true ;
	    // Saved the newly created bucket:
	    saveBucket(b0) ;
	    saveBucket(b1) ;
	    // Try the newly splited bucket:
	    bucket = lookupBucket(hashval) ;
	}
    }

    /**
     * Save the database header into the provided buffer.
     * @param out The data output stream to save the header to.
     * @exception IOException If some IO error occured.
     */

    private void saveHeader (DataOutputStream out) 
	throws IOException
    {
	out.writeInt(block_size) ;
	out.writeInt(dir_bits) ;
	out.writeInt(dir_size) ;
	out.writeInt(dir_adr) ;
	out.writeInt(cache_size) ;
	out.writeInt(bucket_elems) ;
	out.writeInt(next_block) ;
	out.writeInt(avail_length) ;
	out.writeInt(avail_count) ;
	for (int i = 0 ; i < avail_length ; i++) {
	    out.writeInt(avail_size[i]) ;
	    out.writeInt(avail_ptr[i]) ;
	}
    }
	
    /**
     * Restore this database header from the given stream.
     * @param in The data input stream to restore header from.
     * @exception IOException If some IO Error occurs.
     */

    private void restoreHeader (DataInputStream in) 
	throws IOException
    {
	this.block_size   = in.readInt() ;
	this.dir_bits     = in.readInt() ;
	this.dir_size     = in.readInt() ;
	this.dir_adr      = in.readInt() ;
	this.cache_size   = in.readInt() ;
	this.bucket_elems = in.readInt() ;
	this.next_block   = in.readInt() ;
	this.avail_length = in.readInt() ;
	this.avail_count  = in.readInt() ;
	this.avail_size   = new int[avail_length] ;
	this.avail_ptr    = new int[avail_length] ;
	for (int i = 0 ; i < avail_length ; i++) {
	    avail_size[i] = in.readInt() ;
	    avail_ptr[i]  = in.readInt() ;
	}
    }

    /**
     * Print various database options to the given stream.
     * @param out The PrintStream to display info to.
     */

    public void printHeader(PrintStream out) {
	out.println("Options for "+file.getAbsolutePath()+":") ;
	out.println("\tblock_size   = "+block_size) ;
	out.println("\tdir_bits     = "+dir_bits) ;
	out.println("\tdir_size     = "+dir_size) ;
	out.println("\tdir_adr      = "+dir_adr) ;
	out.println("\tcache_size   = "+cache_size) ;
	out.println("\tdir_size     = "+(1<<dir_bits));
	out.println("\tbucket_elems = "+bucket_elems) ;
	out.println("\tnext_block   = "+next_block);
	out.println("\tavail_count  = "+avail_count) ;
	out.println("\tavail_length = "+avail_length);
    }

    /**
     * Print the list of available blocks:
     * @param out The print stream to print to.
     */

    public void printAvail(PrintStream out) {
	out.println("avail_count="+avail_count+"/"+avail_size.length) ;
	for (int i = 0 ; i < avail_count ; i++) 
	    out.println("\tsize="+avail_size[i]+" ,ptr="+avail_ptr[i]) ;
    }

    /**
     * Save a bucket back to the DBF file.
     * @param bucket The bucket to save.
     * @exception IOException If some IO error occured.
     */

    void saveBucket(jdbmBucket bucket)
	throws IOException
    {
	DataOutputStream out = (new DataOutputStream
				(new FastByteArrayOutputStream(buffer))) ;
	bucket.save(out) ;
	fd.seek(bucket.fileptr) ;
	fd.write(buffer) ;
    }
    
    /**
     * Save this database directory.
     * @param out The output stream to save the directory to.
     * @exception IOException If some IO error occured.
     */

    private void saveDirectory(DataOutputStream out) 
	throws IOException
    {
	for (int i = 0 ; i < diridx.length ; i++)
	    out.writeInt(diridx[i]) ;
    }

    /**
     * Restore this database directory.
     * @param in The stream to reastore the directory from.
     * @exception IOException If some IO error occured.
     */

    private void restoreDirectory(DataInputStream in)
	throws IOException
    {
	this.diridx = new int[block_size/4] ;
	for (int i = 0 ; i < diridx.length ; i++)
	    diridx[i] = in.readInt() ;
    }

    /**
     * Mark the given item to be available for this bucket.
     * It is up to the caller to make sure that the bucket avail list size
     * will not be exceeded by this new item. Items are kept sorted by size
     * in the avail_list.
     * @param ptr The item file pointer.
     * @param size The item size.
     */

    void markAvailable(int ptr, int size) {
	// Some data space will indeed leak here, the db should be reorganized
	// Fix suggested by Glen Diener <grd@atg.andor.com>
	if ( avail_count + 1 >= avail_size.length )
	    return;
	header_changed = true ;
	// Keep the list sorted:
	for (int i = 0 ; i < avail_count ; i++) {
	    if ( avail_size[i] >= size ) {
		// There we are:
		System.arraycopy(avail_size,i,avail_size,i+1,avail_count-i);
		System.arraycopy(avail_ptr,i,avail_ptr,i+1,avail_count-i);
		avail_count++ ;
		avail_size[i] = size ;
		avail_ptr[i]  = ptr ;
		return ;
	    }		
	}
	// Wow our biggest avail item
	avail_size[avail_count] = size ;
	avail_ptr[avail_count]  = ptr ;
	avail_count++;
    }
    /**
     * Remove the avail block at given index from our avail list.
     * @param idx The index of the avail block to remove.
     */

    final void removeAvailable(int idx) {
	header_changed = true ;
	avail_count-- ;
	// The last one is easily thrown away:
	if (idx == avail_count) 
	    return ;
	// Displace all avail, etc.
	System.arraycopy(avail_size, idx+1, avail_size, idx, avail_count-idx);
	System.arraycopy(avail_ptr, idx+1, avail_ptr, idx, avail_count-idx) ;
    }

    /**
     * Fix an available block to fit the requested size.
     * @param idx The index of thenewly allocated avail.
     * @param size The size we were requested to allocate.
     * @return The fileptr of the allocated avail block.
     */

    int fixAvailable(int idx, int size) {
	header_changed = true ;
	int fileptr = avail_ptr[idx] ;
	int nsize = avail_size[idx] -= size ;
	int nptr  = avail_ptr[idx]  += size ;
	removeAvailable(idx) ;
	// Ignore remaining bytes if too small
	if ( nsize <= IGNORE_SIZE ) 
	    return fileptr ;
	// This is big, keep it:
	markAvailable (nptr, nsize) ;
	return fileptr ;
    }


    /**
     * Allocate space in the file.
     * @param size The size of the file block we want.
     * @return The file pointer to the allocated block.
     */

    protected int allocateSpace (int size) {
	header_changed = true ;
trace("allocateSpace: avail_count="+avail_count) ;
	// Look in our own avail list:
	for (int i = 0 ; i < avail_count ; i++) {
	    if ( avail_size[i] >= size )
		return fixAvailable(i, size) ;
	}
	// Second stage: allocate a new block:
	int newblock = next_block++ ;
	int fileptr  = newblock * block_size ;
	// modified to allow allocation when size > 1024  grd 7.24.96
	int totalAllocation ;
	while((totalAllocation=((next_block - newblock) * block_size)) < size)
	    next_block++ ;
	int spaceLeftOver = totalAllocation - size ;
	if(spaceLeftOver >= IGNORE_SIZE)
	    markAvailable(fileptr+size, spaceLeftOver) ;
	/* original code follows:
	 * if ( block_size - size >= IGNORE_SIZE )
	 *    markAvailable(fileptr+size, block_size-size) ; 
	 */
	return fileptr ;
    }

    /**
     * Allocate and write the provided key/value.
     * This is a short end for writing new associations to the file.
     * @param bucket Suggested bucket to allocate data from.
     * @param key The key part of the item to write.
     * @param data The data part of the item to write.
     * @return The file position of the written element.
     */

    protected int write (jdbmBucket bucket, byte key[], byte data[]) 
	throws IOException
    {
	int size    = key.length+data.length ;
	int fileptr = bucket.allocateSpace(size) ;
	if ( fileptr < 0 )
	    // No more space in suggested bucket, try our own pool
	    fileptr = allocateSpace(size) ;
trace("write: @"+fileptr) ;
	fd.seek(fileptr) ;
	fd.write(key) ;
	fd.write(data) ;
	return fileptr ;
    }

    /**
     * Read the key part of the given bucket element.
     * @param el The lement whose key is to be read.
     */

    byte[] readKey (jdbmBucketElement el)
	throws IOException
    {
trace("read: @"+el.fileptr) ;
	byte key[] = new byte[el.key_size] ;
	fd.seek(el.fileptr) ;
	if (fd.read(key) != el.key_size) 
	    throw new RuntimeException ("invalid key read.") ;
	return key ;
    }

    /**
     * Read the data part of the given bucket element.
     * @param el The element whose data part is to be read.
     */

    byte[] readData(jdbmBucketElement el) 
	throws IOException
    {
	byte data[] = new byte[el.data_size] ;
	fd.seek(el.fileptr+el.key_size) ;
	if (fd.read(data) != el.data_size)
	    throw new RuntimeException ("invalid data read.") ;
	return data ;
    }

    /**
     * Select a bucket to be removed from the cache, unload it.
     * @return A bucket ready to be reused.
     */

    protected synchronized jdbmBucket unloadBucket()
	throws IOException
    {
	LRUEntry lru = list.getLRU() ;
	if ( lru == null )
	    return null ;
	jdbmBucket bucket = lru.bucket ;
	if ( bucket.modified ) 
	    saveBucket(bucket) ;
	// Remove it from directory cache:
	loaded_buckets-- ;
	return bucket ;
    }

    /**
     * Load the bucket whose file pointer is given.
     * @param fileptr The file pointer of the bucket to be loaded.
     * @param into The bucket to resue (or <strong>null</strong>).
     */

    protected synchronized LRUEntry loadBucket(int fileptr)
	throws IOException
    {
	jdbmBucket bucket = null ;
	// Should we remove an entry from the cache:
	if ( loaded_buckets >= cache_size ) {
trace("*** removing bucket from cache !") ;
	    bucket = unloadBucket() ;
	} else {
trace("*** filling cache.") ;
	    loaded_buckets++ ;
	    bucket = new jdbmBucket(this, fileptr, -1) ;
	}
	// Seek to the appropriate location, and restore:
	fd.seek((long) fileptr) ;
	if (fd.read(buffer, 0, buffer.length) != buffer.length) 
	    throw new IOException ("invalid read length.") ;
	jdbmBucket.restore(new DataInputStream
			   (new ByteArrayInputStream(buffer))
			   , fileptr
			   , bucket) ;
	// Put this bucket in our cache, and return it:
	return list.addEntry(bucket) ;
    }

    /**
     * Read in the bucket for the provided hash value.
     * If the bucket is already loaded in the cache, mark it as the most
     * recently used, otherwise, load it from disk.
     * @param hashval The hash value for the bucket to locate.
     */

    private synchronized jdbmBucket lookupBucket(int hashval)
	throws IOException
    {
	int didx = (hashval >>> (31 - dir_bits)) ;
	int fptr = diridx[didx] ;
	int dptr = fptr / block_size ;

	LRUEntry lru  = list.lookupBucket(fptr) ;
	// Lookup the bucket cache
	if ( lru == null ) 
	    lru = loadBucket(fptr) ;
	list.notifyUses (lru) ;
	return lru.bucket ;
    }

    /**
     * Store the given association of key/value.
     * @param key The bytes that makes the key.
     * @param value The bytes that makes the value.
     * @param mode The mode of the storing, can be...
     */

    public void store (byte key[], byte value[], int mode) 
	throws IOException
    {
	int               hashval = hash(key) ;
	jdbmBucket        bucket  = lookupBucket(hashval) ;
	jdbmBucketElement el      = bucket.lookup(key, hashval) ;

	// Check if this association already exists.
	if ( el != null ) {
	    if (mode == STORE_REPLACE) 
		bucket.delete (el) ;
	    else
		return ;
	}
	// Add it:
	if ( bucket.count == bucket_elems ) {
	    splitBucket(hashval, bucket) ;
	    bucket = lookupBucket(hashval) ;
	}
	bucket.add (hashval, key, value) ;
    }

    /**
     * Lookup the value associated with the provided key.
     * @param key The bits of the key to look for.
     * @return The bits that makes the associated value, or 
     *    <strong>null</strong> if not found.
     */

    public byte[] lookup(byte key[])
	throws IOException
    {
	int               hashval = hash(key) ;
	jdbmBucket        bucket  = lookupBucket(hashval) ;
	jdbmBucketElement el      = bucket.lookup(key, hashval) ;
	return (el != null) ? readData(el) : null ;
    }

    /**
     * Lookup the value associated to the given String key.
     * @param key The string that we are looking for.
     * @return The bits that makes the associated value, or 
     *    <strong>null</strong> if not found.
     */

    public byte[] lookup(String key) 
	throws IOException
    {
	// FIXME: the min is that the buffer is not allocated on each lookup
	byte b[] = new byte[key.length()] ;
	key.getBytes(0, b.length, b, 0) ;
	return lookup(b) ;
    }

    /**
     * Delete the association for the provided key.
     * @param key The key of the element to remove.
     * @return A boolean <strong>true</strong> if deletion was succesfull.
     */

    public boolean delete(byte key[]) 
	throws IOException
    {
	int               hashval = hash(key) ;
	jdbmBucket        bucket  = lookupBucket(hashval) ;
	jdbmBucketElement el      = bucket.lookup(key, hashval) ;
	
	if ( el != null ) {
	    bucket.delete(el) ;
	    return true ;
	}
	return false ;
    }

    /**
     * Delete the association for the provided String key.
     * @param key The key of the element to remove.
     * @return A boolean <strong>true</strong> if deletion was succesfull.
     */

    public boolean delete(String key) 
	throws IOException
    {
	// FIXME: the min is that the buffer is not allocated on each lookup
	byte b[] = new byte[key.length()] ;
	key.getBytes(0, b.length, b, 0) ;
	return delete(b) ;
    }

    /**
     * Save thisdatabase to disk.
     */

    public void save() 
	throws IOException
    {
	// Write the header if needed:
	if ( header_changed ) {
trace ("saving header.") ;
	    DataOutputStream out = (new DataOutputStream
				    (new FastByteArrayOutputStream(buffer))) ;
	    saveHeader(out) ;
	    fd.seek(0) ;
	    fd.write(buffer) ;
	    header_changed = false ;
	}
	// Write the directory if needed:
	if ( dir_changed ) {
trace ("saving directory.") ;
	    DataOutputStream out = (new DataOutputStream
				    (new FastByteArrayOutputStream(buffer))); 
	    saveDirectory(out) ;
	    fd.seek(dir_adr) ;
	    fd.write(buffer) ;
	    dir_changed = false ;
	}
	// Write any modified bucket
	list.saveModified(this) ;
    }

    public jdbm (String name) 
	throws IOException
    {
        File file = new File(name);

	boolean exists = file.exists() ;
	// Open the file, and write options:
	this.file   = file ;
	this.fd     = new RandomAccessFile(file, "rw") ;
	this.buffer = new byte[block_size] ;
	this.list   = new LRUList() ;
	if ( exists ) {
	    // Restore the data base state:
	    // Restore its header infos:
	    fd.seek(0) ;
	    if (fd.read(buffer) != buffer.length) 
		throw new IOException("unable to restore DB header.") ;
	    restoreHeader(new DataInputStream
			  (new ByteArrayInputStream(buffer)));
	    // Restore the directory:
	    fd.seek(dir_adr) ;
	    if (fd.read(buffer) != buffer.length) 
		throw new IOException("unable to restore DB directory.");
	    restoreDirectory(new DataInputStream
			     (new ByteArrayInputStream(buffer))) ;
	    // Initialize other fields:
	    int dir_size        = (1<<dir_bits) ;
	    this.loaded_buckets = 0 ;
	} else {
	    // Create a new DBM file
	    this.block_size   = BLOCK_SIZE ;
	    this.dir_bits     = DIR_BITS ;
	    this.dir_adr      = block_size ;
	    this.bucket_elems = ((block_size - jdbmBucket.fsize)
				 / (jdbmBucketElement.fsize + 4)) + 1 ;
	    // Compute the # of directory bits:
	    this.dir_size   = (1<<dir_bits) ;
	    while (dir_size*4 < block_size) {
		dir_size <<= 1 ;
		dir_bits  += 1 ;
	    }
	    if (dir_size*4 != block_size) 
		throw new RuntimeException ("block_size can't match dir_size");
	    // Setup the cache and allocate the directory:
	    this.cache_size     = CACHE_SIZE ;
	    this.loaded_buckets = 1 ;
	    this.diridx         = new int[dir_size] ;
	    int bucket_adr      = 2*block_size ;
	    LRUEntry b          = list.addEntry(new jdbmBucket(this
							       , bucket_adr
							       , 3)) ;
	    for (int i = 0 ; i < dir_size ; i++) 
		diridx[i] = bucket_adr ;
	    // Initialize the db available blocks:
	    this.avail_length = ((block_size - fsize) / (2 * 4)) ;
	    this.avail_size   = new int[avail_length] ;
	    this.avail_ptr    = new int[avail_length] ;
	    this.avail_count  = 0 ;
	    this.next_block   = 4 ;
	    // Write back these configuration options:
	    DataOutputStream out = null ;
	    // Block 0: the header
	    out = new DataOutputStream(new FastByteArrayOutputStream(buffer));
	    saveHeader (out) ;
	    fd.seek(0) ;
	    fd.write(buffer) ;
	    // Block 1: the directory
	    out = new DataOutputStream(new FastByteArrayOutputStream(buffer)) ;
	    saveDirectory(out);		
	    fd.seek(dir_adr) ;
	    fd.write(buffer) ;
	    // Block 2: the initial bucket
	    out = new DataOutputStream(new FastByteArrayOutputStream(buffer));
	    b.bucket.save(out) ; 
	    fd.seek(2*block_size) ;
	    fd.write(buffer) ;
	}
    }

    /**
     * Update this enumeration current bucket to the next one.
     * @param enum The current enumeration state.
     * @return A boolean <strong>true</strong> if the end of the database
     * has been reached, <strong>false</strong>otherwise.
     */

    protected boolean getNextBucket(jdbmEnumerator enum)
	throws IOException
    {
	int fptr = -1;
	int last = -1;
	int didx = -1;
	if ( enum.didx < 0 ) {
	    didx = 0;
	    last = -1;
	    fptr = diridx[0];
	} else  if ( enum.didx + 1 < dir_size ) {
	    didx = enum.didx;
	    last = diridx[didx++];
	    fptr = diridx[didx];
	} else {
	    return false;
	}
	// Loop until this is really a new one:
	while ((fptr == last) && (didx < dir_size))
	    fptr = diridx[didx++];
	// Lookup this bucket:
	if ( didx < dir_size ) {
	    LRUEntry lru  = list.lookupBucket(fptr) ;
	    if ( lru == null ) 
		lru = loadBucket(fptr) ;
	    list.notifyUses (lru) ;
	    enum.bucket = lru.bucket ;
	    enum.didx   = didx;
	    enum.bidx   = 0;
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Enumerate the keys of this database.
     * This method will retun an enumeration object suitable to walk through
     * all the keys of the database. You <strong>are not guaranteed</strong>
     * that the enumerator will not enumerate the same key multiple time. 
     * <p>You are guaranteed, however that you will walk at least once
     * through all the keys that were present at the time you created the 
     * enumeration (but not through the one that were deleted while you are
     * walking through the database).
     * @return An enumeration instance.
     */

    public Enumeration keys() { 
	return new jdbmEnumerator(this, true, -1);
    }

    /**
     * Enumerate the elements of the database.
     * This method has the same limitations then it's <code>keys</code>
     * counterpart.
     * @return An enumeration instance.
     */

    public Enumeration elements() {
	return new jdbmEnumerator(this, false, -1);
    }

    public static byte[] getBytes(String str) {
	byte b[] = new byte[str.length()] ;
	str.getBytes(0, b.length, b, 0) ;
	return b;
    }

    public static void main (String args[])
	throws Exception
    {
	jdbm db = new jdbm(args[0]) ;
	db.printHeader(System.out) ;
	for (int i = 1 ; i < args.length ; i++) {
	    if ( args[i].equals("-add") ) {
		byte bk[] = getBytes(args[i+1]) ;
		byte bv[] = getBytes(args[i+2]) ;
		db.store(bk, bv, STORE_INSERT) ;
		i += 3 ;
	    } else if ( args[i].equals("-get") ) {
		byte bk[] = getBytes(args[i+1]) ;
		byte bv[] = db.lookup(bk) ;
		if ( bv == null )
		    System.out.println("not found.") ;
		else
		    System.out.println(new String(bv, 0, 0, bv.length)) ;
		i+= 2 ;
	    } else if (args[i].equals("-nadd") ) {
		int    count = Integer.parseInt(args[i+1]) ;
		String pref  = args[i+2] ;
		while (--count >= 0) {
		    String key = pref+"-"+count;
		    String val = pref+"-value-for-"+count ;
		    System.out.println(key+"="+val) ;
		    db.store(getBytes(key), getBytes(val), STORE_INSERT) ;
		}
		i += 3 ;
	    } else if (args[i].equals("-nget")) {
		int    count = Integer.parseInt(args[i+1]) ;
		String pref  = args[i+2] ;
		while (--count >= 0) {
		    String key  = pref+"-"+count ;
		    byte   bv[] = db.lookup(getBytes(key)) ;
		    if ( bv == null )
			System.out.println("*** not found.") ;
		    else
			System.out.println(key
					   + "="
					   + new String(bv, 0, 0, bv.length));
		}
		i += 3 ;
	    } else if (args[i].equals("-del")) {
		if ( db.delete(getBytes(args[i+1])) )
		    System.out.println("deletion succesfull.") ;
		else
		    System.out.println("element not found.") ;
		i += 2 ;
	    } else {
		System.out.println("[-add <key> <val>] [-get <key>]") ;
		System.exit(1) ;
	    }
	}
	db.save() ;
    }
}
