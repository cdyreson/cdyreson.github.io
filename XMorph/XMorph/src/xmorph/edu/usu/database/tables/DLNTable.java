package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.TypeId;
import xmorph.org.exist.numbering.DLN;
import java.util.*;

/**
 * Table that maps a DLN to an SQL Id, for MorphSQL.
 * @author Curtis
 */
public class DLNTable {

    private Map<DLN, TypeId> tab;

    public DLNTable(Map<DLN, TypeId> tab) {
        this.tab = tab;
    }

    /* Add this node to the table.
     * @param n - DLN is the key
     * @param t - TypeId is the value
     */
    public void put(DLN n, TypeId t) {
            tab.put(n, t);
    }

    /* Retrieve a TypeId from the table, given as a DLN
     * Node has to exist for this to work.
     * @param n - Id to lookup
     * @returns - found TypeId (or null if no TypeId?)
     */
    public TypeId get(DLN n) {
        return tab.get(n);
    }
}
