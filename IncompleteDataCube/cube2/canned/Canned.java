package cube.canned;

import java.io.*;
import java.util.*;
import cube.tools.*;
import cube.configure.*;
import cube.database.*;
import cube.persistent.*;
import cube.globals.*;

/**
* Create a canned image of each table in the database
**/
public class Canned {

  public static void main(String args[]) {
    Globals globals = new Globals();

    //System.out.println("import java.io.*;");
    System.out.println("package w3c.tools.dbm;");
    System.out.println("import java.util.*;");
    System.out.println("import cube.tools.*;");
    System.out.println("public class InitTables {");
    System.out.println("  public static Hashtable whichInit(String s) {");
    System.out.println("");
    int count = 1;
    emitTable(Constants.filterTableName, count++);
    emitTable(Constants.filterUnitTableName, count++);
    emitTable(Constants.filterMeasureTableName, count++);
    emitTable(Constants.countTableName, count++);
    emitTable(Constants.idTableName, count++);
    emitTable(Constants.stringTableName, count++);
    for (int i =0; i < Constants.dimensions; i++) {
      emitTable(Constants.dimensionNames[i] +
                Constants.coarserUnitGraphName, count++);
      emitTable(Constants.dimensionNames[i] +
                Constants.finerUnitGraphName, count++);
      emitTable(Constants.dimensionNames[i] +
                Constants.coarserMeasureGraphName, count++);
      emitTable(Constants.dimensionNames[i] +
                Constants.finerMeasureGraphName, count++);
      emitTable(Constants.dimensionNames[i] +
                Constants.unitToMeasureTableName, count++);
      emitTable(Constants.dimensionNames[i] +
                Constants.measureTableName, count++);
      }
    System.out.println("   Internal.Error(\"Died opening \" + s);");
    System.out.println("   return null;");
    System.out.println("   } // end of whichInit");

    count = 1;
    globals.filterTable.emitCannedInitializationCode(count++);
    globals.filterUnitTable.emitCannedInitializationCode(count++);
    globals.filterMeasureTable.emitCannedInitializationCode(count++);
    globals.countTable.emitCannedInitializationCode(count++);
    Database db = new Database(Constants.myDatabaseName);
    Table IdToStringTable = db.createTable(Constants.idTableName);
    Table StringToIdTable = db.createTable(Constants.stringTableName);
    IdToStringTable.emitCannedInitializationCode(count++);
    StringToIdTable.emitCannedInitializationCode(count++);
    for (int i =0; i < Constants.dimensions; i++) {
      globals.coarserUnitGraphs[i].edges.emitCannedInitializationCode(count++);
      globals.finerUnitGraphs[i].edges.emitCannedInitializationCode(count++);
      globals.coarserMeasureGraphs[i].edges.emitCannedInitializationCode(count++);
      globals.finerMeasureGraphs[i].edges.emitCannedInitializationCode(count++);
      globals.unitToMeasureTables[i].emitCannedInitializationCode(count++);
      globals.measureTables[i].emitCannedInitializationCode(count++);
      }
    System.out.println("} // end of class");

    }

  private static void emitTable(String name, int which) {
    System.out.println("    if (s.compareTo(\"" + Constants.myDatabaseName +
                         "/" + name +
                         "\") == 0) {return init" + which + "();}");
    }

  private String toString(byte valueAsBytes[]) {
    // Convert the value byte array to a value String 
    char[] valueAsChars = new char[valueAsBytes.length];
    ByteArrayInputStream valueStream = new ByteArrayInputStream(valueAsBytes);
    InputStreamReader reader = new InputStreamReader(valueStream);
    try {
      reader.read(valueAsChars, 0, valueAsChars.length);
      } catch (IOException e) {
        Internal.Error("in ByteArrayToString");
      } 
 
    return new String(valueAsChars);
    }

}

