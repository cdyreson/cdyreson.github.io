package cube.tools;

import java.io.*;
import java.util.*;
import cube.database.*;
import cube.globals.*;
import cube.configure.*;

/**
* A class that provides conversion routines, mostly for use in a database.
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
* @author Curtis Dyreson and Jason Pountney
*/
public class Convert {
  private static Database db = null;  // Internal db for String mapping, yuck!
  private static Table StringToIdTable = null;
  private static Table IdToStringTable = null;
  private static Id tokenCount = new Id(0);

  /**
  * We have to initialize the conversion tools
  * @param s - name of the database that stores the tools database
  **/
  private static void initialize(String s) {
    db = new Database(s);
    StringToIdTable = db.createTable(Constants.stringTableName);
    IdToStringTable = db.createTable(Constants.idTableName);
    Tuple r = new Tuple(new StringCol("_tokenCount"), tokenCount);
    Tuple t = StringToIdTable.retrieveTuple(r);
    if (t == null) StringToIdMapping("_tokenCount");
    else tokenCount = t.getValueAsId();
    }

  /**
  * Add a new mapping to the string to Id tables
  * @param s - the string to convert
  * @return - the Id to which it maps
  **/
  public static Id StringToIdMapping(String key) {
    if (db == null) initialize(Constants.myDatabaseName);
    StringCol keyCol = new StringCol(key);
    Tuple t1 = new Tuple(keyCol, tokenCount);
    StringToIdTable.insertTuple(t1);
    Tuple t2 = new Tuple(tokenCount, keyCol);
    IdToStringTable.insertTuple(t2);
    tokenCount.increment();
    Tuple t3 = new Tuple(new StringCol("_tokenCount"), tokenCount);
    StringToIdTable.insertTuple(t3);
    return t1.getValueAsId();
    }

  /**
  * Convert an Id to a String by doing a table lookup
  * @param key - the Id to convert
  * @return - the string to which it maps
  **/
  public static String toString(Id key) {
    if (db == null) initialize(Constants.myDatabaseName);
    Tuple r = new Tuple(key);
    Tuple t = IdToStringTable.retrieveTuple(r);
    if (t == null) return null;
    return t.getValueAsString();
    }

  /**
  * Convert a String to an Id by doing a table lookup
  * @param key - the String to convert
  * @return - the Id to which it maps
  **/
  public static Id toId(String key) {
    if (db == null) initialize(Constants.myDatabaseName);
    Tuple r = new Tuple(new StringCol(key));
    Tuple t = StringToIdTable.retrieveTuple(r);
    if (t == null) return StringToIdMapping(key);
    else return t.getValueAsId();
    }

  /*
  public static byte[] jdk1.1ToByteArray(String key) {
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
  */

  /*
  public static String jdk1.1ToString(byte valueAsBytes[]) {
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
  */

  public static void save() {
    StringToIdTable.save();
    IdToStringTable.save();
    }
}
