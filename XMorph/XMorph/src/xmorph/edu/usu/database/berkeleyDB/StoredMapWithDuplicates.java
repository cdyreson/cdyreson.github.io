package xmorph.edu.usu.database.berkeleyDB;

import xmorph.edu.usu.database.*;
//import com.sleepycat.collections.StoredMap;
//import com.sleepycat.je.DatabaseException;
//import com.sleepycat.je.Environment;
//import com.sleepycat.je.EnvironmentConfig;
//import com.sleepycat.je.DatabaseConfig;
//imports for BerkleyDb bindings 
import com.sleepycat.bind.EntryBinding;
//import com.sleepycat.bind.serial.SerialBinding;
//import com.sleepycat.bind.tuple.TupleBinding;
//import com.sleepycat.bind.serial.StoredClassCatalog;
//import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.collections.StoredMap;
//import com.sleepycat.je.Cursor;
import java.util.*;

/**
 * The HashMapWithDuplicates class extends a Map with duplicate key
 * values. It does this by using a Map within a Map.
 * @author Curtis
 */
public class StoredMapWithDuplicates<K, V> extends StoredMap<K, V> implements MapWithDuplicates<K, V> {
    //StoredMap<K, V> tab;

    /*
    public StoredMapWithDuplicates(StoredMap<K, V> tab) {
        this.tab = tab;
    }
    */
    
    public StoredMapWithDuplicates(com.sleepycat.je.Database tableDb, EntryBinding e1, EntryBinding e2, boolean writeAllowed) {
        super(tableDb, e1, e2, writeAllowed);
        //this.tab = tab;
    }

    /* Model a duplicate key with an additional table 
    @Override
    public V put(K key, V value)  {
        // Open a new table if key doesn't exist
        if (!tab.containsKey(key)) {
            tab.put(key, new HashSet());
        }
        HashSet h = (HashSet)tab.get(key);
        if (!h.contains(value)) {
            h.add(value);
        }
        return null;
    }
     * */

    /* Iterate over the values associated with this key */
    public Iterator iterator(K key) {
        // Open a new table if key doesn't exist
        return this.duplicates(key).iterator();
    }
    
    /* Remove this key value pair */
    //public void remove(Object key, Object value) {
    //    remove((K)key, (V)value);
    //}
    
    /* Remove this key value pair */
    /*
    public void remove(K key, V value) {
        this.remove((Object) key, (Object) value);
    }
    */

    /* Remove this key value pair 
    public V putIfAbsent(K key, V value) {
        this.remove((Object) key, (Object) value);
    }
    */

}
