package xmorph.edu.usu.database.berkeleyDB;

import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Text;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.MapWithDuplicates;
import xmorph.edu.usu.database.tables.*;

import xmorph.edu.usu.database.DBError;

// Standard java packages includes
import java.util.*;
import java.io.*;

// BerkeleyDB includes
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.CheckpointConfig;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.DatabaseConfig;
//imports for BerkleyDb bindings 
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
//import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.StoredClassCatalog;
//import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Cursor;

import xmorph.org.exist.numbering.DLN;

/**
 * The Database class provides operators on the underlying database. In this
 * implementation, BerkeleyDB tables are used. Eventually this class will become
 * abstract and implemented by several database products.
 *
 * @author Curtis
 */
public class Database extends xmorph.edu.usu.database.Database {

    //private boolean alreadyOpen;
    /*
     * BerkeleyDB environment and database variables
     */
    private Environment myEnv;
    private DatabaseConfig myDbConfig;
    private StoredClassCatalog javaCatalog;
    private ClassCatalog foo;
    private com.sleepycat.je.Database catalogDb;
    /*
     * Name of the catalog internal to the database
     */
    private final static String javaCatalogName = "catalog";

    /*
     * Default name of the directory in which the database lives
     */
    private static String dbHome = "db";
    /*
     * File handles of all of the open databases, so they can be closed, and
     * opened only once.
     */
    private Map<String, com.sleepycat.je.Database> openDbs;
    //private Map<String, StoredMap> openMaps;
    //private Map<String, com.sleepycat.je.Database> openCursors;

    /*
     * Open all of the database tables. Call this once to open the database.
     */
    private void openTables() {
        if (!alreadyOpen) {
            //System.out.println("DBInfo open");

            abbreviatedTypesTable = new AbbreviatedTypesTable(openTable("abbreviatedTypes", String.class, List.class));
            buildGroupsTableCollection = new BuildGroupsTableCollection(this);
            cardinalityTable = new CardinalityTable(this);
            dlnToSQLIdTable = new DLNTable(openTable("dlnToSQLIdTable", DLN.class, TypeId.class));
            documentsTable = new DocumentsTable(openTable("documents", String.class, TypeId.class));
            groupedNodesTable = new GroupedNodesTable(openTable("groupedNodes", Id.class, Node.class));
            lcaTable = new LCATable(openTable("lcas", TypeId.class, TypeId.class));
            metadataTable = new MetadataTable(openTable("metadata", String.class, Long.class));
            nodesTable = new NodesTable(openTable("nodes", Id.class, Node.class));
            pathsTable = new PathsTable(openTable("paths", String.class, TypeId.class));
            shapesTable = new ShapesTable(openTable("shapes", String.class, ShapeNode.class));
            shapeNodesTable = new ShapeNodesTable(openTable("shapeNodes", TypeId.class, ShapeNode.class));
            textTable = new TextTable(openTable("text", Long.class, Text.class));
            typedNodeTableCollection = new TypedNodeTableCollection(this);
            typePartialityTable = new TypePartialityTable(openTable("typePartiality", TypeId.class, Set.class));
            typesTable = new TypesTable(openTable("types", TypeId.class, String.class));
            typesLookupTable = new TypesTypeIdtoIntegerTable(openTable("typesLookup", TypeId.class, Integer.class));
            typesIntegerToTypeIdTable = new TypesIntegerToTypeIdTable(openTable("typesLongToId", Integer.class, TypeId.class));

            //DocumentInfo.current = metadataTable.get("documentCurrent");
        }
    }

    /* This method commits the open tables, and the database
     * environment.
     */
    @Override
    public void commitDatabase() {
        /*
        if (alreadyOpen) {
            closeDatabase();
        }
        openDatabase();
         */
        if (!alreadyOpen) {
            return;
        }
        String s = "default";
        //System.out.println("Checkpointing");
        try {
            /*
            // Iterate through the open tables
            for (String dbName : openDbs.keySet()) {
            //System.out.println("closing " + dbName);
            com.sleepycat.je.Database db = openDbs.get(dbName);
            db.sync();
            }
            // We need to commit the database catalog
            s = "catalog";  // Set for error message only
            catalogDb.sync();
            
            // Close the environment
            s = " environment";  // Set for error message only
            myEnv.sync();
             */
            myEnv.checkpoint(new CheckpointConfig());
        } catch (Exception e) {
            DBError.Error("Committing database " + dbHome + s + "\nError: " + e);
        }
    }

    /* This method closes all of the open tables, and the database
     * environment.
     */
    @Override
    public void closeDatabase() {
        //System.out.println("closing berkeleydb");
        if (!alreadyOpen) {
            return;
        }

        alreadyOpen = false;
        String s = "default";
        try {
            // Iterate through the open tables
            for (String dbName : openDbs.keySet()) {
                //System.out.println("closing " + dbName);
                com.sleepycat.je.Database db = openDbs.get(dbName);
                db.close();
            }
            // We need to close the database catalog
            s = "catalog";  // Set for error message only
            catalogDb.close();

            // Close the environment
            s = " environment";  // Set for error message only
            //myEnv.sync();
            myEnv.close();
            javaCatalog = null;
            myEnv = null;
        } catch (Exception e) {
            DBError.Error("Closing database " + dbHome + s + "\nError: " + e);
        }
    }

    /* Close the database */
    @Override
    protected void finalize() throws Throwable {
        try {
            closeDatabase();        // close open files
        } finally {
            super.finalize();
        }
    }

    /*
     * Set the database directory.
     */
    public static void setDbHome(String directory) {
        dbHome = directory;
    }

    /*
     * Set the database directory.
     */
    public static String getDbHome() {
        return dbHome;
    }

    /*
     * Open the Database. This method manages all of the BerkeleyDB
     * initialization code, etc. to open tables.
     */
    public void setAllowDuplicates() {
        myDbConfig.setSortedDuplicates(true);
    }

    public void setNoDuplicates() {
        myDbConfig.setSortedDuplicates(false);
    }

    /*
     * Open the Database. This method manages all of the BerkeleyDB
     * initialization code, etc. to open tables.
     */
    @Override
    public void openDatabase() {
        if (!alreadyOpen) {
            openDbs = new Hashtable();
            //openMaps = new Hashtable();
            //openCursors = new Hashtable();
            // Establish some configurations for the DB environment
            try {
                // Test if myEnv is already initialized
                if (myEnv == null) {
                    //System.out.println("New cEnvironment");
                    EnvironmentConfig myEnvConfig = new EnvironmentConfig();
                    //myEnvConfig.setTransactional(true);
                    myEnvConfig.setAllowCreate(true);

                    // Open the environment in the specified directory
                    myEnv = new Environment(new File(dbHome), myEnvConfig);

                    // Now set up the database configuration
                    myDbConfig = new DatabaseConfig();

                    //myDbConfig.setDeferredWrite(true);
                    //myDbConfig.setTransactional(true);

                    myDbConfig.setAllowCreate(true);

                    // Open the database's catalog if not already opened
                    if (javaCatalog == null) {
                        // Open the Java class catalog for serialization
                        //System.out.println("Database opening java catalog");
                        catalogDb = myEnv.openDatabase(null, javaCatalogName, myDbConfig);
                        javaCatalog = new StoredClassCatalog(catalogDb);
                    }
                    openTables();
                    // Set the Flag to indicate we've already opened these guys.
                    alreadyOpen = true;
                }
            } catch (Exception e) {
                DBError.Error("Opening database " + dbHome + "\nError: " + e);
            }
        }
    }

    /*
     * Open an iterator for a table.
     *
     * @param String name - Table name @param EntryBinding e - BerekeleyDB class
     * binding @param Class c - Java class info, BerekeleyDB binding will be
     * created. @returns TableIterator - constructed iterator
     */
    public Iterator openIterator(String name, EntryBinding e, Class c) {
        return openIterator(name, e, new SerialBinding(javaCatalog, c));
    }

    /*
     * Open an iterator for a table.
     *
     * @param String name - Table name @param Class c - Java class info,
     * BerekeleyDB binding will be created. @param EntryBinding e - BerekeleyDB
     * class binding @returns TableIterator - constructed iterator
     */
    public Iterator openIterator(String name, Class c, EntryBinding e) {
        return openIterator(name, new SerialBinding(javaCatalog, c), e);
    }

    /*
     * Open an iterator for a table.
     *
     * @param String name - Table name @param Class c1 - Java class info,
     * BerekeleyDB binding will be created. @param Class c2 - Java class info,
     * BerekeleyDB binding will be created. @returns TableIterator - constructed
     * iterator
     */
    @Override
    public Iterator openIterator(String name, Class c1, Class c2) {
        return openIterator(name, new SerialBinding(javaCatalog, c1), new SerialBinding(javaCatalog, c2));
    }

    /*
     * / * Open an iterator for a table. Contains the code to do the iterator
     * creation.
     *
     * @param String name - Table name @param EntryBinding e1 - BerekeleyDB
     * class binding @param EntryBinding e2 - BerekeleyDB class binding @returns
     * TableIterator - constructed iterator / public TableIterator
     * openIterator(String name, EntryBinding k, EntryBinding b) {
     *
     * Cursor cursor = null; com.sleepycat.je.Database tableDb = null; try {
     * tableDb = openDbs.get(name); if (tableDb == null) { openTable(name, k,
     * b); tableDb = openDbs.get(name); } cursor = tableDb.openCursor(null,
     * null); return new TablePersistentIterator(cursor, k, b); } catch
     * (DatabaseException dbe) { // Exception handling goes here ...
     * DBError.Error("Error in cursor open: " + name + " :" + dbe.toString()); }
     * return null; }
     *
     */
    /*
     * Open an iterator for a table. @param String name - Table name @param
     * EntryBinding e - BerekeleyDB class binding @param Class c - Java class
     * info, BerekeleyDB binding will be created. @returns TableIterator -
     * constructed iterator
     */
    public Iterator openIterator(String name, EntryBinding k, EntryBinding b) {
        //System.out.println("BERKELEYDB DATABASE openeing iterator for " + name);
        if (!openDbs.containsKey(name)) {
            openTable(name, k, b);
        }
        //return ((Map) openMaps.get(name)).keySet().iterator();
        return ((Map) openDbs.get(name)).keySet().iterator();
    }

    /*
     * Open a table.
     *
     * @param String name - Table name @param EntryBinding e for key -
     * BerkeleyDB binding info @param Class c - Java class info for data,
     * BerekeleyDB binding will be created. @returns Map - constructed Map
     */
    public Map openTable(String name, EntryBinding e, Class c) {
        return openTable(name, e, new SerialBinding(javaCatalog, c));
    }

    /*
     * Open a table.
     *
     * @param String name - Table name @param Class c - Java class info for key,
     * BerekeleyDB binding will be created. @param EntryBinding e1 - BerkeleyDB
     * binding info for data @returns Map - constructed Map
     */
    public Map openTable(String name, Class c, EntryBinding e) {
        return openTable(name, new SerialBinding(javaCatalog, c), e);
    }

    /*
     * Open a table.
     *
     * @param String name - Table name @param Class c1 - Java class info for
     * key, BerekeleyDB binding will be created. @param Class c2 - Java class
     * info for data, BerekeleyDB binding will be created. @returns Map -
     * constructed Map
     */
    @Override
    public Map openTable(String name, Class c1, Class c2) {
        return openTable(name, new SerialBinding(javaCatalog, c1), new SerialBinding(javaCatalog, c2));
    }

    /*
     * Open a table with a sort order defined on the keys.
     *
     * @param String name - Table name @param Class c1 - Java class info for
     * key, BerekeleyDB binding will be created. @param Class c2 - Java class
     * info for data, BerekeleyDB binding will be created. @returns Map -
     * constructed Map
     */
    @Override
    public SortedMap openSortedTable(String name, Class c1, Class c2) {
        return (SortedMap) openTable(name, new SerialBinding(javaCatalog, c1), new SerialBinding(javaCatalog, c2));
    }

    /*
     * Open a table.
     *
     * @param String name - Table name @param EntryBinding e1 - BerkeleyDB
     * binding info for key @param EntryBinding e2 - BerkeleyDB binding info for
     * data @param duplicates - Are duplicates allowed? @returns Map -
     * constructed Map
     */
    public Map openTable(String name, EntryBinding e1, EntryBinding e2) {
        com.sleepycat.je.Database tableDb = null;

        try {
            //System.out.println("opening table " + name);
            tableDb = myEnv.openDatabase(null, name, myDbConfig);
            openDbs.put(name, tableDb);
        } catch (Exception e) {
            DBError.Error("Opening table " + name + "\nError: " + e);
        }
        //return new TablePersistent(tableDb, e1, e2);
        StoredSortedMap m = new StoredSortedMap(tableDb, e1, e2, true);
        //openMaps.put(name, m);
        return m;
    }

    /*
     * Open a table.
     *
     * @param String name - Table name @param Class c1 - Java class info for
     * key, BerekeleyDB binding will be created. @param Class c2 - Java class
     * info for data, BerekeleyDB binding will be created. @returns Map -
     * constructed Map
     */
    @Override
    public MapWithDuplicates openTableWithDuplicates(String name, Class c1, Class c2) {
        return openTableWithDuplicates(name, new SerialBinding(javaCatalog, c1), new SerialBinding(javaCatalog, c2));
    }

    /*
     * Open a table.
     *
     * @param String name - Table name @param Class c1 - Java class info for
     * key, BerekeleyDB binding will be created. @param Class c2 - Java class
     * info for data, BerekeleyDB binding will be created. @returns Map -
     * constructed Map
     */
    public MapWithDuplicates openTableWithDuplicates(String name, EntryBinding e1, EntryBinding e2) {
        com.sleepycat.je.Database tableDb = null;
        try {
            setAllowDuplicates();
            tableDb = myEnv.openDatabase(null, name, myDbConfig);
            openDbs.put(name, tableDb);
            setNoDuplicates();
        } catch (Exception e) {
            DBError.Error("Opening table " + name + "\nError: " + e);
        }
        StoredMapWithDuplicates s = new StoredMapWithDuplicates(tableDb, e1, e2, true);
        //openMaps.put(name, s);
        return s;
    }
}
