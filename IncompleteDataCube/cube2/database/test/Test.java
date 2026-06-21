import java.io.*;
import java.util.*;
import w3c.tools.dbm.jdbm;
import cube.*;
import cube.database.*;

class Test {
  public static void main(String argsv[]) {
    Database database = new Database("/nobu/curtis/dbs");
    Table joe = database.createTable("joe");
    Tuple t = new Tuple("hi", "bye");
    joe.insertTuple(t);
    t = new Tuple("hi", "oooooo");
    joe.insertTuple(t);
    t = new Tuple("hi", "f");
    joe.insertTuple(t);
    t = joe.retrieveTuple(t);
    //System.out.println(((StrColumn)t.getValue()).getValue() + " " + ((StrColumn)t.getKey()).getValue());
    System.out.println(t.getValueAsString() + " " + t.getKeyAsString());
    System.out.println(joe.deleteTuple(t.getKey()));
    Id[] v = new Id[4];
    v[0] = new Id(1);
    v[1] = new Id(2);
    v[2] = new Id(3);
    v[3] = new Id(4);
    IdSet s = new IdSet(v);
    t = new Tuple("hi", s);
    joe.insertTuple(t);
    t = joe.retrieveTuple(t);
    Enumeration e = (t.getValueAsIdSet()).enumerate();
    for (;e.hasMoreElements();) {
      System.out.println(((Id)(e.nextElement())).image());
      }
    }
}

