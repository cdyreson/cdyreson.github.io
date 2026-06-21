package xmorph.edu.usu.evaluation.normal;

import xmorph.edu.usu.shape.ShapeNode;
//import xmorph.edu.usu.shape.*;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.algebra.Comparator;
import java.util.*;
import xmorph.edu.usu.database.Database;
//import org.exist.numbering.DLNBase;

/**
 * A NewTagEvalNode is created to evaluate a ShapeNode. The node could be grouped,
 * cloned, and hidden, but in general it represents an Iterator over a set of
 * Ids for a Type.
 *
 * @author Curtis and Kiran
 */
public class EvalNode {

    Comparator wherePredicate;
    Iterator<Id> idIterator;
    Id lca;
    TreeMap<Id, Node> collectedNodes;
    Id currentId;
    ShapeNode shapeNode;
    List<EvalNode> children;
    Database db;
    boolean nodesCached = false;
    Node cachedParent;
    static Id rootId = new Id();
    int level = 0;
    List<EvalNode> dynamicallyGroupedEvalNode;
    Map<String, Node> dynamicGroupTable;

    //Graph dynamicallyGroupedGraph;
    public EvalNode(ShapeNode shapeNode, Database db) {
        this.db = db;
        lca = rootId;
        this.shapeNode = shapeNode;
        //System.out.println(shapeNode.getBaseTypeId().getName());
        this.setIdIterator(db.typedNodeTableCollection.iterator(shapeNode.getBaseTypeId()));
        children = new ArrayList();
        this.nextId();
        collectedNodes = new TreeMap();
        dynamicGroupTable = new HashMap();
        //dynamicallyGroupedGraph = new Graph();
        for (ShapeNode s : shapeNode.getChildren()) {
            EvalNode n = new EvalNode(s, db);
            n.determineLCALevel(this, db);
            //System.out.println("Adding child " + s.image() + " child "+ shapeNode.image());
            children.add(n);
        }
    }

    public TypeId getBaseTypeId() {
        return shapeNode.getBaseTypeId();
    }

    public void determineLCALevel(EvalNode parent, Database db) {
        level = this.getBaseTypeId().lca(parent.getBaseTypeId()).getTreeLevel();
        //System.out.println("LCAlevel " + level + "|" + parent.image() + "!" + this.image());
        //System.out.println(this.getBaseTypeId().toString() + "!" + parent.getBaseTypeId().toString());
        //level = this.shapeNode.getLCA().getDLN().getLevelCount(0);
    }

    public void setIdIterator(Iterator<Id> idIterator) {
        this.idIterator = idIterator;
    }

    // Generate the next Id in this list
    public Id nextId() {
        //System.out.println("EVALNODE evaluating " + idIterator.toString());
        if (idIterator.hasNext()) {
            currentId = idIterator.next();
            //System.out.println("EVALNODE currentId is " + currentId.toString());
        } else {
            currentId = null;
        }
        return currentId;
    }

    public List<EvalNode> children() {
        return children;
    }

    // Generate the next Id in this list
    public Id thisId() {
        return currentId;
    }

    public boolean hasNext() {
        //if (nodesCached) return true;
        return idIterator.hasNext();
    }

    public String image() {
        String s = shapeNode.image();
        return s;
    }

    public boolean evaluate(Node parent, ShapeNode parentShapeNode, Graph graph) {
        // For this parent, let's determine if we already have the appropriate children
        //System.out.println("EVALNODE " + currentId + " Evaluating " + parent.getId().toString());
        //System.out.println("EVAL " + parent);
        boolean haveSelf = false;
        boolean needSelf = !shapeNode.isOptional();

        if (nodesCached) {
            // If we don't have children, is that a problem?
            if (collectedNodes.isEmpty()) {
                //System.out.println("EvalNode.evaluate No cached children.");
                //System.exit(-1);
            } else {
                // Check to see if what has been cached is a child for the parent
                Id firstKey = collectedNodes.firstKey();


                if (firstKey.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
                    // Iterate through the cached children
                    //System.out.println("EVALNODE adding child " + firstKey.getDLN() +  " " + parent.getId().getDLN());

                    for (Node n : collectedNodes.values()) {
                        graph.addEdge(parent, n);
                    }
                    return true;
                } else {
                    nodesCached = false;
                    collectedNodes = new TreeMap();
                }
            }
        }

        // Are we at the end? If so then just return whether we needed self
        if (currentId == null) {
            //System.out.println("here " + needSelf);
            return !needSelf;
        }

        // Check to see if this child has a common LCA with the parent
        //System.out.println("Checking commonLCA " + currentId.getDLN().toString() + " " + parent.getId().getDLN().toString() + " " + level);
        if (!currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
            //System.out.println("No commonLCA ");
            // No common LCA
            // Check if parent is before child, exit
            if (parent.getId().getDLN().before(currentId.getDLN(), true)) {
                // This parent has no children
                return needSelf;
            }

            // Advance child until it is caught up with the parent
            while (currentId.getDLN().before(parent.getId().getDLN(), true)
                    && !currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
                nextId();
                // Have we reached the end of our types?
                if (currentId == null) {
                    // This parent has no children
                    return needSelf;
                }
            }
            // The child is after the parent, so if it lacks a common ancestor then
            // parent has no children.
            if (!currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
                return needSelf;
            }
        }

        // Now we know that we have at least one child for this parent
        // Let's compute the children for this node
        collectedNodes = new TreeMap();
        nodesCached = true;

        while (currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
            Node n = db.nodesTable.get(currentId);
            //System.out.println(parent.getId().toString() + " Cloning " + n.getId().toString() + "  " + level);
            n = n.cloneMe();
            // Fill in the name of this node
            n.setName(shapeNode.getBaseTypeId().getName());

            // Do I have a where clause?
            if (shapeNode.hasWhere()) {
                // Skip this node unless it satisfies the where clause
                if (!shapeNode.getWhere().evaluate(n)) {
                    currentId = nextId();
                    if (currentId == null) {
                        break;
                    }
                    continue;
                }
            }

            // Got me past the where clause!
            haveSelf = true;

            // Am I hidden?
            if (shapeNode.isHidden()) {
                n.hide();
            }

            // Am I grouped?
            if (shapeNode.isGrouped()) {
                //System.out.println("I'm grouped");
                n.setGrouped();
            }

            // Should I be an attribute?
            if (shapeNode.isForceAttribute()) {
                n.setAttribute();
            }

            // Should I be an element?
            if (shapeNode.isForceElement()) {
                n.clearAttribute();
            }
            // Am I dynamically grouped?
            if (shapeNode.isDynamicallyGrouped()) {
                //System.out.println("Dynamically grouped");

                // We have to compute the group value
                if (dynamicGroupTable == null) {
                    dynamicGroupTable = new HashMap();
                }

                if (dynamicallyGroupedEvalNode == null) {
                    dynamicallyGroupedEvalNode = new ArrayList();
                    for (ShapeNode s : shapeNode.getDynamicallyGrouped().getChildren()) {
                        //System.out.println("Dynamically g " + s.image() + s.imageXML());

                        EvalNode f = new EvalNode(s, db);
                        f.determineLCALevel(this, db); // We have to set the LCA explicitly
                        dynamicallyGroupedEvalNode.add(f);
                    }
                }

                // Compute this graph, or should we just compute one?
                Graph dynamicG = new Graph();
                for (EvalNode v : dynamicallyGroupedEvalNode) {
                    //System.out.println("evaluating " + v.image() + "# " + n.getId() + "# "  + n.image() + "# " + v.shapeNode.imageXML());
                    v.evaluate(n, shapeNode, dynamicG);
                }

                String groupValue = dynamicG.imageXMLPrettyPrint(n, "");
                //System.out.println("|" + groupValue + "|");
                if (dynamicGroupTable.containsKey(groupValue)) {
                    //System.out.println("found group value " + groupValue);
                    Node groupedNode = dynamicGroupTable.get(groupValue);
                    //n.setGroupId(groupedNode.getId());
                    n.setGroupNode(groupedNode);
                    n.setGrouped();
                } else {
                    //System.out.println("new group value " + groupValue);
                    dynamicGroupTable.put(groupValue, n);
                    //n.setGroupId(n.getId());
                    n.setGroupNode(n);  // Is set in Node, so redundant
                    n.setGrouped();
                }

            }
            // Do the children of this node
            for (EvalNode eval : children) {
                //System.out.println("Evaluating children " + eval.image() + " " + n.getId().toString());
                boolean success = eval.evaluate(n, shapeNode, graph);
                // If the child fails and I need it, then I'm no good
                if (needSelf) {
                    haveSelf = haveSelf && success;
                }
            }

            // If some of my children failed, then maybe skip this one
            if (!haveSelf) {
                currentId = nextId();
                if (currentId == null) {
                    break;
                }
                continue;
            }

            Id id = n.getId();
            collectedNodes.put(id, n);

            currentId = nextId();
            if (currentId == null) {
                break;
            }
        }

        // Iterate through the cached children
        for (Node n : collectedNodes.values()) {
            //System.out.println("Adding child " + n.imageXML() + " " + parent.imageXML());
            //System.out.println("Adding child " + n.image() + " " + parent.image());
            //parent.addChild(n);
            graph.addEdge(parent, n);
        }

        return haveSelf;
    }
}
