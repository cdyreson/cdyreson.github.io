package w3c.tools.dbm;

import java.io.* ;
import java.util.*;
//import w3c.tools.dbm.*;

class Main {

  public static void main (String[] argv) {
   Hashtable h = new Hashtable();
   byte[]  b = {0, 2, 4};
   byte[]  c = {0, 2, 4};
   MyByteArray g = new MyByteArray(b);
   MyByteArray f = new MyByteArray(c);
   h.put(g, b);
   if (h.containsKey(g)) {System.out.println("yes");}
   h.put(f, c);
   if (h.containsKey(g)) {System.out.println("yes");}
  }
}
