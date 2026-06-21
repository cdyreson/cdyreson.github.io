package xmorph.edu.usu.database.tables;

import java.util.HashMap;
import java.util.Map;
import xmorph.edu.usu.graph.TypeId;

/**
 * Maps an Integer representing the type to its TypeId
 *
 * @author Curtis
 */
public class TypesIntegerToTypeIdTable {

    private Map<Integer, TypeId> tab;
    private HashMap<Integer, TypeId> mappings;

    /**
     * Open the table
     *
     * @author Curtis
     */
    public TypesIntegerToTypeIdTable(Map<Integer, TypeId> tab) {
        this.tab = tab;
        mappings = new HashMap();
    }

    /**
     * Add a new Path to the table
     *
     * @param v - int to add
     * @param t - TypeId of type
     * @author Curtis
     */
    public void put(int v, TypeId t) {
        Integer x = new Integer(v);
        if (!mappings.containsKey(x)) {
            // Not in memory, check on disk
            if (!tab.containsKey(x)) {
                // Not on disk, add it
                tab.put(x, t);
            }
            // Add it to memory
            mappings.put(x, t);
        }
    }

    /*
     * Remove this long from the table.
     *
     * @param t - int to remove
     */
    public void remove(int v) {
        Integer x = new Integer(v);
        if (mappings.containsKey(x)) {
            mappings.remove(x);
            tab.remove(x);
        }
    }

    /**
     * Fetch a type id from the table
     *
     * @param v - int to fetch
     * @return - fetched TypeId
     * @author Curtis
     */
    public TypeId get(int v) {
        Integer x = new Integer(v);
        if (mappings.containsKey(x)) {
            return mappings.get(x);
        }
        if (!tab.containsKey(x)) {
            System.out.println("Problem in TypesIntegerToTypeIdTable, not found integer " + x.intValue());
        }
        TypeId t = (TypeId) tab.get(x);
        mappings.put(x, t);
        return t;
    }
}
