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
    private Hashtable h;

    /**
     * Mode for store - Replace any existing entry with the new provided one.
     */
    public final static int STORE_REPLACE = 1 ;
    /**
     * Mode for store - Only insert this element if it is not already defined.
     */
    public final static int STORE_INSERT = 2 ;

    public jdbm (String fileName) //throws IOException 
    {
        // have to initialize the hash table
        InitTables i = new InitTables();
        h = i.whichInit(fileName);
    }


    /**
     * Store the given association of key/value.
     * @param key The bytes that makes the key.
     * @param value The bytes that makes the value.
     * @param mode The mode of the storing, can be...
     */

    public void store (byte key[], byte value[], int mode) 
	//throws IOException
    {
        //h.put(Convert.ToString(key), value);
        h.put(toString(key), value);
    }

    /**
     * Lookup the value associated with the provided key.
     * @param key The bits of the key to look for.
     * @return The bits that makes the associated value, or 
     *    <strong>null</strong> if not found.
     */

    public byte[] lookup(byte key[])
	//throws IOException
    {
        if (h.containsKey(toString(key))) { 
        //if (h.containsKey(Convert.ToString(key))) { 
          //return ((byte [])h.get(Convert.ToString(key)));
          return ((byte [])h.get(toString(key)));
          }
        else { return null; }
    }

    /**
     * Delete the association for the provided key.
     * @param key The key of the element to remove.
     * @return A boolean <strong>true</strong> if deletion was succesfull.
     */

    public boolean delete(byte key[]) 
	//throws IOException
    {
        if (h.containsKey(toString(key))) { 
          h.remove(toString(key)); return true; }
        //if (h.containsKey(Convert.ToString(key))) { 
          //h.remove(Convert.ToString(key)); return true; }
        return false;
    }

    /**
     * Save thisdatabase to disk, do nothing! 
     */

    public void save() 
	//throws IOException
    {
        return;
    }

    /**
     * Enumerate the keys of this database.
     * @return An enumeration instance.
     */

    public Enumeration keys() { 
      Vector v = new Vector();
      for (Enumeration e = h.keys(); e.hasMoreElements();) {
        //v.addElement(Convert.ToByteArray((String)(e.nextElement())));
        String s = (String)e.nextElement();
        System.out.print(s + " ");
        System.out.println(h.get(s));
        v.addElement(toByteArray(s));
        }
      return v.elements();
    }

  private String toString(byte valueAsBytes[]) {
    // Convert the value byte array to a value String 
    char[] valueAsChars = new char[valueAsBytes.length];
    ByteArrayInputStream valueStream = new ByteArrayInputStream(valueAsBytes);
    InputStreamReader reader = new InputStreamReader(valueStream);
    try {
      reader.read(valueAsChars, 0, valueAsChars.length);
      } catch (IOException e) {
        Internal.Error("in ByteArrayToString");
      } 
 
    return new String(valueAsChars);
    }

  private byte[] toByteArray(String key) {
    // Convert the String to a byte array
    ByteArrayOutputStream keyStream = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(keyStream);
    byte[] keyAsBytes = new byte[2 * key.length()];
    //System.out.println("size of keyAsBytes " + keyAsBytes.length);
    try {
      //System.out.println("ch encoding " + writer.getCharacterEncoding());
      //System.out.println("key is " + key + key.length());
      writer.write(key, 0, key.length());
      writer.close();
      keyAsBytes = keyStream.toByteArray();
      //System.out.println(keyAsBytes[0]);
      //System.out.println(" it is " + keyStream.toString());
      } catch (IOException e) {
        Internal.Error("in StringToByteArray");
      }
    //System.out.println("size of keyAsBytes " + keyAsBytes.length);
    return keyAsBytes;
    }

}
