/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmorph.edu.usu.evaluation.normal;

import java.util.*;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.graph.DocumentRoot;

/**
 *
 * @author Curtis
 */
public class Graph {

    Map<Node, SortedSet<Node>> edges = new HashMap();
    Node root = null;
    //Map<GroupId, List<Node>> groupEdges = new HashMap();

    public Graph() {
        root = new DocumentRoot();
        edges = new HashMap();
    }

    /* Add an edge to the edges */
    public void addEdge(Node key, Node value) {
        //Never add an edge to self, we shouldn't need this?!
        if (key.equals(value)) {
            //System.out.println("phooo");
            return;
        }
        //System.out.println("Key is " + key.getId().toString() + " " + key.getGroupNode().imageXML() + " " + key.isGrouped());
        //System.out.println("add edge " + key.getId().toString() + " " + value.getId().toString() + " " + value.imageXML());
        if (!edges.containsKey(key)) {
            //System.out.println("New edge " + key.getId().toString() + " " + key.getGroupId().toString() + " " + key.isGrouped());

            edges.put(key, new TreeSet());
        }
        if (key.isGrouped() && key.getGroupNode() != null) {
            edges.get(key.getGroupNode()).add(value);
        } else {
            edges.get(key).add(value);
        }
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
        /* CURT TEMPORARILY REMOVED */
        /*
        s += key.imageXMLPrettyPrint("   ", false, this);
         */
        //System.out.println(" s is " + s);
        return s;
    }

    //public SortedSet<Node> rootChildren() {
    //    Node root = new DocumentRoot();
    //    return edges.get(root.getGroupId());
    //}
    public String imageXMLPrettyPrint() {
        //Node root = new DocumentRoot();
        return imageXMLPrettyPrint(root, "");
    }
}
