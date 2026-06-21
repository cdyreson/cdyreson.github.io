package cube.database;
 
import java.lang.*;
import java.util.*;
import java.io.*;
import cube.tools.*;
 
/**
* This class encapsulates a String Column in a database.
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
public class StringCol {
  protected String value;
 
  /**
  * Construct a StringCol from a String.
  * @param value - the string
  */
  public StringCol(String value) {
    this.value = value;
    }
 
  /**
  * Construct a StringCol from a byte Array
  * @param b - a byte array that better represent a String!
  */
  public StringCol(byte[] valueAsBytes) {
/*
    // Convert the value byte array to a value String 
    char[] valueAsChars = new char[valueAsBytes.length];
    ByteArrayInputStream valueStream = new ByteArrayInputStream(valueAsBytes);
    InputStreamReader reader = new InputStreamReader(valueStream);
    try {
      reader.read(valueAsChars, 0, valueAsChars.length);
      } catch (IOException e) {
        Internal.Error("in ByteArrayToString");
      } 
    value = new String(valueAsChars);
    }
*/
    value = new String(valueAsBytes, 0);
    }
 


  /**
  * Return the string value for this column
  * @return - the string
  */
  public String toRealString() {
    return value;
    }
 
  /**
  * Construct a byte array from this string
  **/
  public byte[] toBytes() {
/*
    // Convert the String to a byte array
    ByteArrayOutputStream valueStream = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(valueStream);
    byte[] valueAsBytes = new byte[2 * value.length()];
    try {
      writer.write(value, 0, value.length());
      writer.close();
      valueAsBytes = valueStream.toByteArray();
      } catch (IOException e) {
        Internal.Error("in StringToByteArray");
      } 
    return valueAsBytes;
    }
*/
    byte[] b = new byte[value.length()];
    value.getBytes(0, value.length(), b, 0);
    return b;
    }


}
