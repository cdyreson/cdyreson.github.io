package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.TypeId;
import java.util.*;
import xmorph.org.exist.numbering.NodeId;

/**
 * Table that maps a pair of typeIds to a cardinality, to test
 * for information loss.
 * @author Curtis
 */
public class CardinalityTable {

    private Map<TypeId, Map<TypeId, Long>> tab;
    Database db;

    public CardinalityTable(Database db) {
        this.tab = new HashMap();
        this.db = db;
    }

    /* Add a type mapping to the table.
     * @param n - TypeId is the key
     * @param t - TypeId is the value
     * @param value - cardinality
     */
    public void put(TypeId n, TypeId t, Long value) {
        if (!tab.containsKey(n)) {
            // This is a new TypeId
            tab.put(n, new HashMap());
        }
        Map<TypeId, Long> cardTab = tab.get(n);
        if (!cardTab.containsKey(t)) {
            cardTab.put(t, value);
        }
    }

    /* Add a type mapping to the table, but only if it
     * increases the value already there.
     * @param n - TypeId is the key
     * @param t - TypeId is the value
     * @param value - cardinality
     */
    public void increaseOnlyPut(TypeId n, TypeId t, Long value) {
        if (!tab.containsKey(n)) {
            // This is a new TypeId
            tab.put(n, new HashMap());
        }
        Map<TypeId, Long> cardTab = tab.get(n);
        if (!cardTab.containsKey(t)) {
            cardTab.put(t, value);
        } else {
            // Is value more than the existing value?
            if (cardTab.get(t) < value) {
                cardTab.put(t, value);
            }
        }
    }

    /*
     * Retrieve a cardinality from the table.
     * @param n - TypeId to lookup
     * @param t - TypeId to lookup
     * @returns - cardinality
     */
    public int get(TypeId n, TypeId t) {
        if (tab.containsKey(n)) {
            Map<TypeId, Long> cardTab = tab.get(n);
            if (cardTab.containsKey(t)) {
                return cardTab.get(t).intValue();
            }
        }
        return 1;
    }

    /*
     * Check whether this relationship is lossless.
     * A lossless relationship is one that is one-to-one
     * or one-to-many, but not many-to-many.
     *
     * @param parent - TypeId of parent
     * @param child - TypeId of child
     * @param lca - TypeId of lca
     * @param t - TypeId to lookup
     * @returns - True if lossless, false otherwise.
     */
    public Boolean isNonAdditive(TypeId parent, TypeId child, TypeId lca) {
        // It is non-additive if the lca is a descendent or self of the parent
        //System.out.println("Got here " + parent.getDLN() + " " + child.getDLN() + " " + lca.getDLN());
        return (lca.getDLN().isDescendantOrSelfOf(parent.getDLN()))
          || (lca.getDLN().isDescendantOrSelfOf(child.getDLN()));
    }
}
