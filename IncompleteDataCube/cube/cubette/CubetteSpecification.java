package cube.cubette;

import java.io.*;
import java.util.*;
import cube.globals.*;
import cube.tools.*;
import cube.database.*;
import cube.persistent.*;
import cube.configure.*;

/**
* The CubetteSpecification class implements the specification of a cubette
* in terms of units and measures
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
public class CubetteSpecification {
  private IdList units;
  private IdList measures;
  public IntegerCol value;
  private int hashCode = 0;

  /**
  * Construct a specification 
  * @param units - a list of units
  * @param measures - a list of measures
  **/
  public CubetteSpecification(Id[] units, Id[] measures) {
    this.units = new IdList(units);
    this.measures = new IdList(measures);
    this.value = new IntegerCol(0);
    }

  /**
  * These can be used in Hashtables of all cubette specifications
  **/
  public int hashCode() {
    if (hashCode == 0) {
      hashCode = units.hashCode();
      hashCode += measures.hashCode();
      }
    return hashCode;
    }

  /**
  * These can be used in Hashtables of all cubette specifications
  * @param obj - Will die a horrible death unless it is a cubette spec
  **/
  public boolean equals(Object obj) {
    CubetteSpecification other = (CubetteSpecification)obj;
    IdList units = other.getUnits();
    IdList measures = other.getMeasures();
    return units.equals(this.units) && measures.equals(this.measures);
    }

  /**
  * Construct a specification 
  * @param cubette - which cubette 
  * @param filterUnitTable - a table of all filters, indexed by unit
  * @param filterMeasureTable - a table of all filters, indexed by measure
  **/
  public CubetteSpecification(Id cubette, Table filterUnitTable, Table filterMeasureTable) {
    // load cubette unit and measures off disk
    Id[] units = new Id[Constants.dimensions];
    Id[] measures = new Id[Constants.dimensions];
    Tuple u = filterUnitTable.retrieveTuple(cubette);
    if (u == null) Internal.Error("No units for this filter!");
    this.units = u.getValueAsIdList();
    Tuple m = filterMeasureTable.retrieveTuple(cubette);
    if (m == null) Internal.Error("No measures for this filter!");
    this.measures = m.getValueAsIdList();
    this.value = new IntegerCol(0);
    }

  /**
  * Format the specification as a nice looking string
  **/
  public String image() {
    String s = "";
    s += value.image();
    String separator = " == ";
    for (Enumeration e = units.enumerate(); e.hasMoreElements();) {
      Id id = (Id)e.nextElement();
      s += separator + Convert.toString(id);
      separator = "|";
      }
    separator = "\n      ";
    for (Enumeration e = measures.enumerate(); e.hasMoreElements();) {
      Id id = (Id)e.nextElement();
      s += separator + Convert.toString(id);
      separator = "|";
      }
    return s;
    }

  /**
  * Get the units for this Cubette.
  */
  public IdList getUnits() { return units; }

  /**
  * Get the measures for this Cubette.
  */
  public IdList getMeasures() { return measures; }
}
