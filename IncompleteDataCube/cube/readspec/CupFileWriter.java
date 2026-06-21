import java.util.*;
import cube.globals.*;
import cube.configure.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;

/**
* Actions for the Cup File parser.
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
*/
class CupFileWriter {
  private String nonterminals = "non terminal symbol\n";
  private String terminals = "terminal token\n";
  private String rules = "\n";
  private Id[] filterUnits = new Id[Constants.dimensions];
  private Id[] filterMeasures = new Id[Constants.dimensions];
  private int withinFilterCount = 0; // can never be more than # of dimensions
  private String count_str = "0";
  private Id filterCount = new Id(0);

  public CupFileWriter() {
    }

  /**
  * Parser action to add a new unit and measure to the current filter
  **/
  public void addFilter(String unit, String measure) {
    filterUnits[withinFilterCount] = new Id(unit);
    filterMeasures[withinFilterCount] = new Id(measure);
    withinFilterCount++;
    }

  /**
  * Parser action to create a new filter in the filter table
  **/
  public void filter(Table filterTable, Table filterUnitTable, Table filterMeasureTable) {
    Tuple f;
    IdSet s;
    for (int i = 0; i < filterUnits.length; i++) {
      Tuple r = new Tuple(filterUnits[i]);
      Tuple t = filterTable.retrieveTuple(r);
      if (t == null) s = new IdSet();
      else s = t.getValueAsIdSet();
      s.insert(filterCount.cloneMe());
      f = new Tuple(filterUnits[i], s);
      filterTable.insertTuple(f);
      }
    f = new Tuple(filterCount, new IdList(filterUnits));
    filterUnitTable.insertTuple(f);
    f = new Tuple(filterCount, new IdList(filterMeasures));
    filterMeasureTable.insertTuple(f);
    filterUnits = new Id[Constants.dimensions];
    filterMeasures = new Id[Constants.dimensions];
    withinFilterCount = 0;
    filterCount.increment();
    }

  /**
  * Convert a unit to Subunits and add an edge to the finer unit graph
  **/
  public void unitToSubunits(
      String unitName, 
      IdSet subunitSet, 
      PersistentGraph finerUnitGraph) {
    Id unitId = new Id(unitName);
    for (Enumeration e = subunitSet.enumerate(); e.hasMoreElements();) {
      Id i = (Id)e.nextElement();
      finerUnitGraph.addEdge(unitId,i);
      }
    }

  /**
  * Add a tuple to the unit to measure table
  **/
  public void unitToMeasure(String unit, String measure, Table unitToMeasureTable) {
    Tuple t = new Tuple(new Id(unit), new Id(measure));
    unitToMeasureTable.insertTuple(t);
    }

}  
