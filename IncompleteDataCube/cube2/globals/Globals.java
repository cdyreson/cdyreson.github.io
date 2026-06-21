package cube.globals;

import java.io.*;
import java.util.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;
import cube.configure.*;

/**
*  The Globals class holds all the global variables (Tables and Graphs)
*  for the cube.
* <P>
* For more information on the cube see the cube
* <A HREF="cube.Overview.html">Overview</A>.
* <BR>
* Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
* Please be aware of the 
* <A HREF="cube.Licence.html">Licence</A>
* and
* <A HREF="cube.Version.html">Version</A>.
* @author Curtis Dyreson 
**/
public class Globals {
  /**
  * The database where everything is stored.
  */
  public Database myDatabase;
  /**
  * A table of available filters.
  */
  public Table filterTable;
  /**
  * A table that maps a filter number to a list of units for that filer.
  */
  public Table filterUnitTable;
  /**
  * A table that maps a filter number to a list of measures for that filer.
  */
  public Table filterMeasureTable;
  /**
  * The count for Cubettes (the actuall data store for the data cube). 
  */
  public Table countTable;
  /**
  * Each unit to measure table maps a unit to the measure to which it belongs.
  * There is one table for each dimension.
  */
  public Table[] unitToMeasureTables = new Table[Constants.dimensions];
  /**
  * Each measure table is a list of measures for that dimension.
  * There is one table for each dimension.
  */
  public Table[] measureTables = new Table[Constants.dimensions];
  /**
  * The finer unit graphs map each coarser unit to a set of finer units that
  * are contained by it (but not the transitive closure, just the minimal 
  * set of edges).
  * There is one graph for each dimension.
  */
  public PersistentGraph[] finerUnitGraphs = new PersistentGraph[Constants.dimensions];
  /**
  * The finer measure graphs map each coarser measure to a set of finer measures
  * that are below it in the measure hierarchy (but not the transitive closure, 
  * just the minimal set of edges).
  * There is one graph for each dimension.
  */
  public PersistentGraph[] finerMeasureGraphs = new PersistentGraph[Constants.dimensions];
  /**
  * The coarser unit graphs map each finer unit to a set of coarser units 
  * that contain it (but not the transitive closure, just the minimal set
  * of edges).
  * There is one graph for each dimension.
  */
  public PersistentGraph[] coarserUnitGraphs = new PersistentGraph[Constants.dimensions];
  /**
  * The coarser measure graphs map each finer measure to a set of coarser 
  * measures
  * that are just above it in the measure hierarchy (but not the transitive 
  * closure, just the minimal set of edges).
  * There is one graph for each dimension.
  */
  public PersistentGraph[] coarserMeasureGraphs = new PersistentGraph[Constants.dimensions];

  /**
  * Open all the global Tables and Graphs by creating a Globals object.
  */
  public Globals() {
    myDatabase = new Database(Constants.myDatabaseName);
    filterTable = myDatabase.createTable(Constants.filterTableName);
    filterUnitTable = myDatabase.createTable(Constants.filterUnitTableName);
    filterMeasureTable = myDatabase.createTable(Constants.filterMeasureTableName);
    countTable = myDatabase.createTable(Constants.countTableName);
    for (int i = 0; i < Constants.dimensions; i++) {
      measureTables[i] = myDatabase.createTable(
       Constants.dimensionNames[i] + Constants.measureTableName
       );
      unitToMeasureTables[i] = myDatabase.createTable(
       Constants.dimensionNames[i] + Constants.unitToMeasureTableName
       );
      finerUnitGraphs[i] = new PersistentGraph(myDatabase,
       Constants.dimensionNames[i] + Constants.finerUnitGraphName
       );
      coarserUnitGraphs[i] = new PersistentGraph(myDatabase,
       Constants.dimensionNames[i] + Constants.coarserUnitGraphName
       );
      finerMeasureGraphs[i] = new PersistentGraph(myDatabase,
       Constants.dimensionNames[i] + Constants.finerMeasureGraphName
       );
      coarserMeasureGraphs[i] = new PersistentGraph(myDatabase,
       Constants.dimensionNames[i] + Constants.coarserMeasureGraphName
       );
      }
    }

  /**
  * Save all the open global Tables and Graphs.
  */
  public void save() {
    for (int i =0; i < Constants.dimensions; i++) {
      coarserUnitGraphs[i].save();
      finerUnitGraphs[i].save();
      coarserMeasureGraphs[i].save();
      finerMeasureGraphs[i].save();
      unitToMeasureTables[i].save();
      measureTables[i].save();
      unitToMeasureTables[i].save();
      }
    filterTable.save();
    filterUnitTable.save();
    filterMeasureTable.save();
    }
}

