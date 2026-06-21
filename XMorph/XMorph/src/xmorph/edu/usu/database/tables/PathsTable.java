package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Path;
import java.util.*;

/**
 * maps a path image to a typeId
 * @author Curtis
 */
public class PathsTable {

    private Map<String, TypeId> tab;
    private HashMap<String, TypeId> mappings;

    /* Constructor, maps a String to a TypeId
     * @param tab - Map String to TypeId
     */
    public PathsTable(Map<String, TypeId> tab) {
        this.tab = tab;
        mappings = new HashMap();
    }

    public void put(Path p, TypeId t) {
        //System.out.println("PathsTable put path " + p.image() + " " + t.toString());
        // Check to see if it is memory
        String path = p.image();
        if (!mappings.containsKey(path)) {
            // Not in memory, check on disk
            if (!tab.containsKey(path)) {
                // Not on disk, add it
                tab.put(path, t);
            }
            // Add it to memory
            mappings.put(path, t);
        }
    }

    public void remove(Path p) {
        String path = p.image();
        //System.out.println("PathsTable getting path " + path);
        if (mappings.containsKey(path)) {
            mappings.remove(path);
        }
    }

    public TypeId get(String path) {
        //System.out.println("PathsTable getting path " + path);
        if (mappings.containsKey(path)) {
            return mappings.get(path);
        }
        //System.out.println("new path");
        //mappings.put(path, a);
        TypeId t = tab.get(path);
        if (t != null) {
            mappings.put(path, t);
        }
        return t;
    }

    public TypeId get(Path p) {
        String path = p.image();
        //System.out.println("PathsTable getting path " + path);
        if (mappings.containsKey(path)) {
            return mappings.get(path);
        }
        //mappings.put(path, a);
        TypeId t = tab.get(path);
        if (t != null) {
            mappings.put(path, t);
        }
        return t;
    }
}
