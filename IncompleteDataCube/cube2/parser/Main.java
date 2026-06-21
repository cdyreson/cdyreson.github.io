import java.io.*;
import java.util.*;
import cube.globals.*;
import cube.configure.*;
import cube.database.*;
import cube.persistent.*;
import cube.tools.*;
import cube.cubette.*;

/**
* Parse the output of the flex program that parses the access log
* <P>
* For more information on the cube see the cube
* <A HREF="cube.Overview.html">Overview</A>.
* <BR>
* Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
* Please be aware of the 
* <A HREF="cube.Licence.html">Licence</A>
* and
* <A HREF="cube.Version.html">Version</A>.
*
* @author Curtis Dyreson 
**/
class Main {
  public static void main(String argv[]) {
    Globals global = new Globals();
    Hashtable doneThisRound = new Hashtable();
    Id[] v = new Id[Constants.dimensions];
    int tokens = 0;
    boolean newRound = true;
    lexer.init();
    for (int token = lexer.next_token(); token >= -1; token = lexer.next_token()) {
      if (newRound) {
        //System.out.println("NEXT RECORD");
        doneThisRound.clear();
        tokens = 0;
        newRound = false;
        }
      if (token > 0 ) v[tokens++] = new Id(token);
      else if (token == -1) newRound = true;
      else if (tokens != Constants.dimensions) tokens = 0;
      else {
        // We have a potential candidate
        IdSet f;
        int count;
             
        tokens = 0;
        //for (int i = 0; i < Constants.dimensions; i++) {
        //  System.out.print(v[i].toString() + " ");
        //  }
        //System.out.println();

        // Find all the active filters for the token units
        IdSet cubetteSet = CountIt.start(0, v, 
                                         global.coarserUnitGraphs, 
                                         global.filterTable);
        //System.out.println(cubetteSet.numberOfElements());
        if (cubetteSet.numberOfElements() > 0) {
          LogRecord log = new LogRecord(v, 
                                        global.unitToMeasureTables, 
                                        global.coarserUnitGraphs);
          // Enumerate the cubettes matching this record
          //doneThisRound.clear();
          for (Enumeration e = cubetteSet.enumerate(); e.hasMoreElements();) {
            // Increment the cubette
            Id cubette = ((Id)e.nextElement());

            Cubette.increment(
                    cubette,
                    log, 
                    global.filterUnitTable,
                    global.filterMeasureTable,
                    global.countTable,
                    doneThisRound);
            }
          }
        }
      }
    global.countTable.save();
    }
}
