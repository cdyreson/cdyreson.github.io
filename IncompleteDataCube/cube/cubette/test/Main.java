import java.io.*;
import java.util.*;
import cube.globals.*;
import cube.configure.*;
import cube.database.*;
import cube.persistent.*;
import cube.tools.*;
import cube.cubette.*;

class Main {
  public static void main(String argv[]) {
    Cubette cubette = new Cubette();
    Globals global = new Globals();
    Id[] u = new Id[Constants.dimensions];
    Id[] m = new Id[Constants.dimensions];
    IdSet[] queryUnitsAbove = new IdSet[Constants.dimensions];
    IdSet[] queryMeasuresBelow = new IdSet[Constants.dimensions];

    u[0] = Convert.toId("Australia");
    u[1] = Convert.toId("December 1997");
    //u[2] = Convert.toId("CS Home Page");
    u[2] = Convert.toId("All Pages");
    m[0] = Convert.toId("countries");
    m[1] = Convert.toId("months");
    //m[2] = Convert.toId("pages");
    m[2] = Convert.toId("all");
    for (int i = 0; i < Constants.dimensions; i++) {
      queryUnitsAbove[i] = global.coarserUnitGraphs[i].reachableSet(u[i]);
      System.out.println("Units above for " + i);
      System.out.println(queryUnitsAbove[i].image());
      queryMeasuresBelow[i] = global.finerMeasureGraphs[i].reachableSet(m[i]);
      System.out.println("Measures below for " + i);
      System.out.println(queryMeasuresBelow[i].image());
      }

    CubetteSpecification querySpec = new CubetteSpecification(u, m);
    for (Enumeration e = global.filterUnitTable.enumerateKeys();
         e.hasMoreElements();) {
      byte[] keyAsBytes = (byte[])e.nextElement();
      Id key = new Id(keyAsBytes);
      //System.out.println(key.image());
      CubetteSpecification testSpec = new CubetteSpecification(key, 
        global.filterUnitTable, 
        global.filterMeasureTable);
      System.out.println("TRYING");
      System.out.println(testSpec.image());
      boolean condition = cubette.query(
        testSpec,
        queryUnitsAbove,
        queryMeasuresBelow);
      if (condition) {
        CubetteSpecification[] answer = 
           cubette.sum(
             testSpec, 
             querySpec, 
             global.finerUnitGraphs, 
             global.unitToMeasureTables, 
             global.countTable);
        System.out.println(" And the answer is : ");
        for (int k = 0; k < answer.length; k++) {
          System.out.println(answer[k].image());
          }
        }
      else {
        testSpec = new CubetteSpecification(testSpec.getUnits().toIdArray(), m);
        condition = cubette.query(
          testSpec,
          queryUnitsAbove,
          queryMeasuresBelow);
        if (condition) {
          testSpec = new CubetteSpecification(key, 
                                              global.filterUnitTable, 
                                              global.filterMeasureTable);
          System.out.println("Possible " + testSpec.image());
          }
        }
      }
    }

}


