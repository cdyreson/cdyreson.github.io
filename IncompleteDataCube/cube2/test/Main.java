import java.io.*;
import java.util.*;
import cube.globals.*;
import cube.database.*;
import cube.persistent.*;
import cube.tools.*;
import cube.cubette.*;

class Main {
  public static void main(String argv[]) {
    /*
    Id id = Convert.ToId("a");
    System.out.println("a is " + Convert.ToString(id));
    id = Convert.ToId("b");
    System.out.println("b is " + Convert.ToString(id));
    id = Convert.ToId("c");
    System.out.println("c is " + Convert.ToString(id));
    id = Convert.ToId("b");
    System.out.println("b is " + Convert.ToString(id));
    id = Convert.ToId("c");
    System.out.println("c is " + Convert.ToString(id));
    */
    Globals global = new Globals();
    System.out.println(global.filterMeasureTable.IdIdListImage());
    System.out.println(global.filterUnitTable.IdIdListImage());
    /*
    for (int i = 0; i < Constants.dimensions; i++) {
      System.out.println(global.finerUnitGraphs[i].IdIdListImage());
      System.out.println(global.measureTables[i].IdIdImage());
      System.out.println(global.coarserUnitGraphs[i].IdIdListImage());
      }
    */
    }
}


