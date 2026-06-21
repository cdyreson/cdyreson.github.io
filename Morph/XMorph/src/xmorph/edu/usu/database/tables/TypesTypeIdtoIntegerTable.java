package xmorph.edu.usu.database.tables;

import java.util.HashMap;
import java.util.Map;
import xmorph.edu.usu.graph.TypeId;

/**
 * Maps a typeId to an integer representing the type
 *
 * @author Curtis
 */
public class TypesTypeIdtoIntegerTable {

    private Map<TypeId, Integer> tab;
    private HashMap<TypeId, Integer> mappings;

    /**
     * Open the table
     *
     * @author Curtis
     */
    public TypesTypeIdtoIntegerTable(Map<TypeId, Integer> tab) {
        this.tab = tab;
        mappings = new HashMap();
    }

    /**
     * Add a new Path to the table
     *
     * @param t - TypeId of type
     * @param v - int to add
     * @author Curtis
     */
    public void put(TypeId t, int v) {
        //System.out.println("TypesTypeIdToIntegerTable Putting typeId " + t.toString() + " " + v);
        Integer x = new Integer(v);
        if (!mappings.containsKey(t)) {
            // Not in memory, check on disk
            if (!tab.containsKey(t)) {
                // Not on disk, add it
                tab.put(t, new Integer(x));
            }
            // Add it to memory
            mappings.put(t, x);
        }
    }

    /*
     * Remove this TypeId from the table.
     *
     * @param t - TypeId to remove
     */
    public void remove(TypeId t) {
        if (mappings.containsKey(t)) {
            mappings.remove(t);
            tab.remove(t);
        }
    }

    /**
     * Fetch a type value from the table, or "die" if not found
     *
     * @param t - TypeId of type
     * @return - fetched Long
     * @author Curtis
     */
    public int get(TypeId t) {
        //System.out.println("TypesTypeIdToIntegerTable getting typeId " + t.toString());
        if (mappings.containsKey(t)) {
            return ((Integer) mappings.get(t)).intValue();
        }

        // Fetch off of disk if needed
        if (tab.containsKey(t)) {
            Integer x = (Integer) tab.get(t);
            int d = x.intValue();
            mappings.put(t, d);
            return d;
        }

        System.out.println(" Dying, problem in getting a typeId in TypesTypeIdToIntegerTable " + t.toString());
        //System.exit(-1);
        return 1;
    }
}
