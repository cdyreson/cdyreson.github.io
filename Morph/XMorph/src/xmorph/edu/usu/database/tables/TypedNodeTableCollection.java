package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.TypeId;
//import database.TableIterator;
import java.util.*;

/**
 * The TypedNode Tables are a collection of tables,
 * one table for each type. The table stores all of the
 * nodes for the given type.
 * 
 * @author Curtis
 */
public class TypedNodeTableCollection {
    // An internal table to avoid multiple openings of the
    // individual node tables.
    private HashMap<TypeId, Map<Id, Long>> mappings;
    Database db;

    /* Creates an internal cache of open tables
     */
    public TypedNodeTableCollection(Database db) {
        mappings = new HashMap();
        this.db = db;
    }

    /* Add a node to a particular table.
     * 
     * @param Typeid t - which Type table?
     * @param Id n - Id to add
     */
    public void put(TypeId t, Id n) {
        Map<Id, Long> typeTable = mappings.get(t);
        // Try to fetch map handle from memory cache
        if (typeTable == null) {
            // No map handle in memory, create it
            typeTable = db.openSortedTable(t.toString(), Id.class, Long.class);
            // Add to typedNodes table
            mappings.put(t, typeTable);
        }
        typeTable.put(n, new Long(0));
    //System.out.println("TYPEDNODETABLECOLLECTION putting " + n.toString());
    }

    /* Remove a node from the table. Every node Id is unique.
     * @param Typeid t - which Type table?
     * @param Id n - Id to add
     */
    public void remove(TypeId t, Id n) {
        Map<Id, Long> typeTable = mappings.get(t);
        // Try to fetch map handle from memory cache
        if (typeTable == null) {
            // No map handle in memory, create it
            typeTable = db.openSortedTable(t.toString(), Id.class, Long.class);
            // Add to typedNodes table
            mappings.put(t, typeTable);
        }
        typeTable.remove(n);
    }

    /* Construct an iterator to generate all the nodes
     * (in sequence) for this Type. The nodes are generated
     * in lexical order.
     * 
     * @param Typeid t - which Type table?
     * @returns Iterator - constructed iterator
     */
    public Iterator iterator(TypeId t) {
        //System.out.println("TypedNodeTableCollection open " + t.toString());
        //Map<Long, Node> typeTable = mappings.get(t);
        return db.openIterator(t.toString(), Id.class, Long.class);
    //return typeTable.keySet().iterator();
    }
}
