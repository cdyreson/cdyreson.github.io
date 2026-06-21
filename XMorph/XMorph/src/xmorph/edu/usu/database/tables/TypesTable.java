package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.Path;
import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/**
 * Maps a typeId to a Path image (a String)
 * @author Curtis
 */
public class TypesTable {

    private Map<TypeId, String> tab;

    /**
     * Open the table
     * @author Curtis
     */
    public TypesTable(Map<TypeId, String> tab) {
        this.tab = tab;
    }

    /**
     * Add a new Path to the table
     * @param t - TypeId of type
     * @param p - Path to add
     * @author Curtis
     */
    public void put(TypeId t, Path p) {
        Path f = get(t);
        if (f == null) {
            tab.put(t, p.image());
        }
    }

    /* Remove this TypeId from the table.
     * 
     * @param t - TypeId to remove
     */
    public void remove(TypeId t) {
        tab.remove(t);
    }

    /**
     * Fetch a Path from the table
     * @param t - TypeId of type
     * @return - fetched Path
     * @author Curtis
     */
    public Path get(TypeId t) {
        String s = tab.get(t);
        if (s == null) {
            return null;
        }
        return new Path(s);
    }
}
