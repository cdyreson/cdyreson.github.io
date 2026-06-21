// jdbm.java
// $Id: jdbmEnumerator.java,v 1.3 1996/09/27 16:52:37 abaird Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

//package w3c.tools.dbm ;

import java.util.*;
import java.io.*;

class jdbmEnumerator implements Enumeration {
    int        didx    = -1;
    jdbmBucket bucket  = null;
    jdbm       dbm     = null;
    boolean    bdone   = false;
    boolean    keys    = false;
    int        bidx    = 0;
    byte       next[]  = null;

    jdbmEnumerator(jdbm dbm, boolean keys, int didx) {
	this.dbm    = dbm;
	this.keys   = keys;
	this.didx   = didx;
	this.bucket = bucket;
	try {
	    dbm.getNextBucket(this);
	} catch (IOException ex) {
	    throw new RuntimeException("jdbm: enumeration failure.");
	}
    }
    
    private final byte[] computeNextElement() {
	try {
	    while ( bucket != null ) {
		// Start by looking in the current bucket:
		while (bidx < dbm.bucket_elems) {
		    if (bucket.elements[bidx].hashval != -1) {
			byte b[] =  (keys
				     ? dbm.readKey(bucket.elements[bidx])
				     : dbm.readData(bucket.elements[bidx]));
			bidx++;
			return b;
		    } else {
			bidx++;
		    }
		}
		// Trying looking into next bucket:
		if ( ! dbm.getNextBucket(this) )
		    return null;
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	    throw new RuntimeException("jdbm: enumeration failure.");
	}
	return null;
    }

    public boolean hasMoreElements()
    {
	if ( next == null )
	    next = computeNextElement();
	return next != null;
    }
    
    
    public Object nextElement()
    { 
	byte val[] = next;
	if ((val == null) && ((val = computeNextElement()) == null))
	    throw new NoSuchElementException("jdbm");
	next = null;
	return val;
    }
}
