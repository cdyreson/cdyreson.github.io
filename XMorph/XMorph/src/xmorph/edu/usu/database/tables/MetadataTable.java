package xmorph.edu.usu.database.tables;

import java.util.*;

/**
 * The metadata table is used to store useful information about the documents
 * in the data store. It maps a String to a Long.
 * 
 * @author Curtis
 */
public class MetadataTable {

    private Map<String, Long> tab;

    public MetadataTable(Map<String, Long> tab) {
        this.tab = tab;
    }

    /* Write a tuple to the metadata table */
    public void put(String s, long d) {
        Long x = new Long(d);
        tab.put(s, x);
    }

    /* Lookup up the value in the metadata table.
     * Key is a String.
     */
    public long get(String s) {
        Long x = (Long) tab.get(s);
        long d = 0;
        if (x == null) {
            put(s, d);
        } else {
            d = x.longValue();
        }
        return d;
    }
}
