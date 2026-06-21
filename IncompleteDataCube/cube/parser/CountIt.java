import java.io.*;
import java.util.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;
import cube.globals.*;
import cube.configure.*;

/**
* CountIt contains code to do the counting of records recognized during
* parsing as belonging to one of the specified filters.  It looks through
* all the filters to determine if a given filter matches the current record.
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
public class CountIt {

  /** 
  * Start the counting.
  **/
  public static IdSet start(int index, 
                            Id[] v, 
                            PersistentGraph[] unitGraphs, 
                            Table filterTable) {
    IdSet result = new IdSet();

    if (index == Constants.dimensions) return result;
    PersistentGraph unitGraph = unitGraphs[index];
    Id token = v[index];
    result = getAboveFilters(token, unitGraph, filterTable);
    return possible(index + 1, v, result, unitGraphs, filterTable);
    }

  /** 
  * Check a possible candidate and continue counting.
  **/
  private static IdSet possible(
      int index,
      Id[] v,  
      IdSet sofar, 
      PersistentGraph[] unitGraphs, 
      Table filterTable) {
    IdSet result = new IdSet();

    if (index == Constants.dimensions) return sofar;
    PersistentGraph unitGraph = unitGraphs[index];
    if (sofar.numberOfElements() == 0) return sofar;
    Id token = v[index];
    IdSet above = getAboveFilters(token, unitGraph, filterTable);
    return possible(index + 1, v, above.intersect(sofar), unitGraphs, filterTable);
    }

  /** 
  * Determine all the filters above this unit
  **/
  private static IdSet getAboveFilters(
      Id token, 
      PersistentGraph unitGraph, 
      Table filterTable) {
    IdSet aboveunits = unitGraph.reachableSet(token);
    IdSet above = new IdSet();
    for (Enumeration e = aboveunits.enumerate(); e.hasMoreElements();) {
      Id i = (Id)e.nextElement();
      Tuple t = filterTable.retrieveTuple(i);
      if (t != null) above = above.union(t.getValueAsIdSet());
      }
    //System.out.println("above " + above.numberOfElements());
    return above;
    }

}
