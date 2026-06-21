package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;

/**
 * Maps a filename (a String) to a Shape (a forest of ShapeNodes, but
 * because is XML, the forest has a single root)
 * 
 * @author Curtis
 */
public class ShapesTable {

    private Map<String, ShapeNode> tab;

    /**
     * Open the table
     * 
     * @author Curtis
     */
    public ShapesTable(Map<String, ShapeNode> tab) {
        this.tab = tab;
    }

    /**
     * Add a new ShapeNode to the table
     * 
     * @param fileName - File name, which is the key, for the ShapeNode
     * @param shapeNode - ShapeNode to add
     * @author Curtis
     */
    public void put(String fileName, ShapeNode shapeNode) {
        //System.out.println("Shapes table put " + fileName + " " + shapeNode.imageXML());
        if (fileName == null || fileName.contentEquals("")) {
            int i = 0;
            while (tab.containsKey("default" + i)) {
                i++;
            }
            fileName = "default" + i;
        }
        //System.out.println("Shapes table put " + fileName + " " + shapeNode.imageXML());
        tab.put(fileName, shapeNode);
    }

    /**
     * Determine whether the key is in the table
     * 
     * @param fileName - String, file name
     * @return - is file name in the table
     * @author Curtis
     */
    public boolean containsKey(String fileName) {
        return tab.containsKey(fileName);
    }

    /**
     * Fetch a ShapeNode from the table
     * 
     * @param fileName - String, file name
     * @return - fetched Shape
     * @author Curtis
     */
    public ShapeNode get(String fileName) {
        if (!tab.containsKey(fileName)) {
            /*
            for (String s: tab.keySet()) {
                System.out.println("Filename is " + s);
            }
            */
            System.out.println("ShapesTable.java: error no such shapeNode for " + fileName);
            System.exit(-1);
        }
        return tab.get(fileName);
    }

    /*
     * Iterator over the keys in the ShapesTable.
     * 
     */
    public Iterator<String> iterator() {
        return tab.keySet().iterator();
    }

    /*
     * Fetch the keySet for this table.
     * 
     */
    public Set<String> keySet() {
        return tab.keySet();
    }
}
