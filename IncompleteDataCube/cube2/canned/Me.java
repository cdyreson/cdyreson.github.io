import java.io.*;
import java.util.*;
import cube.tools.*;
public class Me {
  public static Hashtable whichInit(String s) {

    if (s.compareTo("/nobu/curtis/dbs/filters") == 0) {return init1();}
    if (s.compareTo("/nobu/curtis/dbs/filterUnits") == 0) {return init2();}
    if (s.compareTo("/nobu/curtis/dbs/filterMeasures") == 0) {return init3();}
    if (s.compareTo("/nobu/curtis/dbs/count") == 0) {return init4();}
   Internal.Error("Died opening file " + s);
   return null;
   } // end of whichInit
  public static Hashtable init1() {
    Hashtable j = new Hashtable();
  byte [] key0 = {0,0,4,-122};
  byte [] value0 = {0,0,0,1};
  j.put(key0,value0);
  byte [] key1 = {0,0,4,-123};
  byte [] value1 = {0,0,0,1};
  j.put(key1,value1);
  byte [] key2 = {0,0,4,-124};
  byte [] value2 = {0,0,0,0};
  j.put(key2,value2);
  byte [] key3 = {0,0,0,20};
  byte [] value3 = {0,0,0,0};
  j.put(key3,value3);
  byte [] key4 = {0,0,0,9};
  byte [] value4 = {0,0,0,0};
  j.put(key4,value4);
  return j;
  }
  public static Hashtable init2() {
    Hashtable j = new Hashtable();
  byte [] key0 = {0,0,0,0};
  byte [] value0 = {0,0,0,9,0,0,0,20,0,0,4,-124};
  j.put(key0,value0);
  return j;
  }
  public static Hashtable init3() {
    Hashtable j = new Hashtable();
  byte [] key0 = {0,0,0,0};
  byte [] value0 = {0,0,0,8,0,0,0,58,0,0,4,-125};
  j.put(key0,value0);
  return j;
  }
  public static Hashtable init4() {
    Hashtable j = new Hashtable();
  return j;
  }
} // end of class
