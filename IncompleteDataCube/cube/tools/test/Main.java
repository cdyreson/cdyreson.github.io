import java.io.*;
import cube.*;
import cube.database.*;

public class Main {
  public static void main(String argv[])
    {
    Database myDatabase = new Database("/nobu/curtis/dbs");
    Table unitTable = myDatabase.createTable("units");
    Table strings = myDatabase.createTable("ints");

    System.out.println(strings.IdKeysImage());
    /*
    for (Enumeration e = unitTable.enumerateKeys(); e.hasMoreElements();) {
      byte[] g = (byte[])e.nextElement();
      int k = Convert.ToInt(g);
      System.out.println(Convert.ToString(k));
      MachineMsr.addItem(
      "hi" + i
      );
      i++;
      }
    */

 
      /*
      String test = "hello";
      String test2 = "doo";
      byte[] joe;
      int x = 128;
      byte tst = (byte)128;
 
      System.out.println(((int)tst)&0x000000FF);
      System.out.println(tst);
      System.out.println("(byte)255==" + (byte)255);
      System.out.println(Convert.ToInt(Convert.ToByteArray(x)));
      joe = Convert.ToByteArray(test);
      test2 = Convert.ToString(joe);
      System.out.println(test);
      System.out.println(joe.length);
      System.out.println(test2);
      System.out.println(test2.length());
      */
    }

}
