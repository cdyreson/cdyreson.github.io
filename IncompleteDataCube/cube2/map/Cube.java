package cube.applet;

import java.awt.*;
import java.util.*;
import java.applet.Applet;
import cube.globals.*;
import cube.cubette.*;
import cube.configure.*;
import cube.database.*;
import cube.tools.*;
import cube.persistent.*;

 /**
 * This class is the primary cube GUI
 * <P>
 * For more information on the cube see the cube
 * <A HREF="cube.Overview.html">Overview</A>.
 * <BR>
 * Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
 * Please be aware of the 
 * <A HREF="cube.Licence.html">Licence</A>
 * and
 * <A HREF="cube.Version.html">Version</A>.
 * @author Curtis Dyreson and Jason Pountney
 **/
public class Cube extends Applet {

  private Globals global; 
  private List[] measureList; 
  private List[] unitList; 
  private boolean[] measuresLoaded; 

  // the menu is active
  private MenuBar menubar;  
  // set up two menus
  private Menu file, help;  
  // the execButton is to execute the query
  private Button execButton;
  // the redoButtons reload the measures into the Unit list
  private Button[] redoButton; 
  // the drillButtons drill down in the units and measures
  private Button[] drillButton; 
  // the message area 
  //private TextArea message = null;
 
  /**
   * Initialize the GUI
   **/
  public void init() {
    global = new Globals();
    measureList = new List[Constants.dimensions];
    unitList = new List[Constants.dimensions];
    measuresLoaded = new boolean[Constants.dimensions];
    redoButton = new Button[Constants.dimensions];
    drillButton = new Button[Constants.dimensions];

    // Initialise the Unit and Measure Lists, and measuresLoaded list
    for (int i = 0; i < Constants.dimensions; i++) {
      measuresLoaded[i] = true;
      measureList[i] = new List(5,false);
      unitList[i] = new List(5,false);
      Enumeration e = global.measureTables[i].enumerateKeys(); 
      Vector v = new Vector();
      while (e.hasMoreElements()) {
        byte[] g = (byte[])e.nextElement();
        Id k = new Id(g);
        v.addElement(k);
        }
      sortIt(v);
      for (int j = 0; j < v.size(); j++) {
        measureList[i].addItem(v.elementAt(j).toString());
        unitList[i].addItem(v.elementAt(j).toString());
        }
      }

    // Set up menu bar.
    menubar = new MenuBar();
    //this.setMenuBar(menubar);
 
    // Add items to the Main Menu
    file = new Menu("File");
    //file.add(new MenuItem("Query"));
    //file.add(new MenuItem("ReadSpec"));
    //file.add(new MenuItem(""));
    file.add(new MenuItem("Quit!"));
    menubar.add(file);
 
    // Add items to the Help Menu
    help = new Menu("Help");
    help.add(new MenuItem("Help"));
    help.add(new MenuItem("About"));
    menubar.add(help);
    menubar.setHelpMenu(help);
 
    Panel center = new Panel();
    center.setLayout(new GridLayout(2,Constants.dimensions));
 
    // Create the execute query button
    execButton = new Button("Execute Query");
    for (int i = 0; i < Constants.dimensions; i++) {
      redoButton[i] = new Button("Reload Measures");
      drillButton[i] = new Button("Drill Down");
      }
 
    // Create the panels for each measures dimension
    for (int i = 0; i < Constants.dimensions; i++) {
      Panel p = new Panel();
      p = new Panel();
      p.setLayout(new BorderLayout());
      p.add("North", new Label(Constants.dimensionNames[i] + " Measures"));
      p.add("Center", measureList[i]);
      center.add(p);
      p.validate();
      }
 
    // Create the panels for each units dimension
    for (int i = 0; i < Constants.dimensions; i++) {
      Panel bp = new Panel(); 
      bp.setLayout(new BorderLayout());
      bp.add("West", redoButton[i]);
      bp.add("East", drillButton[i]);
      center.add(bp);
      Panel p = new Panel();
      p.setLayout(new BorderLayout());
      p.add("North", new Label(Constants.dimensionNames[i] + " Units"));
      p.add("Center", unitList[i]);
      //p.add("South", redoButton[i]);
      p.add("South", bp);
      center.add(p);
      p.validate();
      }
 
    // Create a panel to handle the execute query button
    Panel buttonPanel = new Panel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(execButton);
    buttonPanel.validate();
 
    setLayout(new BorderLayout());
    add("Center", center);
    add("South", buttonPanel);
    validate();

    }

  /**
   * Handle an event
   **/
    //public boolean action(Event event, Object what) {
    public boolean handleEvent(Event event) {
      switch(event.id) {
      case Event.ACTION_EVENT:

	//  Handle double click on item  
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == this.unitList[i]) drillDown(i);
          }

        // handle menu items   
        if (event.target instanceof MenuItem) {
          if (((String)event.arg).equals("Quit!")) {
            //dispose();
            //System.exit(0);
            }

          if (((String)event.arg).equals ("ReadSpec")) {
            }

          if (((String)event.arg).equals ("Help")) {
            helpBox();
            }

          if (((String)event.arg).equals ("About")) {
            aboutBox();
            }
          }

        // handle buttons
        if (event.target == execButton) {
          runQuery();
          }
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == this.redoButton[i]) { 
            redoMeasuresForUnits(i);
            }
          }
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == drillButton[i]) {
            drillDown(i);
            }
          }

      break;

        //  handle the list boxes (Single click)  

      case Event.LIST_SELECT:
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == this.measureList[i]) selectMeasure(i);
          }
         
        //    Unit lists 
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == this.unitList[i]) updateUnit(i);
          }

      break;

      case Event.LIST_DESELECT:
        return false;

      case Event.WINDOW_ICONIFY:
      case Event.WINDOW_DEICONIFY:
      case Event.WINDOW_MOVED:
        return false;

      case Event.WINDOW_DESTROY:
        return true;

      case Event.MOUSE_DOWN:
      case Event.MOUSE_UP:
      case Event.MOUSE_DRAG:
        return false;

      case Event.KEY_PRESS:
      case Event.KEY_ACTION:
      case Event.KEY_RELEASE:
      case Event.KEY_ACTION_RELEASE:
        return false;

      case Event.GOT_FOCUS:
      case Event.LOST_FOCUS:
      case Event.MOUSE_ENTER:
      case Event.MOUSE_EXIT:
      case Event.MOUSE_MOVE:
        return false;

      default:
        // do nothing
        return false;
      }
    return true;
    }

  /**
  * Emit a message.
  **/
  private void emitMessage(String title, String message) {
    MessageHandler msg = new MessageHandler("Query Result", message);
    msg.pack();
    msg.show();
    }

  /**
  * Execute a query using the selected items!  
  * Currently, just print the name of the query.
  **/
  public void runQuery() {
    //Cubette cubette = new Cubette();
    String queryString = "The query is\n";
    Id[] u = new Id[Constants.dimensions];
    Id[] m = new Id[Constants.dimensions];
    IdSet[] queryUnitsAbove = new IdSet[Constants.dimensions];
    IdSet[] queryMeasuresBelow = new IdSet[Constants.dimensions];
    for (int i = 0; i < Constants.dimensions; i++) {
      if (unitList[i].getSelectedItem() == null) {
        String message = "No unit selected for the " +
                         Constants.dimensionNames[i] + 
                         " dimension.\n";
        emitMessage("Query Result", message);
        return;
        }
      }
       
    for (int i = 0; i < Constants.dimensions; i++) {
      if (measureList[i].getSelectedItem() == null) {
        String message = "No measure selected for the " +
                         Constants.dimensionNames[i] + 
                         " dimension.\n";
        emitMessage("Query Result", message);
        return;
        }
      }
       
    for (int i = 0; i < Constants.dimensions; i++) {
      // Create a pretty picture of the query 
      queryString += "  " +
                     unitList[i].getSelectedItem() + 
                     "@" + 
                     measureList[i].getSelectedItem() + "\n";
      u[i] = new Id(unitList[i].getSelectedItem());
      m[i] = new Id(measureList[i].getSelectedItem());
      queryUnitsAbove[i] = global.coarserUnitGraphs[i].reachableSet(u[i]);
      queryMeasuresBelow[i] = global.finerMeasureGraphs[i].reachableSet(m[i]);
      }
 
    CubetteSpecification querySpec = new CubetteSpecification(u, m);
    for (Enumeration e = global.filterUnitTable.enumerateKeys();
         e.hasMoreElements();) {
      byte[] keyAsBytes = (byte[])e.nextElement();
      Id key = new Id(keyAsBytes);
      CubetteSpecification testSpec = new CubetteSpecification(key, 
        global.filterUnitTable, 
        global.filterMeasureTable);
      boolean condition = Cubette.query(
        testSpec,
        queryUnitsAbove,
        queryMeasuresBelow);
      if (condition) {
        CubetteSpecification[] answer = Cubette.sum(
             testSpec, 
             querySpec, 
             global.finerUnitGraphs, 
             global.unitToMeasureTables, 
             global.countTable);
        // OK we have an answer
        // Format the answer nicely
        for (int k = 0; k < answer.length; k++) {
          queryString += answer[k].image() + "\n";
          }
        emitMessage("Query Result", queryString);
        return;
        }
      }
    queryString += "\nInsufficient Information to Answer Query.\n";
    emitMessage("Query Result", queryString);
    return;
    }
  
  /**
  * A double click on an item in the Unit List, depending on the 
  * state, take the appropriate action.
  **/
  public void drillDown(int dimension) {
    // Treat as a single click if on a measure
    if (measuresLoaded[dimension]) {
      updateUnit(dimension);
      return;
      }

    List selectedUnitList = unitList[dimension];
    Id unit = new Id(selectedUnitList.getSelectedItem());
    Tuple r = global.finerUnitGraphs[dimension].edges.retrieveTuple(unit);
    Table t = global.finerUnitGraphs[dimension].edges;
    //unitList[dimension].addItem("xxxx" + t.size() + "gggg");
    //for (Enumeration e = t.enumerateKeys(); e.hasMoreElements();) {
    //  Id u = new Id((byte[])e.nextElement());
    //  selectedUnitList.addItem(u.toString());
    //  }
    
    // Are there any edges out of here?
    if (r != null) {
      // Yes we have edges 
      selectedUnitList.clear();
      IdSet units = r.getValueAsIdSet();
      Vector v = new Vector();
      for (Enumeration e = units.enumerate(); e.hasMoreElements();) {
        v.addElement((Id)e.nextElement());
        }
      sortIt(v);
      for (int j = 0; j < v.size(); j++) {
        selectedUnitList.addItem(v.elementAt(j).toString());
        }
      }
    }

  /**
  * They've highlighted a Unit, now lets show all the subunits within
  * this unit (plus this unit).
  * @param name - a list containing the selected unit
  **/
  public void updateUnit(int dimension) {
    List selectedUnitList = unitList[dimension];
    List selectedMeasureList = measureList[dimension];
    Id testId = new Id(selectedUnitList.getSelectedItem());

    // Were the measures previously loaded?  In this case, we load all the
    // units from this measure to the measure selected in the measures window
    if (measuresLoaded[dimension]) {
      measuresLoaded[dimension] = false;
      Id stopMeasure = testId;

      selectedUnitList.clear();
      // Only take the units at this measure
      IdSet stopMeasureSet = 
      //  global.coarserMeasureGraphs[dimension].reachableSet(stopMeasure);
          new IdSet();
      stopMeasureSet.insert(stopMeasure);
      
      Vector v = new Vector();
      for (Enumeration e = 
             global.unitToMeasureTables[dimension].enumerateKeys();
           e.hasMoreElements();) {
        byte[] g = (byte[])e.nextElement();
 
        Tuple t = global.unitToMeasureTables[dimension].retrieveTuple(new Id(g));
        if (t == null) Internal.Error("no measure for this unit");
        Id tempMeasure = t.getValueAsId();
        if (stopMeasureSet.memberOf(tempMeasure)) {
          v.addElement(new Id(g));
          }
        }
      sortIt(v);
      for (int j = 0; j < v.size(); j++) {
        selectedUnitList.addItem(v.elementAt(j).toString());
        }
      }
    // else we selected a unit, stay calm 
    }

  /**
   * Reload the measures into the appropriate unit list
   * @param i - which dimension
   **/
  public void redoMeasuresForUnits(int i) {
   // If a measure was not previously selected then load them all
   if (measureList[i].getSelectedItem() == null) {
     Vector v = new Vector();
     measureList[i].clear();
     for (Enumeration e = global.measureTables[i].enumerateKeys(); 
          e.hasMoreElements();) {
       byte[] g = (byte[])e.nextElement();
       Id k = new Id(g);
       v.addElement(k);
       }
      sortIt(v);
      for (int j = 0; j < v.size(); j++) {
        measureList[i].addItem(v.elementAt(j).toString());
        }
     }
   // A measure was previously selected then load only the measures above it
   else {
     Id measure = new Id(measureList[i].getSelectedItem());
     IdSet measureSet = global.coarserMeasureGraphs[i].reachableSet(measure);
     measureSet.insert(measure);
     unitList[i].clear();
     Vector v = new Vector();

     for (Enumeration e = measureSet.enumerate(); e.hasMoreElements();) {
       Id k = (Id)e.nextElement();
       v.addElement(k);
       }
     sortIt(v);
     for (int j = 0; j < v.size(); j++) {
       unitList[i].addItem(v.elementAt(j).toString());
       }
     measuresLoaded[i] = true;
     }
   }

  /**
   * Display the units in this list
   * @param name - list of units
   **/
  public void selectMeasure(int i) {
    Id measure = new Id(measureList[i].getSelectedItem());
    }
 
  /**
   * helpBox - Action to take when the help box is selected
   **/
  public void helpBox() {
    MessageHandler msg = new MessageHandler("No Help Sorry", "Refer to http://www.cs.jcu.edu.au/~curtis/htmls/IncompleteDataCube.html for help.");
    msg.pack();
    msg.show();
    }
 
  /**
   * aboutBox - Action to take when the about box is selected
   **/
  public void aboutBox() {
    MessageHandler msg = new MessageHandler("About", 
      "An IncompleteDataCube was designed by Curtis E. Dyreson at\n" +
      "James Cook University in Townsville, Queensland, Australia.\n" +
      "Curtis and Jason Pountey wrote the Java code with help from\n" +
      "the Jigsaw, JavaLex, and JavaCup designers.\n"
      );
    msg.pack();
    msg.show();
    }

/*
 * @(#)QSortAlgorithm.java	1.6 96/12/06
 *
 * Copyright (c) 1994-1996 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

/**
 * A quick sort demonstration algorithm
 * SortAlgorithm.java
 *
 * @author James Gosling
 * @author Kevin A. Smith
 * @version 	@(#)QSortAlgorithm.java	1.3, 29 Feb 1996
 */

   /** This is a generic version of C.A.R Hoare's Quick Sort 
    * algorithm.  This will handle arrays that are already
    * sorted, and arrays with duplicate keys.<BR>
    *
    * If you think of a one dimensional array as going from
    * the lowest index on the left to the highest index on the right
    * then the parameters to this function are lowest index or
    * left and highest index or right.  The first time you call
    * this function it will be with the parameters 0, a.length - 1.
    *
    * @param a       an integer array
    * @param lo0     left boundary of array partition
    * @param hi0     right boundary of array partition
    */
   void QuickSort(Vector v, int lo0, int hi0) 
   {
      int lo = lo0;
      int hi = hi0;
      Id mid;

      if ( hi0 > lo0)
      {

         /* Arbitrarily establishing partition element as the midpoint of
          * the array.
          */
         mid = (Id)v.elementAt( ( lo0 + hi0 ) / 2 );

         // loop through the array until indices cross
         while( lo <= hi )
         {
            /* find the first element that is greater than or equal to 
             * the partition element starting from the left Index.
             */
	     while( ( lo < hi0 ) && ( ((Id)v.elementAt(lo)).lt(mid) ))
		 ++lo;

            /* find an element that is smaller than or equal to 
             * the partition element starting from the right Index.
             */
	     while( ( hi > lo0 ) && ( mid.lt((Id)v.elementAt(hi)) ))
		 --hi;

            // if the indexes have not crossed, swap
            if( lo <= hi ) 
            {
               swap(v, lo, hi);
               ++lo;
               --hi;
            }
         }

         /* If the right index has not reached the left side of array
          * must now sort the left partition.
          */
         if( lo0 < hi )
            QuickSort( v, lo0, hi );

         /* If the left index has not reached the right side of array
          * must now sort the right partition.
          */
         if( lo < hi0 )
            QuickSort( v, lo, hi0 );

      }
   }

   private void swap(Vector v, int i, int j)
   {
      Id T;
      T = (Id)v.elementAt(i); 
      v.setElementAt(v.elementAt(j), i);
      v.setElementAt(T, j);
   }

   private void sortIt(Vector v) 
   {
      QuickSort(v, 0, v.size() - 1);
   }

}
