// jdbm.java
// $Id: jdbm.java,v 1.4 1996/09/27 16:52:36 abaird Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

package w3c.tools.dbm ;

import java.io.* ;
import java.util.*;
import cube.tools.*;

/**
 * An fake dbm like database in Java, this is an in memory version 
 * with canned data to support an applet.
 */

public class jdbm {
    public static int STORE_REPLACE = -1;
    private Hashtable h;
    private String name;

    public jdbm (String fileName) throws IOException 
    {
        // have to initialize the hash table
        InitTables i = new InitTables();
        h = i.whichInit(fileName);
        this.name = fileName;
    }


    /**
     * Store the given association of key/value.
     * @param key The bytes that makes the key.
     * @param value The bytes that makes the value.
     * @param mode The mode of the storing, can be...
     */

    public void store (byte key[], byte value[], int mode) throws IOException
    {
        h.put(new MyByteArray(key), value);
    }

    /**
     * Lookup the value associated with the provided key.
     * @param key The bits of the key to look for.
     * @return The bits that makes the associated value, or 
     *    <strong>null</strong> if not found.
     */

    public byte[] lookup(byte key[]) throws IOException
    {
        MyByteArray b = new MyByteArray(key);
        if (h.containsKey(b)) { 
          return ((byte [])h.get(b));
          }
        else { 
          return null; 
          }
    }

    /**
     * Delete the association for the provided key.
     * @param key The key of the element to remove.
     * @return A boolean <strong>true</strong> if deletion was succesfull.
     */

    public boolean delete(byte key[]) throws IOException
    {
        MyByteArray b = new MyByteArray(key);
        if (h.containsKey(b)) { 
          h.remove(b); return true; }
        return false;
    }

    /**
     * Save thisdatabase to disk, do nothing! 
     */

    public void save() throws IOException
    {
        return;
    }

    /**
     * Return the number of keys in this database.
     * @return An integer
     */

    public int size() { 
      return h.size();
    }


    /**
     * Enumerate the keys of this database.
     * @return An enumeration instance.
     */

    public Enumeration keys() { 
      Vector v = new Vector();
      for (Enumeration e = h.keys(); e.hasMoreElements();) {
        MyByteArray b = (MyByteArray)e.nextElement();
        v.addElement(b.toRealMyByteArray());
        }
      return v.elements();
    }


}
