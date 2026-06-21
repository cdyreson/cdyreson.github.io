package xmorph.edu.usu.algebra;

import java.util.*;

/**
 * A Dictionary stores the mapping of names in a translation.
 * 
 * @author Kiran
 */
public class Dictionary {

    Map<String, String> dMap;

    public void Dictionary() {
        dMap = new HashMap();
    }

    public String get(String s) {
        if (dMap.containsKey(s)) {
            return dMap.get(s);
        }
        return s;
    }

    public boolean containsKey(String s) {
        return dMap.containsKey(s);
    }

    public String getValue(String t) {
        for (String source : dMap.keySet()) {
            if (dMap.get(source).equals(t)) {
                return source;
            }
        }
        return t;
    }

    public void put(String a, String b) {
        //I don't know why the following check would be needed, but
        //it fixes a bug!
        if (dMap == null) {
            dMap = new HashMap();
        }
        dMap.put(a, b);
    }
}


