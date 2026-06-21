package cube.database;
 
import java.lang.*;
import java.util.*;
import cube.tools.*;
 
/**
* This class encapsulates an Id in a Database
* An Id is a compact 'key' data type for columns in a database.
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
public class Id {
  public int id;
  public static final int byteSize = 4;
 
  /**
  * Construct an Id from an array of integers
  * An Id is just a list of integers, currently a list of size 1,
  *   so we are limited to 2^32-1 Ids.  Id can be reimplemented
  *   using the BigInteger class to get more Ids.
  **/
  public Id(int[] id) {
    this.id = id[0];
    }
 
  /**
  * Construct an Id from an integer
  * The initial value of the Id is taken from the integer
  **/
  public Id(int id) {
    this.id = id;
    }
 
  /**
  * Construct an Id from a string.
  **/
  public Id(String s) {
    //this.id = Convert.toId(s);
    Id temp = Convert.toId(s);
    this.id = temp.id;
    }

  /**
  * Construct an Id from nothing, initialise the Id to zero.
  * Should we deprecate this?
  **/
  public Id() {
    this.id = 0;
    }
 
  /**
  * Construct an Id from a byte array
  **/
  public Id(byte[] b) {
    id = (b[3] & 0x000000FF) +
         ((b[2] & 0x000000FF) << 8) +
         ((b[1] & 0x000000FF) << 16) +
         ((b[0] & 0x000000FF) << 24);
    }
 
  /**
  * Construct an Id from a byte array
  **/
  public Id fromBytes(byte[] b) {
    return new Id(b);
    }

  /**
  * Hashcode generator
  **/
  public int hashCode() {
    return this.id;
    }
 
  /**
  * Less than test, is this Id less than another?
  *  compare byte images
  **/
  public boolean lt(Id other) {
    return this.id < other.id;
    }

  /**
  * Equality test, is this Id eqal to another?
  *  compare byte images
  **/
  public boolean equals(Object obj) {
    Id other = (Id)obj;
    return this.id == other.id;
    }
 
  /**
  * Return an image of the id as a string
  **/
  public String image() {
    return new String("" + this.id);
    }
 
  /**
  * Return a duplicate of the object
  **/
  public Id cloneMe() {
    return new Id(this.id);
    }
 
  /**
  * Convert this Id to a String
  **/
  public String toString() {
    return Convert.toString(this);
    }

  /**
  * Increment an Id (this is for counters)
  **/
  public void increment() {
    this.id++;
    }
 
  /**
  * Convert this Id to a byte array
  **/
  public byte[] toBytes() {
    byte[] b = new byte[byteSize];
    b[3] = (byte) (id >>> 0);
    b[2] = (byte) (id >>> 8);
    b[1] = (byte) (id >>> 16);
    b[0] = (byte) (id >>> 24);
    return b;
    }
 
}
