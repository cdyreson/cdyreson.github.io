// jdbmBucketElement.java
// $Id: jdbmBucketElement.java,v 1.1 1996/04/10 13:56:26 abaird Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

//package w3c.tools.dbm ;

import java.io.* ;

/**
 * A single element of a bucket.
 */

class jdbmBucketElement {
    /**
     * Number of bytes of the key to keep here, for fast search.
     */
    static final int KEYSTART = 4 ;
    /**
     * The size of an element, when dumped to disk.
     */
    static final int fsize = (4				// hash value
			      + KEYSTART		// first bytes of key
			      + 4			// key size
			      + 4			// data size
			      + 4) ;			// file pointer
    
    /**
     * The hashvalue for this element.
     */
    int hashval = -1 ;
    /**
     * The first byte of this element, for fast comparison.
     */
    byte keystart[] = null ;
    /**
     * The size of the key for this element.
     */
    int key_size = -1 ;
    /**
     * The size of the data associated with this element.
     */
    int data_size = -1 ;
    /**
     * The file pointer, to point to the element key + data.
     */
    int fileptr = -1 ;


    static final jdbmBucketElement restore (DataInputStream in
					    , jdbmBucketElement into) 
	throws IOException
    {
	into.hashval   = in.readInt() ;
	in.read(into.keystart) ;
	into.key_size  = in.readInt() ;
	into.data_size = in.readInt() ;
	into.fileptr   = in.readInt() ;
	return into ;
	
    }

    static final jdbmBucketElement restore (DataInputStream in) 
	throws IOException
    {
	jdbmBucketElement el = new jdbmBucketElement() ;
	return restore (in, el) ;
    }

    void save (DataOutputStream out)
	throws IOException
    {
	out.writeInt(hashval) ;
	out.write(keystart) ;
	out.writeInt(key_size) ;
	out.writeInt(data_size) ;
	out.writeInt(fileptr) ;
    }

    public String toString() {
	return "[h="+hashval+" ,ksize="+key_size+" ,dsize="+data_size+"]";
    }

    jdbmBucketElement() {
	this.hashval   = -1 ;
	this.keystart  = new byte[KEYSTART] ;
	this.key_size  = -1 ;
	this.data_size = -1 ;
	this.fileptr   = -1 ;
    }
}
