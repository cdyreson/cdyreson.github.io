package cube.database;

import java.lang.*;
import java.util.*;
import cube.tools.*;

/**
* An IdSet is a set of Ids.  It is implemented using a Hashtable.
* An IdSet is a data type for columns in a database. 
*  An IdSet cannot be used as a key.
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
* @author Curtis Dyreson and Jason Pountney
**/
public class IdSet {
  private Hashtable h;

  /**
  * Create an empty set
  **/
  public IdSet() {
    h = new Hashtable();
    } 

  /**
  * Create a set from a byte image of the set
  * @param b - the byte image of the set
  **/
  public IdSet(byte[] b) {
    h = new Hashtable();

    for (int i = 0; i < b.length; i += Id.byteSize) {
      byte[] temp = new byte[Id.byteSize];
      for (int j = 0; j < temp.length; j++) temp[j] = b[i+j];
      h.put(new Id(temp),"");
      }
    }
 
  /**
  * Create a set from the list
  * @param v - a list of Ids
  **/
  public IdSet(IdList v) {
    h = new Hashtable();
    Id[] array = v.toIdArray();
    for (int i = 0; i < array.length; i++) {
      h.put(array[i],"");
      }
    }

  /**
  * Create a set from an array of Ids
  * @param v - the array of Ids
  **/
  public IdSet(Id[] v) {
    h = new Hashtable();
    for (int i = 0; i < v.length; i++) {
      //System.out.println("i is " + i);
      //System.out.println("v is " + v[2]);
      //System.out.println("length is " + v.length);
      h.put(v[i],"");
      } 
    }

  /**
  * How many elements are in this set?
  **/
  public int numberOfElements() {
    return h.size();
    }

  /**
  * Is the given Id a member of the set?
  **/
  public boolean memberOf(Id v) {
    return h.containsKey(v);
    }

  /**
  * Delete an Id from this set, does nothing if element does not exist
  * @param v - element to remove
  **/
  public void delete(Id v) {
    if (h.containsKey(v)) h.remove(v);
    }

  /**
  * Insert an element into this set
  * @param v - element to insert
  **/
  public void insert(Id v) {
    h.put(v, "");
    }

  /**
  * Return an enumeration of the elements (keys) in the set.
  **/
  public Enumeration enumerate() {
    return h.keys();
    }

  /**
  * Create a new set that is the union of this set and another.
  * @param other - the other set
  */
  public IdSet union(IdSet other) {
    IdSet result = new IdSet();
    for (Enumeration e = other.enumerate(); e.hasMoreElements();) {
      result.insert((Id)e.nextElement());
      }
    for (Enumeration e = h.keys(); e.hasMoreElements();) {
      result.insert((Id)e.nextElement());
      }
    return result;
    }

  /**
  * Add the elements in another set to this one
  * @param other - the other set
  */
  public void unionSelf(IdSet other) {
    for (Enumeration e = h.keys(); e.hasMoreElements();) {
      this.insert((Id)e.nextElement());
      }
    }

  /**
  * image creates a formatted string of all the elements in the set
  */
  public String image() {
    String result = "IdSet elements are:\n";
    for (Enumeration e = h.keys(); e.hasMoreElements();) {
      Id i = ((Id)e.nextElement());
      result += i.toString() + " ";
      }
    return result + "\nno more!\n";
    }

  /**
  * Create a new set that is the result of taking the intersection of this
  * with another Set
  * @param other - the other set
  */
  public IdSet intersect(IdSet other) {
    Enumeration e = other.enumerate();
    IdSet result = new IdSet();
    while (e.hasMoreElements()) {
      Id to_test = (Id)e.nextElement();
      if (this.memberOf(to_test)) result.insert(to_test);
      }
    return result;
    }

  /**
  * Convert the set to a byte array
  */
  public byte[] toBytes() {
    byte[] b = new byte[h.size()*Id.byteSize];
    int k = 0;
 
    for (Enumeration e = h.keys(); e.hasMoreElements();) {
      byte[] id = ((Id)e.nextElement()).toBytes();
      for (int j = 0; j < id.length; j++) {
        b[k++] = id[j];
        }
      }
    return b;
    }

  /**
  * Convert the set to an array of Ids (should this be an IdList?)
  **/
  public Id[] toIdArray() {
    Id[] result = new Id[this.numberOfElements()];
    int i = 0;
    Enumeration e = this.enumerate();
    while (e.hasMoreElements()) {
      result[i++] = (Id)e.nextElement();
      }
    return result;
    }
}
