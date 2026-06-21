package xmorph.edu.usu.database.memory;

import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Text;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.tables.*;
import java.util.*;
import xmorph.org.exist.numbering.DLN;

/**
 * The Database class provides operators on the underlying database, where the
 * database is assumed to be in memory (transient rather than persistent). In
 * this implementation, HashMap-based tables are used.
 *
 * @author Curtis
 */
public class Database extends xmorph.edu.usu.database.Database {

    protected Map<String, Map> openDbs = new HashMap();
    //private boolean alreadyOpen = false;

    /*
     * Build the database public Database() { openDatabase(); }
     *
     */

    /*
     * Open all of the database tables. Call this once to open the database.
     */
    @Override
    public void openDatabase() {
        //System.out.println(" opening " + alreadyOpen + this);
        if (!alreadyOpen) {
            alreadyOpen = true;
            //System.out.println("DBInfo open" + alreadyOpen);
            openDbs = new Hashtable();
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
        }
    }

    /*
     * This method commits the data tables, and the database environment.
     * For the in-memory implementation it does nothing.
     */
    @Override
    public void commitDatabase() {
    }

    /*
     * This method closes all of the open tables, and the database environment.
     * For the in-memory implementation it does nothing.
     */
    @Override
    public void closeDatabase() {
        //System.out.println("closing db " + alreadyOpen);
    }

    /*
     * Open an iterator for a table. @param String name - Table name @param
     * EntryBinding e - BerekeleyDB class binding @param Class c - Java class
     * info, BerekeleyDB binding will be created. @returns TableIterator -
     * constructed iterator
     */
    @Override
    public Iterator openIterator(String name, Class a, Class c) {
//System.out.println("asdf " + openDbs.keySet().size());
//System.out.println(" xxx " + name);
//System.out.println("foo " + openDbs.get(name) + name + openDbs.get(name).keySet());
        if (!openDbs.containsKey(name)) {
            openTable(name, a, c);
        }
        TreeMap t = (TreeMap)openDbs.get(name);
        return t.navigableKeySet().iterator();
    }

    /*
     * Open a table. @param String name - Table name @param Class b for key -
     * ignored @param Class c - Java class info for data, ignored @returns Map -
     * constructed Map
     */
    @Override
    public HashMapWithDuplicates openTableWithDuplicates(String name, Class e, Class c) {
        return new HashMapWithDuplicates();
    }

    /*
     * Open a table. @param String name - Table name @param Class b for key -
     * ignored @param Class c - Java class info for data, ignored @returns Map -
     * constructed Map
     */
    @Override
    public SortedMap openSortedTable(String name, Class e, Class c) {
        //System.out.println("opening sorted " + name);
        SortedMap h = new TreeMap();
        openDbs.put(name, h);
        return h;
    }

    /*
     * Open a table. @param String name - Table name @param Class b for key -
     * ignored @param Class c - Java class info for data, ignored @returns Map -
     * constructed Map
     */
    @Override
    public Map openTable(String name, Class e, Class c) {
        //System.out.println("opening " + name);
        HashMap h = new HashMap();
        openDbs.put(name, h);
        return h;
    }
}
