package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/**
 * Maps the name of a document to the TypeId of the document root type.
 * @author Curtis
 */
public class DocumentsTable {

    private Map<String, TypeId> tab;

    public DocumentsTable(Map<String, TypeId> tab) {
        this.tab = tab;
    }

    public TypeId get(String s) {
        TypeId d = tab.get(s);
        if (d == null) {
            System.err.println("No information about the XML document " + s);
        }
        return d;
    }

    /* Finish the insertion of an XML document in the database.
     * @param fileName - Name of the file to insert
     * @param rootTypeId - TypeId of the root type
     */
    public TypeId put(String fileName, TypeId t) {
        //System.out.println("putting information about the XML document " + s);
        /*
        TypeId d = tab.get(s);
        if (d == null) {
        d = new TypeId(s);
        }
         */
        if (fileName == null || fileName.contentEquals("")) {
            int i = 0;
            while (tab.containsKey("default" + i)) {
                i++;
            }
            fileName = "default" + i;
        }
        tab.put(fileName, t);
        return t;
    }
}
