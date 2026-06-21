package cube.database;

import java.io.*;
import java.util.*;
import cube.tools.*;

/**
* This class encapsulates a database table.
*  We have implemented tables as persistent associative array.  More 
*  specifically, they are GDBM files.  The good things about GDBM files
*  are that they can be copied, compressed, fields can be of any size,
*  and they are cheap to build.  The
*  bad things are that they do not have concurrency control and are 
*  not as fast as other kinds of DBM files.
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
* @see cube.database.Database
* @see cube.database.Tuple
* @author Curtis Dyreson and Jason Pountney
**/
public class Table {
  /** name of the table*/
  private String name;
  /** dbm file handle*/
  private Dbm dbmFile;

  /**
  * Create a table with the give name (opens a dbm file).
  * @param name  Name of the table.
  */
  public Table(String name) {
    this.name = name;
    dbmFile = new Dbm(name);
    }
    
  /**
  * Insert a tuple @see cube.database.Tuple
  * into the table.  Will replace an existing tuple so be careful.
  * @param tuple Tuple to insert.
  */
  public void insertTuple(Tuple tuple) {
    byte key[] = tuple.getKeyAsBytes();
    byte value[] = tuple.getValueAsBytes();

    dbmFile.store(key,value);
    }

  /**
  * Delete a tuple with the given key from the table.
  * @param key Key to delete.
  * @return Was the tuple found and deleted?
  */
  public boolean deleteTuple(byte[] key) {
    boolean found = false;

    found = dbmFile.delete(key);
    return found;
    }

  /**
  * Retrieve a tuple with an IdList Key from the table.  
  * @param key IdList to retrieve, 
  * @return The tuple retrieved or null if no matching tuple was found.
  */
  public Tuple retrieveTuple(IdList key) {
    // Convert the key to a byte array
    byte[] keyAsBytes = key.toBytes();
    byte[] valueAsBytes = dbmFile.lookup(keyAsBytes);
    if (valueAsBytes == null) return null;
    return new Tuple(keyAsBytes, valueAsBytes);
    }

  /**
  * Retrieve a tuple with an Id Key from the table.  
  * @param key Id to retrieve, 
  * @return The tuple retrieved or null if no matching tuple was found.
  */
  public Tuple retrieveTuple(Id key) {
    // Convert the key to a byte array
    byte[] keyAsBytes = key.toBytes();
    byte[] valueAsBytes = dbmFile.lookup(keyAsBytes);
    if (valueAsBytes == null) return null;
    return new Tuple(keyAsBytes, valueAsBytes);
    }

  /**
  * Retrieve a tuple with the given key from the table.  This routine will fill
  * in the value of the passed tuple, or it will return null for
  * the tuple.  To use this you must fill in the <i> type </i> of
  * the return value in the tuple, and the key for the tuple, e.g., 
  * <pre>
  *   Tuple r = new Tuple("hi", 0);  // we want an integer value for key "hi"
  *   r = table.retrieveTuple(r);    // try to retrieve it 
  *   if (r == null) ...             // key was not found in the table 
  *   else {                         // tuple was retrieved, the table 
  *                                  // fetch the integer value 
  *     int i = ((IntColumn)(r.getValue())).getValue();
  *     ...
  * </pre>
  * @param tuple Tuple to retrieve, 
  * @return The tuple retrieved or null if no matching tuple was found.
  */
  public Tuple retrieveTuple(Tuple tuple) {
    // Convert the key int to a byte array
    byte[] valueAsBytes = dbmFile.lookup(tuple.getKeyAsBytes());
    if (valueAsBytes == null) return null;
    tuple.updateValue(valueAsBytes);
    return tuple;
    }

  /**
  * Emit code to initialize a dbm file (hashtable) with the info in this table
  * This is strictly for the canned version of the interface.
  * @Return void
  */
  public void emitCannedInitializationCode(int which) {
    System.out.println("  public static Hashtable init" + which + "() {");
    System.out.println("    Hashtable j = new Hashtable();");
    System.out.println("    byte[] key; byte[] value; String s;");
    int count = 0;
    int i;
    for (Enumeration e = dbmFile.keys(); e.hasMoreElements();) {
      byte[] b = (byte[])e.nextElement();
      System.out.print("  byte [] key" + count + " = {");
      for (i = 0; i < (b.length - 1); i++) {
        System.out.print(b[i] + ",");
        }
      System.out.println(b[i] + "};");
      b = dbmFile.lookup(b);
      System.out.print("  byte [] value" + count + " = {");
      for (i = 0; i < (b.length - 1); i++) {
        System.out.print(b[i] + ",");
        }
      System.out.println(b[i] + "};");
      System.out.println("  j.put(key"+count+".toString(),value"+count+");");
      count++;
      }
    System.out.println("  return j;");
    System.out.println("  }");
    }
 
  /**
  * Return a string of the key, value pairs (assumed to be Ids).
  * @Return A string of key,value pairs.
  */
  public String IdIdImage() {
    String result = "Key,Value Image for Table:\n";
    for (Enumeration e = dbmFile.keys(); e.hasMoreElements();) {
      byte[] b = (byte[])e.nextElement();
      byte[] valueAsBytes = dbmFile.lookup(b);
      if (valueAsBytes == null) {
        Internal.Error("Failed to retrieve on a known key!");
        }
      Id i = new Id(b);
      result += i.toString() + ",";
      i = new Id(valueAsBytes);
      result += i.toString() + "\n";
      }
    return result;
    }

  /**
  * Return a string of the key, value pairs (assumed to be Id Lists).
  * @Return A string of key,value pairs.
  */
  public String IdIdListImage() {
    dbmFile.save();
    String result = "Key,Value Image for Table:\n";
    for (Enumeration e = dbmFile.keys(); e.hasMoreElements();) {
      byte[] b = (byte[])e.nextElement();
      byte[] valueAsBytes = dbmFile.lookup(b);
      Id key = new Id(b);
      result += key.toString() + "+";
      IdList value = new IdList(valueAsBytes);
      result += value.image() + "\n";
      }
    return result;
    }

  /**
  * Return an enumeration of the key values.
  * @Return An enumeration.
  */
  public Enumeration enumerateKeys() {
    return dbmFile.keys();
    }

  /**
  * Return a string of the key values (assumed to be Ids) as Strings.
  * @Return A string of key names.
  */
  public String IdAsStringKeysImage() {
    String result = "Key Name Image for Table:\n";
    for (Enumeration e = dbmFile.keys(); e.hasMoreElements();) {
      byte[] b = (byte[])e.nextElement();
      Id i = new Id(b);
      result += i.toString() + " ";
      }
    return result;
    }

  /**
  * Return a string of the key values (assumed to be Ids).
  * @Return A string of key values.
  */
  public String IdKeysImage() {
    String result = "Key Image for Table:\n";
    for (Enumeration e = dbmFile.keys(); e.hasMoreElements();) {
      byte[] b = (byte[])e.nextElement();
      Id i = new Id(b);
      result += i.image() + " ";
      }
    return result;
    }

  /**
  * Clear all the tuples from a table.  Not yet implemented.
  */
  public void clear() {
    System.out.println("Clearing table");
    }

  /**
  * Clear all the tuples from a table.  Not yet implemented.
  */
  public void save() {
      dbmFile.save();
    }

}
