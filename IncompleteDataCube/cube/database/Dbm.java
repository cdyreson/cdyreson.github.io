package cube.database ;

import java.io.* ;
import java.util.*;
import cube.tools.*;
import w3c.tools.dbm.jdbm;
//import dbm.*;

/**
* This class encapsulates the interface to a dbm file, in this case gdbm.
* It is adapted from jdbm.java (c) COPYRIGHT MIT and INRIA, 1996.
* This is a fake dbm-like database interface to the jdbm package which is a real
* java interface to gdbm (you'll have to grab jdbm/gdbm from elsewhere).
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
* @author jdbm.java
**/
public class Dbm {
  /** The file handle **/
  private jdbm jdbmFile;
  /** The name of the file **/
  private String name;

  /**
  * Create a new Dbm file.
  **/
  public Dbm (String fileName) {
    this.name = fileName; 
    try {
      jdbmFile = new jdbm(fileName);
      } catch (IOException e) {
        Internal.Error("Opening jdbm file " + this.name + "\nError: "+e);
      }
    }

    /**
     * Store the given association of key/value.  Always stores in replace mode.
     * @param key The bytes that makes the key.
     * @param value The bytes that makes the value.
     * @param mode The mode of the storing, can be...
     */

  public void store (byte key[], byte value[]) {
    try {
      jdbmFile.store(key,value,jdbmFile.STORE_REPLACE);
      } catch (IOException e) {
        Internal.Error("in storeJdbm" + e);
      }
    }

    /**
     * Lookup the value associated with the provided key.
     * @param key The bits of the key to look for.
     * @return The bits that makes the associated value, or 
     *    <strong>null</strong> if not found.
     */

  public byte[] lookup(byte key[]) {
    try {
      return jdbmFile.lookup(key);
      } catch (IOException e) {
      System.out.println("Lookup failed in jdbmFile " + e);
      }
      return null;
    }

  /**
  * Delete the association for the provided key.
  * @param key The key of the element to remove.
  * @return A boolean <strong>true</strong> if deletion was succesfull.
  */

  public boolean delete(byte key[]) {
    boolean found = false;
 
    try {
      found = jdbmFile.delete(key);
      } catch (IOException e) {
        Internal.Error("delete in jdbmFile failed!");
      }
    return found;
    }

  ///**
  // * Number of tuples in the table.
  // */
  // public int size() {
  //  return jdbmFile.size();
  //  }

  /**
   * Save thisdatabase to disk, do nothing! 
   */
  public void save() {
    try {
      jdbmFile.save();
      } catch (IOException ex) {
        Internal.Error("Gotta save it!");
      }
    }

    /**
     * Enumerate the keys of this database.
     * @return An enumeration instance.
     */

    public Enumeration keys() { 
      return jdbmFile.keys();
    }
}
