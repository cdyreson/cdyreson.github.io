package cube.globals;

import java.io.*;
import java.util.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;
import cube.configure.*;

/**
* A Dimension is a collection of graphs and tables that encode all the
* units and measures for that dimension.
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
public class Dimension {
  public Database myDatabase;
  public PersistentGraph finerUnitGraph;
  public PersistentGraph coarserUnitGraph;
  public PersistentGraph finerMeasureGraph;
  public PersistentGraph coarserMeasureGraph;
  public Table unitToMeasureTable;

  public Dimension(String s) {
    myDatabase = new Database(Constants.myDatabaseName);
    finerUnitGraph = new PersistentGraph(myDatabase, Constants.finerUnitGraphName+s);
    coarserUnitGraph = new PersistentGraph(myDatabase, Constants.coarserUnitGraphName+s);
    finerMeasureGraph = new PersistentGraph(myDatabase, Constants.finerMeasureGraphName+s);
    coarserMeasureGraph = new PersistentGraph(myDatabase, Constants.coarserMeasureGraphName+s);
    unitToMeasureTable = myDatabase.createTable(Constants.unitToMeasureTableName+s);
    }
}

