package cube.database;
 
import java.lang.*;
import java.util.*;
 
/**
* An Integer Column is just an integer, but can be used as
* a 'key' data type for columns in a database.
* <P>
* For more information on the cube see the cube
* <A HREF="cube.Overview.html">Overview</A>.
* <BR>
* Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
* Please be aware of the
* <A HREF="cube.Licence.html">Licence</A>
* and
* <A HREF="cube.Version.html">Version</A>.
*
* @see cube.database.Table
* @see cube.database.Tuple
* @author Curtis Dyreson 
**/
public class IntegerCol {

  /** The value of the integer, it is public rather than writing a getValue method...*/
  public int value;
  /** Size of the data type */
  public static final int byteSize = 4;
 
  /**
  * Construct an IntegerCol from an integer
  */
  public IntegerCol(int value) {
    this.value = value;
    }
 
  /**
  * Construct an IntegerCol from a byte array
  */
  public IntegerCol(byte[] b) {
    value = (b[3] & 0x000000FF) +
         ((b[2] & 0x000000FF) << 8) +
         ((b[1] & 0x000000FF) << 16) +
         ((b[0] & 0x000000FF) << 24);
    }
 
  /**
  * Hashcode generator
  */
  public int hashCode() {
    return this.value;
    }
 
  /**
  * Equality test, is this IntegerCol equal to another?
  *  compare byte images
  * @param other - The object to compare with.
  */
  public boolean equals(Object obj) {
    IntegerCol other = (IntegerCol)obj;
    return this.value == other.value;
    }
 
  /**
  * Return an image of this as a string
  */
  public String image() {
    return new String("" + this.value);
    }
 
  /**
  * Return a duplicate of the object
  */
  public IntegerCol cloneMe() {
    return new IntegerCol(this.value);
    }
 
  /**
  * Increment an IntegerCol by one (this is for counters)
  */
  public void increment() {
    this.value++;
    }

  /**
  * Increment an IntegerCol by the given amount
  * @param other - amount to increment by
  */
  public void increment(int other) {
    this.value += other;
    }

  /**
  * Increment an IntegerCol by the amount of another IntegerCol
  * @param sum - amount to increment by
  */
  public void increment(IntegerCol other) {
    this.value += other.value;
    }
 
  /**
  * Convert to a byte array
  */
  public byte[] toBytes() {
    byte[] b = new byte[byteSize];
    b[3] = (byte) (value >>> 0);
    b[2] = (byte) (value >>> 8);
    b[1] = (byte) (value >>> 16);
    b[0] = (byte) (value >>> 24);
    return b;
    }
 
}
