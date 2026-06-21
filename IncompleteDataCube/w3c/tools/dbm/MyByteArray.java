package w3c.tools.dbm ;

import java.io.* ;
import java.util.*;
//import cube.tools.*;

/**
 * Implement a sane equality test on a byte array
 */

public class MyByteArray extends Object {
    private byte[] b;
    private int hashCode;

    public MyByteArray (byte[] b) {
      this.b = b;
      hashCode = 0;
      for (int i = 0; i < b.length; i++) hashCode += b[i];
    }

    public byte[] toRealMyByteArray () {
      return this.b;
    }

    public int hashCode () {
      return hashCode;
    }

    public boolean equals (Object other) {
      MyByteArray b = (MyByteArray)other;
      return b.equals(this.b);
    }

    public boolean equals (MyByteArray other) {
      return other.equals(this.b);
    }

    public boolean equals (byte[] a) {
      if (a.length != b.length) return false;
      for (int i = 0; i < a.length; i++) if (a[i] != b[i]) return false;
      return true;
    }

}
