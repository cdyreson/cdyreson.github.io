// jdbmBucket.java
// $Id: jdbmBucket.java,v 1.2 1996/08/09 15:16:41 abaird Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

//package w3c.tools.dbm ;

import java.io.*  ;

class jdbmBucket {
    /**
     * Number of available blocks to keep per bucket.
     */
    private static final int AVAIL_SIZE = 6 ;
    /**
     * Below wich size should we ignore free blocks ?
     */
    private final static int IGNORE_SIZE = 8 ;

    /**
     * File size of a Bucket, <em>without</em> its bucket elements.
     * The number of bucket elements in a Bucket depends on the block size
     * parameter of the database.
     */
    static final int fsize = 
    (4						// bits used to this bucket
     + 4					// # of occupied elements
     + 4					// avail count
     + (AVAIL_SIZE*4)				// avail size
     + (AVAIL_SIZE*4));				// avail ptrs
     
    /**
     * Number of bits used to get here.
     */
    int bits = 0 ;
    /**
     * Number of elements bucket full.
     */
    int count = 0 ;
    /**
     * List of buckets elements.
     */
    jdbmBucketElement elements[] = null ;
    /**
     * Number of available blocks.
     */
    int avail_count = 0 ;
    /**
     * Available blocks size.
     */
    int avail_size[] = null ;
    /**
     * Available blocks file pointers.
     */
    int avail_ptr[] = null ;

    /**
     * The database this bucket belongs to.
     */
    jdbm db = null ;
    /**
     * Is this bucket modified ?
     */
    boolean modified = false ;
    /**
     * This bucket adress in the DBF file.
     */
    int fileptr = -1 ;

    /**
     * Does array a starts with the bytes of array s.
     */

    private final boolean arrayStartsWith (byte a[], byte s[]) {
	int len = Math.min(a.length, s.length) ;
	for (int i = 0 ; i < len ; i++) {
	    if ( a[i] != s[i] ) {
db.trace("array doesn't start with.") ;
		return false ;
	    }
	}
db.trace("array matches.") ;
	return true ;
    }
    
    /**
     * Are two arrays equals ?
     * @param a1 First array.
     * @param a2 Second array.
     * @return A boolean <strong>true</strong> if arrays are equals.
     */

    private final boolean arrayEquals(byte a1[], byte a2[]) {
	if (a1.length == a2.length) {
	    int len = a1.length ;
	    for (int i = 0 ; i < len ; i++)
		if ( a1[i] != a2[i] )
		    return false ;
	    return true ;
	}
	return false ;
    }
     
     
    /**
     * Save this bucket to the given data output stream.
     * @param out The data output stream to write to.
     * @exception IOException If some IO error occured.
     */

    void save (DataOutputStream out) 
	throws IOException
    {
	out.writeInt(bits) ;
	out.writeInt(count) ;
	for (int i = 0 ; i < elements.length ; i++)
	    elements[i].save(out) ;
	out.writeInt(avail_count) ;
	for (int i = 0 ; i < avail_size.length ; i++) {
	    out.writeInt(avail_size[i]) ;
	    out.writeInt(avail_ptr[i]) ;
	}
	modified = false ;
    }

    /**
     * Restore a bucket from the DBF file.
     * @param in The data input stream to restore the bucket from.
     * @param into Restore the bucket into this unused bucket.
     * @exception IOException If some IO error occured.
     */

    static jdbmBucket restore (DataInputStream in
			       , int fileptr
			       , jdbmBucket into)
	throws IOException
    {
	into.fileptr = fileptr ;
	into.bits  = in.readInt() ;
	into.count = in.readInt() ;
	for (int i = 0 ; i < into.elements.length ; i++) 
	    jdbmBucketElement.restore(in, into.elements[i]) ;
	into.avail_count = in.readInt() ;
	for (int i = 0 ; i < into.avail_size.length ; i++) {
	    into.avail_size[i] = in.readInt() ;
	    into.avail_ptr[i]  = in.readInt() ;
	}
	return into ;
    }

    /**
     * Lookup this bucket for a given key.
     * @param key The key we are looking for.
     * @param hashval The hash value for this key.
     * @return A bucket elemennt, or <strong>null</strong>.
     */

    protected jdbmBucketElement lookup (byte key[], int hashval)
	throws IOException
    {
	int iloc = hashval % db.bucket_elems ; 
	int hloc = iloc ;
	while (true) {
	    jdbmBucketElement el = elements[iloc] ;
db.trace("lookup: at "+iloc+" "+el) ;
            if ( el.hashval == -1 )
		return null ;
	    if ((el.hashval == hashval) && arrayStartsWith(key,el.keystart)) {
		// It may be this one:
		byte filekey[] = db.readKey(el) ;
		if (arrayEquals(filekey, key))
		    return el ;
	    }
	    // It is not this one, continue, or halt:
	    iloc = (iloc+1) % elements.length ;
	    if ( iloc == hloc )
		return null ;
	}
    }

    /**
     * Add a new association to this bucket.
     * It is up to the caller to make sure that this won't require a bucket
     * split.
     * @param hashval The already computed hash value for the key.
     * @param key The key as a bytes[].
     * @param value The value as a byte[].
     */

    protected void add (int hashval, byte key[], byte value[])
	throws IOException
    {
	if ( count >= db.bucket_elems )
	    throw new RuntimeException("implementation error.") ;
	// Find a suitable location:
	int iloc = hashval % db.bucket_elems ;
	while ( true ) {
	    jdbmBucketElement el = elements[iloc] ;
// db.trace("elem["+iloc+"] of "+el);
	    if ( el.hashval == -1 ) {
		// Got a free element:
		el.hashval = hashval ;
		System.arraycopy(key, 0
				 , el.keystart, 0
				 , ((key.length <= jdbmBucketElement.KEYSTART)
				    ? key.length
				    : jdbmBucketElement.KEYSTART)) ;
		el.key_size  = key.length ;
		el.data_size = value.length ;
		el.fileptr   = db.write(this, key, value) ;
		modified     = true ;
		count++ ;
		return ;
	    }
	    iloc = (iloc+1) % elements.length ;
	}
    }
     
    /**
     * Remove the avail block at given index from our avail list.
     * @param idx The index of the avail block to remove.
     */

    private final void removeAvailable(int idx) {
	modified = true ;
	avail_count-- ;
	// The last one is easily thrown away:
	if (idx == avail_count) 
	    return ;
	// Displace all avail, etc.
	System.arraycopy(avail_size, idx+1, avail_size, idx, avail_count-idx);
	System.arraycopy(avail_ptr, idx+1, avail_ptr, idx, avail_count-idx) ;
    }

    /**
     * Mark the given item to be available for this bucket.
     * It is up to the caller to make sure that the bucket avail list size
     * will not be exceeded by this new item. Items are kept sorted by size
     * in the avail_list.
     * @param ptr The item file pointer.
     * @param size The item size.
     */

    private void markAvailable(int ptr, int size) {
	modified = true ;
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
     * Fix an available block to fit the requested size.
     * @param idx The index of thenewly allocated avail.
     * @param size The size we were requested to allocate.
     * @return The fileptr of the allocated avail block.
     */

    private int fixAvailable(int idx, int size) {
	modified = true ;
	int fileptr = avail_ptr[idx] ;
	int nsize = avail_size[idx] -= size ;
	int nptr  = avail_ptr[idx]  += size ;
	removeAvailable(idx) ;
	// Ignore remaining bytes if too small
	if ( nsize <= IGNORE_SIZE ) 
	    return fileptr ;
	// If remaining chunk is big, pass it to out db.
	if ( nsize >= db.block_size ) {
	    // This is big, leave it for the db to manage:
	    db.markAvailable (nptr, nsize) ;
	} else {
	    // Since we just got rid of an avail, we can plug a new one:
	    markAvailable(nptr, nsize) ;
	}
	return fileptr ;
    }
    
    /**
     * Remove the given element of this bucket.
     * @param el The element whose content is to be freed.
     */

    protected void delete (jdbmBucketElement el) {
	int size = el.key_size+el.data_size ;
	if ((size >= db.block_size) || (avail_count+1 >= AVAIL_SIZE)) {
	    db.markAvailable(el.fileptr, size) ;
	} else {
	    markAvailable(el.fileptr, el.key_size+el.data_size) ;
	}
	// Clear the bucket element:
	int elem_loc = el.hashval % db.bucket_elems ;
	// Following two lines suggested by  Glen Diener <grd@atg.andor.com>
	// Basically avoid hash collisions
	while(elements[elem_loc] != el)
	    elem_loc = (elem_loc+1) % db.bucket_elems ;
	el.hashval = -1 ;
	count-- ;
	// Check hash code of following elements, if any:
	int last_loc = elem_loc ;
	elem_loc = (elem_loc+1) % db.bucket_elems ;
	while ((elem_loc != last_loc) && (elements[elem_loc].hashval != -1)) {
	    int h = elements[elem_loc].hashval % db.bucket_elems ;
	    if (((last_loc < elem_loc) && ((h <= last_loc) || (h > elem_loc)))
		|| (last_loc > elem_loc && h <= last_loc && h > elem_loc)) {
// db.trace("saving: "+elem_loc+" "+elements[elem_loc]+" into "+last_loc);
		elements[last_loc] = elements[elem_loc] ;
		elements[elem_loc] = new jdbmBucketElement() ;
		last_loc = elem_loc ;
	    }
	    elem_loc = (elem_loc+1) % db.bucket_elems ;
	}
	modified = true ;
	return ;
    }
	
    /**
     * Allocate some space in this bucket.
     * @param size The size of the block to allocate.
     * @return A valid file pointer to the block, or <strong>-1</strong> if
     *    requested space wasn't available.
     */

    protected int allocateSpace(int size) {
	for (int i = 0 ; i < avail_count ; i++) {
	    if ( avail_size[i] >= size ) {
		// Got it ! return this one.
		return fixAvailable(i, size) ;
	    }
	}
	return -1 ;
    }
	
      
    jdbmBucket(jdbm db, int fileptr, int availblock) {
	this.db       = db ;
	this.fileptr  = fileptr ;
	this.elements = new jdbmBucketElement[db.bucket_elems] ;
	for (int i = 0 ; i < this.elements.length ; i++)
	    this.elements[i] = new jdbmBucketElement() ;
	this.avail_count = 0 ;
	this.avail_size  = new int[AVAIL_SIZE] ;
	this.avail_ptr   = new int[AVAIL_SIZE] ;
	if ( availblock >= 0 ) {
	    avail_count   = 1 ;
	    avail_size[0] = db.block_size ;
	    avail_ptr[0]  = availblock*db.block_size ;
	}
    }


}

