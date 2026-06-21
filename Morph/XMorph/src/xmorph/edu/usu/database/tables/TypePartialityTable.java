package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/**
 * Maps a type id to a set of type ids to which it is partial
 * 
 * @author Curtis
 */
public class TypePartialityTable {

    private Map<TypeId, Set<TypeId>> tab;

    public TypePartialityTable(Map<TypeId, Set<TypeId>> tab) {
        this.tab = tab;
    }

    public void put(TypeId fromTypeId, TypeId toTypeId) {
        if (!tab.containsKey(fromTypeId)) {
            tab.put(fromTypeId, new HashSet());
        }
        Set<TypeId> toSet = tab.get(fromTypeId);
        toSet.add(toTypeId);
        tab.put(fromTypeId, toSet);
    }

    /*
     * Check to determine if the child is partial with respect to the
     * parent.
     * 
     * @param fromTypeId - parent id
     * @param toTypeId - child id
     * @returns True if child is partial, false otherwise
     */
    public Boolean childIsPartial(TypeId fromTypeId, TypeId toTypeId) {
        if (!tab.containsKey(fromTypeId)) {
            return false;
        }
        Set<TypeId> toSet = tab.get(fromTypeId);
        if (toSet.contains(toTypeId)) {
            return true;
        }
        return false;
    }

    public Set<TypeId> get(TypeId t) {
        return tab.get(t);
    }

    /*
     * Check whether this relationship is type-total.
     * It is type-total if none of the relationships
     *
     * @param parent - TypeId of parent
     * @param child - TypeId of child
     * @param lca - TypeId of lca
     * @param t - TypeId to lookup
     * 
     * @returns - True if lossless, false otherwise.
     */
    public Boolean isInclusive(TypeId parent, TypeId child, TypeId lca) {
        // Check path from the lca to both guys to determine type-partiality
        TypeId current = parent;
        //System.out.println("Got here " + parent.getDLN() + " " + child.getDLN() + " " + lca.getDLN());
        //for (TypeId t : tab.keySet()) {
        //    System.out.println("key is " + t.getDLN());
        //}
        while (!current.equals(lca)) {
            TypeId parentTypeId = current.getParent();
            //System.out.println("insideP " + parentTypeId.getDLN());
            //Set<TypeId> s = tab.get(parentTypeId);
            //if (s != null) {
            if (tab.containsKey(parentTypeId)) {
                //System.out.println("Ppartial " + parentTypeId.getDLN());
                if (tab.get(parentTypeId).contains(current)) {
                    // We have something that is partial

                    return false;
                }
            } else {
                //System.out.println("Pnot in tab " + parentTypeId.getDLN());
            }
            current = parentTypeId;
        }
        //System.out.println("print current");
        current = child;
        while (!current.equals(lca)) {
            TypeId parentTypeId = current.getParent();
            //System.out.println("inside " + parentTypeId.getDLN().toString());
            if (tab.containsKey(parentTypeId)) {
                if (tab.get(parentTypeId).contains(current)) {
                    // We have something that is partial
                    //System.out.println("Cpartial " + parentTypeId.getDLN().toString());
                    return false;
                }
            }
            current = parentTypeId;
        }
        return true;
    }
}
