package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.Element;
import xmorph.edu.usu.database.Database;
import java.util.*;

/**
 * The BuildGroup Table is actually a collection of tables,
 * one table for each type. The table for the type maps a
 * String to a group Id.
 * 
 * The BuildGroupsTableCollection class manages the collection of all
 * building groups. There is one build group for each type.
 * @author Curtis
 */
public class BuildGroupsTableCollection {
    // An internal table to avoid multiple openings of the
    // individual node tables.
    private HashMap<TypeId, Map> mappings;
    Database db;

    /* Creates an internal cache of open tables
     */
    public BuildGroupsTableCollection(Database db) {
        this.db = db;
        mappings = new HashMap();
    }

    /* Add a group value to a particular table.
     * @param Typeid t - which Type table?
     * @param String s - Group value.
     */
    public void put(TypeId t, Element e) {
        //System.out.println("Pugging group vaue");
        Map<String, Id> typeTable = mappings.get(t);
        // Try to fetch map handle from memory cache
        if (typeTable == null) {
            // No map handle in memory, create it
            typeTable = db.openTable("buildGroup" + t.toString(), String.class, Id.class);
            // Add to typedNodes table
            mappings.put(t, typeTable);
        }

        // Now let's check to see if we have already seen this String value
        if (!typeTable.containsKey(e.groupValue())) {
            // A new Group, let's allocate the group Id
            //typeTable.put(e.groupValue(), e.getGroupId());
            typeTable.put(e.groupValue(), e.getGroupNode().getId());
        }
    }
    
    /* Get a group Id for a particular String
     * @param Typeid t - which Type table?
     * @param String s - Group value to lookup.
     */
    public Id get(TypeId t, Element e) {
        // First make sure it is there
        put(t, e);
        Map<String, Id> typeTable = mappings.get(t);
        // Try to fetch map handle from memory cache
        if (typeTable == null) {
            // If not present in the data store, add it
            System.err.println("Internal error, no type for this group");
            System.exit(-1);
        }
        return typeTable.get(e.groupValue());
    }

}
