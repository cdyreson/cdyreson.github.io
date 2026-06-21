package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/** 
 * Maps a TypeId to a ShapeNode. Used to modify the data's shape
 * in append/update.
 * @author Curtis
 */
public class ShapeNodesTable {

    private Map<TypeId, ShapeNode> tab;

    public ShapeNodesTable(Map<TypeId, ShapeNode> tab) {
        this.tab = tab;
    }

    /* Add this ShapeNode to the table. Every TypeId is unique.
     * @param t - TypeId to add
     * @param n - ShapeNode to add
     */
    public void put(TypeId t, ShapeNode n) {
        tab.put(t, n);
    }

    /* Remove this TypeId from the table.
     * 
     * @param n - TypeId to remove
     */
    public void remove(TypeId t) {
        tab.remove(t);
    }

    /* Retrieve a ShapeNode from the table, given a TypeId
     * Node has to exist for this to work.
     * @param t - Id to lookup
     * @returns - found ShapeNode (or null if no ShapeNode?)
     */
    public ShapeNode get(TypeId n) {
        return tab.get(n);
    }
}
