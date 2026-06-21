package cube.database;

import java.lang.*;
import java.util.*;
import cube.tools.*;
 
/**
* An IdList is a data type for a column in a database.
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
* @see cube.database.Id
* @see cube.database.Tuple
* @author Curtis Dyreson and Jason Pountney
**/
public class IdList {
  private Id[] value;
  private int hashCode = 0;

  /**
  * Construct a list from an array of Ids
  * @param value - An array of Ids, the array is not cloned for 
  *                this constructor!
  **/
  public IdList(Id[] value) {
    // should we clone this?
    this.value = value;
    }


  /**
  * The hashCode for this is the sum of the hashCodes for all the Ids in it
  **/
  public int hashCode() {
    if (hashCode != 0) return hashCode;
    int sum = 0;
    for (int i = 0; i < this.value.length; i++) {
      Id u = value[i];
      sum += u.hashCode();
      }
    hashCode = sum;
    return sum;
    }

  /**
  * IdLists can be used in HashTables.
  * @param obj - It must be an IdList or it will die a horrible death.
  **/
  public boolean equals(Object obj) {
    IdList other = (IdList)obj;
    Enumeration e2 = other.enumerate();
    Enumeration e1 = this.enumerate();
    while (e1.hasMoreElements()) {
      Id u1 = (Id)(e1.nextElement());
      Id u2 = (Id)(e2.nextElement());
      if (!u1.equals(u2)) return false;
      }
    return true;
    }

  /**
  * Construct a list from a byte array
  * @param b - a byte array, the byte array is cloned
  */
  public IdList(byte[] b) {
    int index = 0;
    value = new Id[b.length/Id.byteSize];
    for (int i = 0; i < b.length; i += Id.byteSize) {
      byte[] temp = new byte[Id.byteSize]; 
      for (int j = 0; j < temp.length; j++) temp[j] = b[i+j];
      Id toadd = new Id(temp);
      value[index++] = new Id(temp);
      }
    }

  /**
  * Convert the value to a nice String image for dumping
  */
  public String image() {
    String s = "";
    String toadd = ""; 
    for (int i = 0; i < value.length; i++) {
      s += toadd;
      //s += value[i].image();
      s += value[i].toString();
      toadd = ", "; 
      }
    return s;
    }
 
  /**
  * Return an enumeration of the elements in the list.
  **/
  public Enumeration enumerate() {
    return new IdEnumeration(value);
    }
 
  /**
  * Retrieve an array of Ids that is the sequence
  */
  public Id[] toIdArray() {
    return value;
    }
 
  /**
  * Convert to a byte array
  */
  public byte[] toBytes() {
    byte[] b = new byte[value.length*Id.byteSize];
    int k = 0;
 
    for (int i = 0; i < value.length; i++) {
      byte[] id = value[i].toBytes();
      for (int j = 0; j < id.length; j++) {
        b[k++] = id[j];
        }
      }
    return b;
    }
}
