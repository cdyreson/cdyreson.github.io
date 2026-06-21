package cube.cubette;

import java.io.*;
import java.util.*;
import cube.globals.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;
import cube.globals.*;
import cube.configure.*;

/**
* The top-level interface to the federation of cubettes that is an incomplete
* data cube.  This class advertises operations that
* are available on the Federation.
* <P>
* For more information on the cube see the cube
* <A HREF="cube.Overview.html">Overview</A>.
*   <P>
* Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
* Please be aware of the 
* <A HREF="cube.Licence.html">Licence</A>
* and
* <A HREF="cube.Version.html">Version</A>.
* @author Curtis Dyreson 
**/
public class Federation {

  /**
  * The constructor does nothing currently, all the important methods 
  * are static.
  **/
  public Federation() {
    }

  /**
  * Return the result of a query.  Return null if the query cannot 
  * be satisfied.
  */
  public static String getPartialAnswers(
      CubetteSpecification querySpec,
      Globals global) {

    IdSet[] queryUnitsAbove = new IdSet[Constants.dimensions];
    IdSet[] queryMeasuresBelow = new IdSet[Constants.dimensions];
    Id[] u = querySpec.getUnits().toIdArray();
    Id[] m = querySpec.getMeasures().toIdArray();

    for (int i = 0; i < Constants.dimensions; i++) {
      queryUnitsAbove[i] = global.coarserUnitGraphs[i].reachableSet(u[i]);
      queryMeasuresBelow[i] = global.finerMeasureGraphs[i].reachableSet(m[i]);
      }
 
    String s = "";
    for (Enumeration e = global.filterUnitTable.enumerateKeys();
         e.hasMoreElements();) {
      Id key = new Id((byte[])e.nextElement());
      CubetteSpecification testSpec = new CubetteSpecification(key,
        global.filterUnitTable,
        global.filterMeasureTable);
      CubetteSpecification partialSpec = new CubetteSpecification(
        testSpec.getUnits().toIdArray(), m);
      boolean condition = Cubette.query(
        partialSpec,
        queryUnitsAbove,
        queryMeasuresBelow);
      if (condition) {
        s += testSpec.image() + "\n";
        }
      }
    return s;
    }

  /**
  * Return the result of a query.  Return null if the query cannot 
  * be satisfied.
  */
  public static String getQueryResult(
      CubetteSpecification querySpec,
      Globals global) {

    IdSet[] queryUnitsAbove = new IdSet[Constants.dimensions];
    IdSet[] queryMeasuresBelow = new IdSet[Constants.dimensions];
    Id[] u = querySpec.getUnits().toIdArray();
    Id[] m = querySpec.getMeasures().toIdArray();

    for (int i = 0; i < Constants.dimensions; i++) {
      queryUnitsAbove[i] = global.coarserUnitGraphs[i].reachableSet(u[i]);
      queryMeasuresBelow[i] = global.finerMeasureGraphs[i].reachableSet(m[i]);
      }
 
    for (Enumeration e = global.filterUnitTable.enumerateKeys();
         e.hasMoreElements();) {
      Id key = new Id((byte[])e.nextElement());
      CubetteSpecification testSpec = new CubetteSpecification(key,
        global.filterUnitTable,
        global.filterMeasureTable);
      boolean condition = Cubette.query(
        testSpec,
        queryUnitsAbove,
        queryMeasuresBelow);
      if (condition) {
        CubetteSpecification[] answer = Cubette.sum(
             testSpec,
             querySpec,
             global.finerUnitGraphs,
             global.unitToMeasureTables,
             global.countTable);
        // OK we have an answer
        // Format the answer nicely
        String queryString = "";
        for (int k = 0; k < answer.length; k++) {
          queryString += answer[k].image() + "\n";
          }
        return queryString;
        }
      }
    return null;
    }

}
