package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.graph.Id;
import java.util.*;

/**
 * Master table that stores all of the node information. 
 * Maps a node Id to the Node information.
 * @author Curtis
 */
public class NodesTable {

    private Map<Id, Node> tab;

    public NodesTable(Map<Id, Node> tab) {
        this.tab = tab;
    }

    /* Add this node to the table. Every node Id is unique.
     * @param n - node to add
     */
    public void put(Node n) {
        tab.put(n.getId(), n);
    }

    /* Remove this node from the table. Every node Id is unique.
     * 
     * @param n - node to remove
     */
    public void remove(Node n) {
        tab.remove(n.getId());
    }

    /* Retrieve a Node from the table, given an Id
     * Node has to exist for this to work.
     * @param n - Id to lookup
     * @returns - found node (or null if no Node?)
     */
    public Node get(Id n) {
        return tab.get(n);
    }
}
