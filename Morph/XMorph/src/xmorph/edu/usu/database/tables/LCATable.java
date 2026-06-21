package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.reporting.ErrorInformation;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.reporting.*;
import java.util.*;

/**
 * Maps a TypeId to the LCA for this type (also a TypeId)
 * @author Curtis
 */
public class LCATable {

    private Map<TypeId, TypeId> tab;

    /**
     * Open the table
     */
    public LCATable(Map<TypeId, TypeId> tab) {
        this.tab = tab;
    }

    /**
     * Add a new LCA to the table
     * @param t - TypeId of type
     * @param lca - TypeId of lca
     */
    public void put(TypeId t, TypeId lca) {
        tab.put(t, lca);
    }

    /**
     * Fetch the LCA TypeId from the table
     * @param t - TypeId of type
     * @return - fetched TypeId
     */
    public TypeId get(TypeId t) {
        TypeId f = tab.get(t);
        if (f == null) {
            System.out.println("Warning: bad lca for type " + t.getName());
            ErrorInformation.appendLn("Warning: bad lca for type " + t.getName());
        //System.exit(-1);
        }
        return f;
    }
}
