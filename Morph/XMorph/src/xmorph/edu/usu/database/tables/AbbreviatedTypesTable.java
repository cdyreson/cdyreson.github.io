package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/**
 * Maps an element name to a list of type ids
 * 
 * @author Aswani and Curtis
 */
public class AbbreviatedTypesTable {

    private Map<String, List<TypeId>> tab;

    public AbbreviatedTypesTable(Map<String, List<TypeId>> tab) {
        this.tab = tab;
    //tab = db.openTableStringListTypeId("abbreiviatedTypes");
    }

    public void put(String s, TypeId t) {
        List<TypeId> a = tab.get(s);
        if (a == null) {
            a = new ArrayList<TypeId>();
        }
        a.add(t);
        tab.put(s, a);
    }

    public void remove(String s, TypeId t) {
        List<TypeId> a = tab.get(s);
        if (a == null) {
            a = new ArrayList<TypeId>();
        }
        a.remove(t);
        tab.put(s, a);
    }

    public List<TypeId> get(String s) {
        List<TypeId> a = tab.get(s);
        return a;
    }
}
