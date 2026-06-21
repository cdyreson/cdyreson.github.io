package cube.configure;

import java.io.*;
import java.util.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;

/**
* This class contains global constants.
* Configure this class as necessary.
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
public class Constants {
  //--------------------------------------------------------------------
  // Configure the following constants
  //--------------------------------------------------------------------
  /** 
  * What are the names of the dimensions? 
  **/
  public static final String[] dimensionNames = {"Machine", "Time", "Page"};
  /** 
  * What is the home directory for the cube? 
  **/
  //public static final String cubeHome = "/net/zeus/facstaff/cdyreson/myclasses/cube/";
  public static final String cubeHome = "";
  /** 
  * What is the home directory for the dimension specifications? 
  **/
  public static final String cubeInputPath = cubeHome + "specifications/";
  /** 
  * What is the home directory for the created databases? 
  **/
  //public static final String cubeDbs = cubeHome + "dbs";
  public static final String cubeDbs = cubeHome;
  //public static final String myDatabaseName = cubeHome + "dbs";
  public static final String myDatabaseName = cubeHome;

  //---------------------------------------------------------------------
  // From here down, none of these should be changed, but they can be
  //---------------------------------------------------------------------
  /** How many dimensions does the cube have? **/
  public static final int dimensions = dimensionNames.length;

  /** What is the name of the file that contains the filters? **/
  public static final String filterFileName = "Filters";

  /** What are the names of the various tables in the database? **/
  public static final String stringTableName = "strings";
  public static final String idTableName = "ids";
  public static final String finerUnitGraphName = "finerUnits";
  public static final String coarserUnitGraphName = "coarserUnits";
  public static final String finerMeasureGraphName = "finerMeasures";
  public static final String coarserMeasureGraphName = "coarserMeasures";
  public static final String unitToMeasureTableName = "unitToMeasure";
  public static final String measureTableName = "measures";
  public static final String filterTableName = "filters";
  public static final String filterUnitTableName = "filterUnits";
  public static final String filterMeasureTableName = "filterMeasures";
  public static final String countTableName = "count";

  /** What is the name of the flex file produced?, not used! **/
  //public static final String lexFileName = cubeHome + "parser/flexer.l";
}
