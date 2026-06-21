// FastByteArrayOutputStream.java
// $Id: FastByteArrayOutputStream.java,v 1.1 1996/04/10 13:56:19 abaird Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

//package w3c.tools.dbm ;

import java.io.* ;

class FastByteArrayOutputStream extends ByteArrayOutputStream {
    FastByteArrayOutputStream(byte buf[]) {
	this.buf = buf ;
    }
}
