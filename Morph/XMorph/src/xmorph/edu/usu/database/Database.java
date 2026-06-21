package xmorph.edu.usu.database;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import xmorph.edu.usu.database.tables.*;

/**
 * The Database class provides operators on the underlying database. In this
 * implementation, BerkeleyDB tables are used. Eventually this class will become
 * abstract and implemented by several database products.
 *
 * @author Curtis
 */
public abstract class Database {

    /* Database object that stores all of the data files that are open */
    //private Set<> dataDocumentIds = new HashSet();
    /* File handles of all of the open databases, so they can be closed,
     and opened only once. */

    /* Metadata table */
    public MetadataTable metadataTable;
    /* Tables */
    public AbbreviatedTypesTable abbreviatedTypesTable;
    public BuildGroupsTableCollection buildGroupsTableCollection;
    public CardinalityTable cardinalityTable;
    public DLNTable dlnToSQLIdTable;
    public DocumentsTable documentsTable;
    public GroupedNodesTable groupedNodesTable;
    public LCATable lcaTable;
    public NodesTable nodesTable;
    public PathsTable pathsTable;
    public TextTable textTable;
    public TypedNodeTableCollection typedNodeTableCollection;
    public TypePartialityTable typePartialityTable;
    public TypesTable typesTable;
    public ShapesTable shapesTable;
    public ShapeNodesTable shapeNodesTable;
    public TypesTypeIdtoIntegerTable typesLookupTable;
    public TypesIntegerToTypeIdTable typesIntegerToTypeIdTable;

    /*Flags */
    static public Boolean inMemoryDB = true;
    protected boolean alreadyOpen = false;

    public Database() {
        //System.out.println("opening this sucker" + this);
        openDatabase();
    }

    /* Close the database */
    /*
     @Override
     protected void finalize() throws Throwable {
     super.finalize();
     closeDatabase();
     }
     */
    /* Open all of the database tables.
     * Call this once to open the database.
     */
    public abstract void openDatabase();

    /* This method closes all of the open tables, and the database
     * environment.
     */
    public abstract void closeDatabase();

    /* Make the changes persistent.
     */
    public abstract void commitDatabase();

    /* Open an iterator for a table. Contains the code to do the iterator
     * creation.
     * @param String name - Table name
     * @param Class c - Class binding for key
     * @param Class b - Class binding for value
     * @returns TableIterator - constructed iterator
     */
    public abstract Iterator openIterator(String name, Class c, Class b);

    /* Open a table.
     * @param String name - Table name
     * @param Class c - Class binding for key
     * @param Class b - Class binding for value
     * @returns Map - constructed table
     */
    public abstract Map openTable(String name, Class c, Class b);

    /* Open a table with a sort order defined on the keys.
     * @param String name - Table name
     * @param Class c - Class binding for key
     * @param Class b - Class binding for value
     * @returns Map - constructed table
     */
    public abstract SortedMap openSortedTable(String name, Class c, Class b);

    /* Open a table.
     * @param String name - Table name
     * @param Class c - Class binding for key
     * @param Class b - Class binding for value
     * @returns Map - constructed table
     */
    public abstract MapWithDuplicates openTableWithDuplicates(String name, Class c, Class b);
}
