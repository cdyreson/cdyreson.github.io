import java.io.*;
import java.util.*;
import cube.globals.*;
import cube.configure.*;
import cube.database.*;
import cube.persistent.*;
import cube.tools.*;
import cube.cubette.*;

class Partial {
  public static void main(String argv[]) {
    Cubette cubette = new Cubette();
    Globals global = new Globals();
    Id[] u = new Id[Constants.dimensions];
    Id[] m = new Id[Constants.dimensions];
    IdSet[] queryUnitsAbove = new IdSet[Constants.dimensions];
    IdSet[] queryMeasuresBelow = new IdSet[Constants.dimensions];

    u[0] = Convert.toId("Australia");
    u[1] = Convert.toId("December 1997");
    u[2] = Convert.toId("CS Home Page");
    m[0] = Convert.toId("countries");
    m[1] = Convert.toId("months");
    m[2] = Convert.toId("pages");

    // Create a specification for the query
    CubetteSpecification querySpec = new CubetteSpecification(u,m);

    // Test each filter for satisfaction in the query
    for (Enumeration e = global.filterUnitTable.enumerateKeys();
         e.hasMoreElements();) {
      Id key = new Id((byte[])e.nextElement());

      // Create a specification for each cubette
      CubetteSpecification testSpec = new CubetteSpecification(key, 
        global.filterUnitTable, 
        global.filterMeasureTable);

      Id[] tu = testSpec.getUnits().toIdArray();
      Id[] tm = testSpec.getMeasures().toIdArray();

      for (int i = 0; i < Constants.dimensions; i++) {
        queryUnitsAbove[i] = global.coarserUnitGraphs[i].reachableSet(tu[i]);
        queryMeasuresBelow[i] = global.finerMeasureGraphs[i].reachableSet(tm[i]);
        }

      boolean condition = cubette.query(
        querySpec,
        queryUnitsAbove,
        queryMeasuresBelow);
      if (condition) {
        System.out.println("Possible " + testSpec.image());
        }
      }
    }

}


