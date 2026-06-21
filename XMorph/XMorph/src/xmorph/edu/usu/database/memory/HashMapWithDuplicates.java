package xmorph.edu.usu.database.memory;

import xmorph.edu.usu.database.*;
import java.util.*;

/**
 * The HashMapWithDuplicates class extends a Map with duplicate key
 * values. It does this by using a Map within a Map.
 * @author Curtis
 */
public class HashMapWithDuplicates<K, V> extends HashMap<K, V> implements MapWithDuplicates<K, V> {

    HashMap tab;

    public HashMapWithDuplicates() {
        tab = new HashMap();
    }

    /* Model a duplicate key with an additional table */
    @Override
    public V put(K key, V value) {
        // Open a new table if key doesn't exist
        if (!tab.containsKey(key)) {
            tab.put(key, new HashSet());
        }
        HashSet h = (HashSet) tab.get(key);
        if (!h.contains(value)) {
            h.add(value);
        }
        return null;
    }

    /* Iterate over the values associated with this key */
    public Iterator iterator(K key) {
        // Open a new table if key doesn't exist
        if (!tab.containsKey(key)) {
            Set s = new HashSet();
            return s.iterator();
        }
        HashSet h = (HashSet) tab.get(key);
        return h.iterator();
    }

    /* Remove this key value pair */
    //public void remove(Object key, Object value) {
    //    remove((K)key, (V)value);
    //}
    /* Remove this key value pair */
    public V putIfAbsent(K key, V value) {
        // Open a new table if key doesn't exist
        Set s;
        if (!tab.containsKey(key)) {
            s = new HashSet();
        }
        else {
            s = (HashSet)tab.get(key);
        }
        if (!s.contains(value)) {
            s.add(value);
            return value;
        }
        return null;
    }

    /* Remove this key value pair */
    public void remove(K key, V value) {
        // Open a new table if key doesn't exist
        if (!tab.containsKey(key)) {
            return;
        }
        HashSet h = (HashSet) tab.get(key);
        h.remove(value);
    }
}
