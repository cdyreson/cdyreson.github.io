package cube.cubette;

import java.io.*;
import java.util.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;

/**
* A log record is a structure consisting of units above a unit.
* It is used to update the count associated with filters for each
* record read from a log file.
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
public class LogRecord {
  private Id[] units;
  private Id[] measures;
  Hashtable[] measuresForUnitsHashtables;
  IdSet[] unitsAboveSets;
  IdSet[] activeFilters;

  /**
  * Create a new LogRecord
  * @param units - A list of units to look above in each dimension.
  * @param unitToMeasureTables - Tables that map units to particular measures.
  * @param coarserUnitGraphs - Directed graphs that lead from finer to 
  *   coarser units.
  **/
  public LogRecord(
      Id[] units, 
      Table[] unitToMeasureTables, 
      PersistentGraph[] coarserUnitGraphs) {

    this.units = units;
    measures = new Id[units.length];
    measuresForUnitsHashtables = new Hashtable[units.length];
    unitsAboveSets = new IdSet[units.length];
    // Cycle through each dimension
    for (int i = 0; i < units.length; i++) {
      // get measure for each unit
      measures[i] = (unitToMeasureTables[i].retrieveTuple(units[i])).getValueAsId();
      // Allocate hash tables
      measuresForUnitsHashtables[i] = new Hashtable();
      // Grab the units above this one
      IdSet reachableSet = coarserUnitGraphs[i].reachableSet(units[i]);
      unitsAboveSets[i] = reachableSet;
      // Look at each of the units above
      for (Enumeration e = reachableSet.enumerate(); e.hasMoreElements();) {
        Id reachableUnit = (Id)(e.nextElement());
        // Hash each on the measure
        Id measure = (unitToMeasureTables[i].retrieveTuple(reachableUnit)).getValueAsId();
        measuresForUnitsHashtables[i].put(measure, reachableUnit);
        }
      }
    }

  /**
  * Builds a list of the units in each dimension for a measure
  * @param measures List of measures.
  */
  public Id[] unitsAtMeasures(Id[] measures) {
    Id[] theUnits = new Id[measures.length];
    // Cycle through each dimension
    for (int i = 0; i < measures.length; i++) {
      // find corresponding unit
      theUnits[i] = (Id)(measuresForUnitsHashtables[i].get(measures[i]));
      }
    return theUnits;
    }
   
}
