package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.graph.Id;
import java.util.*;

/**
 * Master table that stores all of the grouped node information. 
 * Maps a node Id to the grouped Node information.
 * @author Curtis
 */
public class GroupedNodesTable {

    private Map<Id, Node> tab;

    public GroupedNodesTable(Map<Id, Node> tab) {
        this.tab = tab;
    }

    /* Add this node to the table using the indicated Id.
     * @param n - node to add
     */
    public void put(Id i, Node n) {
        tab.put(i, n);
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
