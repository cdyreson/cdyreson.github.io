package cube.persistent;

import cube.database.*;
import cube.tools.*;
import java.util.*;
import java.io.*;

/**
 * A persistent implementation of a graph.  Each graph is a separate table.
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
 * @see cube.database.Database
 * @see cube.database.Table
 * @author Curtis Dyreson
 */
public class PersistentGraph {

  /**
   * The edges Table in the database
   */
  public Table edges;

  /**
   * Create a new PersistentGraph.  
   * @param db Database where the graph will live
   * @param name Name of the graph (will create a Table with this name!)
   */
  public PersistentGraph (Database db, String name) {
    edges = db.createTable(name);
  }

  /** 
   * Return an Id to IdList image of the graph.
   */
  public String IdIdListImage () {
    return edges.IdIdListImage();
    }

  /** 
   * Return an Id to IdList image of the graph.
   */
  public String IdKeysImage() {
    return edges.IdKeysImage();
    }

  /** 
   * Add an edge. 
   * @param from node that the edge is from
   * @param to node that the edge is to
   */
  public void addEdge (Id from, Id to) {
    IdSet s;
    Tuple t = edges.retrieveTuple(from);
    if (t == null) s = new IdSet();
    else s = t.getValueAsIdSet();
    s.insert(to);
    edges.insertTuple(new Tuple(from, s));
    }

  /**
   * Get the set of nodes that are reachable from this node, with cycles
   * handled correctly!
   * @param from starting node
   */
  public IdSet reachableSet(Id from) {
    // the result set
    IdSet result = new IdSet();
    // put the current node into the result
    result.insert(from);
    // get the nodes reachable from this node
    Tuple t = edges.retrieveTuple(from);
    if (t != null) {
      // for each reachable node
      IdSet toSet = t.getValueAsIdSet();
      for (Enumeration e = toSet.enumerate(); e.hasMoreElements();) {
        Id i = (Id)e.nextElement();
        // is it a member already of the result?
        if (!result.memberOf(i)) 
          // follow the node
          this.reachableSetInner(result, i);
        }
      }
    return result;
    }

  /**
  * Get the set of nodes that are reachable from this node.
  * And allow cycles!
  * @param result - result to this point
  * @param from - starting node
  **/
  private IdSet reachableSetInner(IdSet result, Id from) {
    // put the current node into the result
    result.insert(from);
    // get the nodes reachable from this node
    Tuple t = edges.retrieveTuple(from);
    if (t != null) {
      // for each reachable node
      IdSet toSet = t.getValueAsIdSet();
      for (Enumeration e = toSet.enumerate(); e.hasMoreElements();) {
        Id i = (Id)e.nextElement();
        // is it a member already of the result?
        if (!result.memberOf(i))
          // follow the node
          this.reachableSetInner(result, i);
        }
      }
    return result;
    }
 
  /**
  * Get the set of nodes at the indicated measure 
  * that are reachable from this initial node.
  * Assume for now that the graph is a strict hierarchy,
  * in future we will relax this assumption.
  * @param from The unit to begin searching from.
  * @param stopMeasure The measure at which to stop.
  * @param unitToMeasureTable The table that associates units with their measures.
  */
  public IdSet reachableSetStopAtMeasure(
      Id from,   
      Id stopMeasure,
      Table unitToMeasureTable) {

    return _reachableSetStopAtMeasure(from, stopMeasure, unitToMeasureTable, new IdSet());
    }

  private IdSet _reachableSetStopAtMeasure(
      Id from,   
      Id stopMeasure,
      Table unitToMeasureTable, 
      IdSet result) {

    //System.out.println("from " + from.toString());
    //System.out.println("stop " + stopMeasure.toString());
    // have we reached the stop measure?
    Tuple t = unitToMeasureTable.retrieveTuple(from);
    if (t == null) 
      Internal.Error("No measure for this unit!" + Convert.toString(from));
    Id testMeasure = t.getValueAsId();
    //System.out.println("test " + testMeasure.toString());
    if (stopMeasure.equals(testMeasure)) {
      // put the current node into the result
      result.insert(from);
      return result;
      }

    // get the nodes reachable from this node
    t = edges.retrieveTuple(from);
    if (t != null) {
      // for each reachable node
      IdSet toSet = t.getValueAsIdSet();
      for (Enumeration e = toSet.enumerate(); e.hasMoreElements();) {
        Id i = (Id)e.nextElement();
        // follow the edges from the node
        _reachableSetStopAtMeasure(i,stopMeasure,unitToMeasureTable,result);
        }
      }
    return result;
    }

  /**
  * Save the graph to disk.  Needed?
  */
  public void save() {
    edges.save();
    }
}
