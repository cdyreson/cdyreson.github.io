/*
 * The Graph class implements a Graph structure to hold the XML output.
 * Each edge in the Graph represents a parent/child edge. 
 */
package xmorph.edu.usu.evaluation.shapelyidmerge;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import xmorph.edu.usu.graph.DocumentRoot;
import xmorph.edu.usu.graph.Node;

/**
 *
 * @author Curtis
 */
public class Graph {

    Map<Node, SortedSet<Node>> edges;
    Map<Node, Node> groupMap;
    public Node root;
    //Map<GroupId, List<Node>> groupEdges = new HashMap();

    public Graph() {
        root = new DocumentRoot();
        edges = new HashMap();
        groupMap = new HashMap();
    }

    /* Add an edge to the edges */
    public void addEdge(Node key, Node value) {
        //Never add an edge to self, we shouldn't need this?!
        if (key.equals(value)) {
            //System.out.println("phooo");
            return;
        }

        // Map the value to a group if it is grouped
        if (groupMap.containsKey(value)) {
            value = groupMap.get(value);
        }

        // Map the key to a group if it is grouped
        if (groupMap.containsKey(key)) {
            key = groupMap.get(key);
        }

        //System.out.println("Key is " + key.getId().toString() + " " + key.getGroupNode().imageXML() + " " + key.isGrouped());
        //System.out.println("adds edge " + key.getId().toString() + " " + key.imageXML() + " " + value.getId().toString() + " " + value.imageXML());
        // Is key grouped?
        if (key.isGrouped() && key.getGroupNode() != null) {
            //System.out.println("grouped " + key + " " + key.getGroupNode());
            // Map the key to its group
            if (!groupMap.containsKey(key)) {
                groupMap.put(key, key.getGroupNode());
            }
            key = groupMap.get(key);
        }

        // Is it already in the graph?
        if (!edges.containsKey(key)) {
            //System.out.println("New edge " + key.getId().toString() + " " + key.getGroupNode().imageXML() + " to " + value.getId());
            edges.put(key, new TreeSet());
        }
        edges.get(key).add(value);

        key.setNoAttribute();
    }

    /* Add an edge to the edges */
    /*
     public void addLeaf(Node key) {
     // If a leaf is grouped, does it matter?
     if (!edges.containsKey(key)) {
     edges.put(key, new TreeSet());
     }
     }
     */
    public SortedSet<Node> getChildren(Node key) {
        if (edges.containsKey(key)) {
            //System.out.println("found key");
            return edges.get(key);
        }
        //System.out.println("did not find key " + key.getId().toString());
        return new TreeSet();
    }

    public String imageXMLPrettyPrint(Node key, String indent) {
        String s = "";
        // Walk the edges, doing pretty print
        s += indent;
        // Do the children
        s += key.imageXMLPrettyPrint("   ", false, this);
        //System.out.println(" imagePrettyPrint s is " + s);
        return s;
    }

    //public SortedSet<Node> rootChildren() {
    //    Node root = new DocumentRoot();
    //    return edges.get(root.getGroupId());
    //}
    public String imageXMLPrettyPrint() {
        //Node root = new DocumentRoot();
        //System.out.println("PrettyPrinting " + root.getId());
        return imageXMLPrettyPrint(root, "");
    }
}
