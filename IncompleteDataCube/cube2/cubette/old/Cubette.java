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
* The top-level interface to a complete sub-cube within the incomplete 
* data cube.
* A Cubette is a complete sub-cube.  This class advertises operations that
* are available on Cubettes.
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
public class Cubette {

  /**
  * Determine if a query is satisfied by the specified Cubette.
  * Return true if the query can be satisfied, false otherwise.  For more
  * on satisfaction, see the relevant papers (e.g., the VLDB paper).
  */
  public static boolean query(
      CubetteSpecification cubetteSpec,
      IdSet[] queryUnitsAbove,
      IdSet[] queryMeasuresBelow) {

    // cycle through the query units and measures making sure they meet criteria
    for (int i = 0; i < Constants.dimensions; i++) {
      Id measure = (cubetteSpec.getMeasures().toIdArray())[i];
      Id unit = (cubetteSpec.getUnits().toIdArray())[i];
      IdSet measureSet = queryMeasuresBelow[i];
      IdSet unitSet = queryUnitsAbove[i];
      if (!(measureSet.memberOf(measure))) return false;
      if (!(unitSet.memberOf(unit))) return false;
      }  

    // We have a satisfying cubette!  
    return true;
    }

  /** 
  * Compute the query units.
  * Only call this after you have assured that the cubette is satisfying!
  * For now we will assume a strict hierarchy.
  */
  private static CubetteSpecification[] innerCubettes(
      CubetteSpecification query,
      PersistentGraph[] finerUnitGraphs,
      Table[] unitToMeasureTables) {

    // load cubette unit and measures off disk
    IdSet[] innerUnits = new IdSet[Constants.dimensions];
    
    // get all the units at the query measure.
    Id[] queryUnits = query.getUnits().toIdArray();
    Id[] queryMeasures = query.getMeasures().toIdArray();
    for (int i = 0; i < Constants.dimensions; i++) {
      innerUnits[i] = 
        finerUnitGraphs[i].reachableSetStopAtMeasure(
           queryUnits[i], 
           queryMeasures[i], 
           unitToMeasureTables[i]);
      }  

    // now take the crossproduct of each combination of the real query units
    return crossProduct(innerUnits,queryMeasures);
    }

  /**
  * Determine the cross product in terms of which cubettes, of a set
  * of units and a list of measures (so all the units at a particular measure).
  **/
  private static CubetteSpecification[] crossProduct(IdSet[] units, Id[] measures) {
    Id counters[] = new Id[measures.length];
    Id tempUnits[] = new Id[measures.length];
    Hashtable cubetteSpecs = new Hashtable();
 
    // Allocate enough space to hold the result
    for (Enumeration e = units[0].enumerate(); e.hasMoreElements();) {
      Id i = (Id)e.nextElement();
      Id[] sofar = new Id[measures.length];
      sofar[0] = i;
      innerCrossProduct(1, units, measures, sofar, cubetteSpecs);
      }

    CubetteSpecification[] result = new CubetteSpecification[cubetteSpecs.size()];
    int count = 0;
    for (Enumeration e = cubetteSpecs.keys(); e.hasMoreElements();) {
      result[count] = (CubetteSpecification)e.nextElement();
      count++;
      }
    return result;
    }
    
  /**
  * Compute the inner cross product, a helper function for CrossProduct.
  **/
  private static void innerCrossProduct(
     int current,
     IdSet[] units, 
     Id[] measures,
     Id[] sofar,
     Hashtable cubetteSpecs) {

    // Have we run out of dimensions?
    if (current == measures.length) {
      // TODO: I probably don't have to clone the measures here
      CubetteSpecification temp = new CubetteSpecification(((Id[])(Object)sofar.clone()), ((Id[])(Object)measures.clone()));
      cubetteSpecs.put(temp,"");
      return;
      }

    // Do another dimension
    for (Enumeration e = units[current].enumerate(); e.hasMoreElements();) {
      Id i = (Id)e.nextElement();
      sofar[current] = i;
      innerCrossProduct(current + 1, units, measures, sofar, cubetteSpecs);
      }
    }

  /** 
  * Compute a sum aggregate for the answer.
  * Only call this after you have assured that the cubette is satisfying!
  * For now we will assume a strict hierarchy.
  */
  public static CubetteSpecification[] sum(
      CubetteSpecification satisfyingCubette,
      CubetteSpecification query,
      PersistentGraph[] finerUnitGraphs,
      Table[] unitToMeasureTables,
      Table countTable) {

    // Get all the cubettes within the query, the query might ask for lots
    // of points.
    CubetteSpecification querySpecs[] = innerCubettes(
      query,
      finerUnitGraphs,
      unitToMeasureTables);
 
    // for each part of the query
    for (int i = 0; i < querySpecs.length; i++) {
      CubetteSpecification cubette = querySpecs[i];
     
      // Get all cubettes in the base of the cubette for each point in the query
      CubetteSpecification baseSpecs[] = 
        innerCubettes(
          new CubetteSpecification(cubette.getUnits().toIdArray(), 
                                   satisfyingCubette.getMeasures().toIdArray()),
          finerUnitGraphs,
          unitToMeasureTables);

      // Sum over each base unit to obtain the result
      IntegerCol newSum = new IntegerCol(0);
      for (int j = 0; j < baseSpecs.length; j++) {
        Tuple t = countTable.retrieveTuple(baseSpecs[j].getUnits());
        if (t != null) 
          // found tuple, add it to running sum
          newSum.increment(t.getValueAsIntegerCol());
        }
      cubette.value = newSum;
      }
    return querySpecs;
    } 

  /**
  * Increment the count associated with this cubette.
  **/
  public static void increment(
      Id cubette,
      LogRecord log, 
      Table filterUnitTable,
      Table filterMeasureTable,
      Table countTable,
      Hashtable doneThisRound) {

    Tuple m = filterMeasureTable.retrieveTuple(cubette);
    if (m == null) Internal.Error("No measures for this filter!");
    IdList measures = m.getValueAsIdList();

    // find the units for the count
    IdList countList = new IdList(log.unitsAtMeasures(measures.toIdArray()));
    Tuple t = countTable.retrieveTuple(countList);
    IntegerCol count = (t == null)? new IntegerCol(0): t.getValueAsIntegerCol();
    count.increment();
    countTable.insertTuple(new Tuple(countList, count));
    System.out.println("increment count for " + countList.image());
    doneThisRound.put(countList.toBytes()," ");
    }
}
