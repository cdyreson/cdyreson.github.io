package cube.gui;

import java.awt.*;
import java.util.*;
import java.applet.Applet;
import cube.globals.*;
import cube.configure.*;
import cube.database.*;
import cube.tools.*;
import cube.persistent.*;
import cube.cubette.*;

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
public class CubeGUI extends Frame {

  private boolean inAnApplet = true;
  public Globals global; 
  public List[] measureList; 
  public List[] unitList; 
  public boolean[] measuresLoaded; 

  // the menu is active
  private MenuBar menubar;  
  // set up two menus
  private Menu file, help;  
  // the execButton is to execute the query
  protected Button execButton;
  // the redoButtons reload the measures into the Unit list
  protected Button[] redoButton; 
  // the drillButtons drill down in the units and measures
  protected Button[] drillButton; 
 
  /**
   * The starts the GUI
   **/
  public static void main(String args[]) {
    CubeGUI window = new CubeGUI("Incomplete Data Cube");
    window.inAnApplet = false;

    //window.setTitle("Incomplete Data Cube Application");
    window.pack();
    window.show();
    }

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
      for (Enumeration e = global.measureTables[i].enumerateKeys(); e.hasMoreElements();) {
        byte[] g = (byte[])e.nextElement();
        Id k = new Id(g);
 
        measureList[i].addItem(k.toString());
        unitList[i].addItem(k.toString());
        }
      }

    }

  /**
   * Handle an event
   **/
    public boolean action(Event event, Object what) {
    //public boolean handleEvent(Event event) {
      switch(event.id) {
      case Event.ACTION_EVENT:

	//  Handle double click on item  
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == CubeGUI.unitList[i]) selectUnit(i);
          }

        // handle menu items   
        if (event.target instanceof MenuItem) {
          if (((String)event.arg).equals("Quit!")) {
            System.out.println("Quitting");
            dispose();
            System.exit(0);
            }

          if (((String)event.arg).equals("Query")) {
            System.out.println("Query selected");
            }
          if (((String)event.arg).equals ("ReadSpec")) {
            System.out.println("ReadSpec selected");
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
          if (event.target == redoButton[i]) redoMeasuresForUnits(i);
          }
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == drillButton[i]) selectUnit(i);
          }

      break;

        //  handle the list boxes (Single click)  

      case Event.LIST_SELECT:
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == CubeGUI.measureList[i]) selectMeasure(i);
          }
         
        //    Unit lists 
        for (int i = 0; i < Constants.dimensions; i++) {
          if (event.target == CubeGUI.unitList[i]) updateUnit(i);
          }

      break;

      case Event.LIST_DESELECT:
        return false;

      case Event.WINDOW_ICONIFY:
      case Event.WINDOW_DEICONIFY:
      case Event.WINDOW_MOVED:
        return false;

      case Event.WINDOW_DESTROY:
            if (inAnApplet) {
                dispose();
                return true;
            } else {
                dispose();
                //System.exit(0);
            }

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
        System.out.println("Missed one !!");
      break;
    }
    //return super.handleEvent(event);
    return true;
    }

    //MessageHandler m = new MessageHandler("message", "how ya going");
    //m.pack();
    //m.show();

  /**
  * Create a new CubeGUI with a default title.
  */
  public CubeGUI() { 
    this("Incomplete Data Cube");
    }

  /**
   * Build a new graphic user interface, with the given title
   **/
  public CubeGUI(String title, int k) { 

    //Set name of Frame;
    //super(title);  

    this.init();
    // there is a Panel for each dimension
    //Panel[] panel = new Panel[Constants.dimensions];

    // Set up menu bar.
    menubar = new MenuBar();
    this.setMenuBar(menubar);

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

    // Create the execute query button
    execButton = new Button("Execute Query");
    for (int i = 0; i < Constants.dimensions; i++) {
      redoButton[i] = new Button("Reload Measures");
      drillButton[i] = new Button("Drill Down");
      }

    Panel center = new Panel(); 
    center.setLayout(new GridLayout(2,Constants.dimensions));

    // Create the panels for each measures dimension
    for (int i = 0; i < Constants.dimensions; i++) {
      Panel p = new Panel(); 
      p = new Panel(); 
      p.setLayout(new BorderLayout());
      p.add("North", new Label(Constants.dimensionNames[i] + "Measures"));
      p.add("Center", measureList[i]);
      center.add(p);
      }

    // Create the panels for each units dimension
    for (int i = 0; i < Constants.dimensions; i++) {
      Panel p = new Panel(); 
      Panel bp = new Panel(); 
      p.setLayout(new BorderLayout());
      bp.setLayout(new BorderLayout());
      bp.add("West", redoButton[i]);
      bp.add("East", drillButton[i]);
      p.add("North", new Label(Constants.dimensionNames[i] + " Units"));
      p.add("Center", unitList[i]);
      //p.add("South", redoButton[i]);
      p.add("South", bp);
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
   * Build a new graphic user interface, with the given title
   **/
  public CubeGUI(String title) { 

    //Set name of Frame;
    //super(title);  

    this.init();

    // Set up menu bar.
    menubar = new MenuBar();
    this.setMenuBar(menubar);
 
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
      p.add("North", new Label(Constants.dimensionNames[i] + "Measures"));
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
  * Execute a query using the selected items!  
  * Currently, just print the name of the query.
  **/
  public void runQuery() {
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
        MessageHandler msg = new MessageHandler("Query Result", message);
        msg.pack();
        msg.show();
        return;
        }
      }
       
    for (int i = 0; i < Constants.dimensions; i++) {
      if (measureList[i].getSelectedItem() == null) {
        String message = "No measure selected for the " +
                         Constants.dimensionNames[i] + 
                         " dimension.\n";
        MessageHandler msg = new MessageHandler("Query Result", message);
        msg.pack();
        msg.show();
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
        CubetteSpecification[] answer = 
           Cubette.sum(
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
        MessageHandler msg = new MessageHandler("Query Result", queryString);
        msg.pack();
        msg.show();
        return;
        }
      }
      queryString += "\nInsufficient Information to Answer Query.\n";
      MessageHandler msg;
      msg = new MessageHandler("Query Result", queryString);
      msg.pack();
      msg.show();
    }
  
  /**
  * A double click on an item in the Unit List, depending on the 
  * state, take the appropriate action.
  **/
  public void selectUnit(int dimension) {
    // Treat as a single click if on a measure
    if (measuresLoaded[dimension]) {
      updateUnit(dimension);
      return;
      }

    List selectedUnitList = unitList[dimension];
    Id unit = new Id(selectedUnitList.getSelectedItem());
    //selectedUnitList.addItem(unit.toString());
    Tuple r = global.finerUnitGraphs[dimension].edges.retrieveTuple(unit);
    // Are there any edges out of here?
    if (r != null) {
      // Yes we have edges 
      selectedUnitList.removeAll();
      IdSet units = r.getValueAsIdSet();
      //System.out.println(units.numberOfElements());
      for (Enumeration e = units.enumerate(); e.hasMoreElements();) {
        Id u = (Id)e.nextElement();
        selectedUnitList.addItem(u.toString());
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

      // clear the unit list
      selectedUnitList.removeAll();
      // Only take the units at this measure
      IdSet stopMeasureSet = 
      //  global.coarserMeasureGraphs[dimension].reachableSet(stopMeasure);
          new IdSet();
      stopMeasureSet.insert(stopMeasure);
      
      for (Enumeration e = 
             global.unitToMeasureTables[dimension].enumerateKeys();
           e.hasMoreElements();) {
        byte[] g = (byte[])e.nextElement();
 
        Tuple t = global.unitToMeasureTables[dimension].retrieveTuple(new Id(g));
        if (t == null) Internal.Error("no measure for this unit");
        Id tempMeasure = t.getValueAsId();
        if (stopMeasureSet.memberOf(tempMeasure)) {
          selectedUnitList.addItem((new Id(g)).toString());
          }
        }
      }
    // else we selected a unit, stay calm 
    }

  /**
  * They've highlighted a Unit, now lets show all the subunits within
  * this unit (plus this unit).
  * @param name - a list containing the selected unit
  **/
  public void oldupdateUnit(int dimension) {
    List selectedUnitList = unitList[dimension];
    List selectedMeasureList = measureList[dimension];
    Id testId = new Id(selectedUnitList.getSelectedItem());
 
    // Were the measures previously loaded?  In this case, we load all the
    // units from this measure to the measure selected in the measures window
    if (measuresLoaded[dimension]) {
      measuresLoaded[dimension] = false;
      Id stopMeasure = new Id(selectedMeasureList.getSelectedItem());
      // If no measure is selected, stop at measure selected in Unit window
      if (stopMeasure == null) stopMeasure = testId;
      // clear the unit list
      selectedUnitList.removeAll();
      IdSet stopMeasureSet = 
         global.coarserMeasureGraphs[dimension].reachableSet(stopMeasure);
      for (Enumeration e = 
             global.unitToMeasureTables[dimension].enumerateKeys();
           e.hasMoreElements();) {
        byte[] g = (byte[])e.nextElement();
 
        Tuple t = global.unitToMeasureTables[dimension].retrieveTuple(new Id(g));
        if (t == null) Internal.Error("no measure for this unit");
        Id tempMeasure = t.getValueAsId();
        if (stopMeasureSet.memberOf(tempMeasure)) {
          selectedUnitList.addItem((new Id(g)).toString());
          }
        }
      }
    }

  /**
   * Reload the measures into the appropriate unit list
   * @param i - which dimension
   **/
  public void redoMeasuresForUnits(int i) {
   // If a measure was not previously selected then load them all
   if (measureList[i].getSelectedItem() == null) {
     measureList[i].removeAll();
     for (Enumeration e = global.measureTables[i].enumerateKeys(); 
          e.hasMoreElements();) {
       byte[] g = (byte[])e.nextElement();
       Id k = new Id(g);
       measureList[i].addItem(k.toString());
       }
     }
   // A measure was previously selected then load only the measures above it
   else {
     Id measure = new Id(measureList[i].getSelectedItem());
     IdSet measureSet = global.coarserMeasureGraphs[i].reachableSet(measure);
     measureSet.insert(measure);
     unitList[i].removeAll();

     for (Enumeration e = measureSet.enumerate(); e.hasMoreElements();) {
       Id k = (Id)e.nextElement();
       unitList[i].addItem( k.toString());
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
    System.out.println(measure.toString());
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
    System.out.println("About Selected");
    }
}
