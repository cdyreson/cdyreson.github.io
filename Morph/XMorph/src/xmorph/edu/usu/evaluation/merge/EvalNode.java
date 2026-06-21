package xmorph.edu.usu.evaluation.merge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xmorph.edu.usu.algebra.Comparator;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
//import org.exist.numbering.DLNBase;

/**
 * Merging evaluates the shape into a merged list of nodes.
 *
 * @author Curtis
 */
public class EvalNode {

    Comparator wherePredicate;
    CachedIterator idIterator;
    //Id lca;
    //TreeMap<Id, Node> collectedNodes;
    //Id currentId;
    ShapeNode shapeNode;
    List<EvalNode> children;
    Database db;
    boolean nodesCached = false;
    Node cachedParent;
    static Id rootId = new Id();
    int level;
    List<EvalNode> dynamicallyGroupedEvalNode;
    Map<String, Node> dynamicGroupTable;
    MergedListIterator childrenIterator;
    Map<Integer, EvalNode> childrenEvalNodesMap;
    //List<CachedIterator> resetActionList;
    //List<CachedIterator> iteratorList;
    List<Integer> levelList;
    boolean rootNode;
    boolean freeMe;

    //Graph dynamicallyGroupedGraph;
    public EvalNode(ShapeNode shapeNode, Database db, boolean parentFreeMe) {
        initialize(shapeNode, db, parentFreeMe);
        if (shapeNode.getBaseTypeId() == null) {
            //System.out.println("EVALNODE Shape root node");
            rootNode = true;
        } else {
            rootNode = false;
            level = getBaseTypeId().getDLN().getTreeLevel();
            idIterator = new CachedIterator(db.typedNodeTableCollection.iterator(shapeNode.getBaseTypeId()));
            //System.out.println("EVALNODE shapenode " + db.typesLookupTable.get(shapeNode.getBaseTypeId()) + " " + shapeNode.getBaseTypeId().getName());
        }
        buildChildren(parentFreeMe);
    }

    public EvalNode(ShapeNode shapeNode, Database db, boolean parentFreeMe, CachedIterator iter) {
        initialize(shapeNode, db, parentFreeMe);
        if (shapeNode.getBaseTypeId() == null) {
            //System.out.println("EVALNODE Shape root node");
            rootNode = true;
        } else {
            rootNode = false;
            level = getBaseTypeId().getDLN().getTreeLevel();
            idIterator = iter;
            //System.out.println("EVALNODE shapenode " + db.typesLookupTable.get(shapeNode.getBaseTypeId()) + " " + shapeNode.getBaseTypeId().getName());
        }
        buildChildren(parentFreeMe);
    }

    private void initialize(ShapeNode shapeNode, Database db, boolean parentFreeMe) {
        this.db = db;
        this.shapeNode = shapeNode;
        childrenIterator = new MergedListIterator();
        childrenEvalNodesMap = new HashMap();
        children = new ArrayList();
        //iteratorList = new ArrayList();
        levelList = new ArrayList();
        dynamicGroupTable = new HashMap();
        freeMe = parentFreeMe;
        rootNode = false;
    }

    private void buildChildren(boolean parentFreeMe) {
        // Check to see if we have any children with lcas above us, if not can free
        for (ShapeNode child : shapeNode.getChildren()) {
            TypeId childTypeId = child.getBaseTypeId();
            int lcaLevel = determineLCALevel(childTypeId, db);
            /*
             * if (!rootNode) { System.out.println("EVALNODE levels " +
             * shapeNode.getBaseTypeId().getName() + " " +
             * child.getBaseTypeId().getName() + " " + level + " " + lcaLevel);
             * }
             */
            if (lcaLevel < level) {
                freeMe = false;
            }
            //System.out.println("freeMe " + freeMe + " " + parentFreeMe);
        }
        // Build an iterator of all my children
        for (ShapeNode child : shapeNode.getChildren()) {
            TypeId childTypeId = child.getBaseTypeId();
            Integer childType = db.typesLookupTable.get(childTypeId);
            int lcaLevel = determineLCALevel(childTypeId, db);
            EvalNode childEvalNode = new EvalNode(child, db, freeMe && parentFreeMe);
            CachedIterator childIterator = childEvalNode.getIterator();
            childrenIterator.addFeed(childIterator);

            levelList.add(new Integer(lcaLevel));
            //iteratorList.add(childIterator);
            //System.out.println("EVALNODE lcalevel " + lcaLevel + " " + childTypeId.getName());
            childIterator.setLevel(lcaLevel);

            //System.out.println("getting baseid type " + childEvalNode.getBaseTypeId());
            //System.out.println("lookup " + db.typesLookupTable.get(childTypeId));
            childrenEvalNodesMap.put(childType, childEvalNode);
            children.add(childEvalNode);
        }
    }

    public CachedIterator getIterator() {
        return idIterator;
    }

    final public TypeId getBaseTypeId() {
        return shapeNode.getBaseTypeId();
    }

    final public int determineLCALevel(TypeId child, Database db) {
        //System.out.println("Child is " + child);
        if (rootNode) {
            return child.getDLN().getTreeLevel();
        }
        return this.getBaseTypeId().lca(child).getTreeLevel();
        //System.out.println("LCAlevel " + level + "|" + parent.image() + "!" + this.image());
        //System.out.println(this.getBaseTypeId().toString() + "!" + parent.getBaseTypeId().toString());
        //level = this.shapeNode.getLCA().getDLN().getLevelCount(0);
    }

    public List<EvalNode> children() {
        return children;
    }

    public String image() {
        String s = shapeNode.image();
        return s;
    }

    public Node kickStartRoot() {
        // Check if we have any more nodes
        idIterator.setRoot();

        if (!idIterator.hasNext()) {
            return null;
        }

        // Fetch the next node
        Id currentId = idIterator.next();
        return db.nodesTable.get(currentId);
    }

    public boolean evaluate(Graph graph) {
        Node currentNode = kickStartRoot();
        if (currentNode == null) {
            return false;
        }
        return evaluate(currentNode, graph);
    }

    public void freeCache() {
        idIterator.freeCache();
        for (EvalNode child : children) {
            child.freeCache();
        }
    }

    public boolean evaluate(Node currentNode, Graph graph) {
        if (db.typesTable.get(shapeNode.getTypeId()) != null) {
            //System.out.println("EVALNODE start " + currentNode + " " + db.typesTable.get(shapeNode.getTypeId()).image());
            //boolean needSelf = !shapeNode.isOptional();
        }

        // Set the parent
        Id currentId = currentNode.getId();
        //System.out.println("EVALNODE hasNext? " + currentId.getDLN().toString());
        for (EvalNode c : children) {
            c.getIterator().setParent(currentId);
        }

        // For each child that is in the "range" of the current node
        // add it to the graph
        // Reset the merged list iterator since it must be empty
        childrenIterator.reset();
        while (childrenIterator.hasNext()) {
            //System.out.println("EVALNODE currentId " + currentId.getDLN());
            Id id = childrenIterator.next();
            int typeNum = id.getDLN().getTypeID();
            //System.out.println("EVALNODE typenum " + typeNum + " " + id.getDLN());
            EvalNode childEvalNode = childrenEvalNodesMap.get(typeNum);
            Node node = db.nodesTable.get(id);
            //System.out.println("EVALNODE evaluate child ");

            // Do I have a where clause? 
            if (shapeNode.hasWhere()) {
                // Skip this node unless it satisfies the where clause 
                if (!shapeNode.getWhere().evaluate(currentNode)) {
                    continue;
                }
            }

            // Got me past the where clause!

            // Am I hidden? 
            if (shapeNode.isHidden()) {
                currentNode.hide();
            }

            // Am I grouped? 
            if (shapeNode.isGrouped()) {
                //System.out.println("I'm grouped"); 
                //shapeNode.setDynamicallyGrouped(shapeNode);
                Node groupNode = db.groupedNodesTable.get(currentId);
                currentNode.setGroupNode(groupNode);
            }

            // Should I be an attribute? 
            if (shapeNode.isForceAttribute()) {
                currentNode.setAttribute();
            }

            // Should I be an element? 
            if (shapeNode.isForceElement()) {
                currentNode.clearAttribute();
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

                        EvalNode f = new EvalNode(s, db, true);
                        f.determineLCALevel(this.getBaseTypeId(), db);
                        //We have to set the LCA explicitly 
                        dynamicallyGroupedEvalNode.add(f);
                    }
                }

                // Compute this graph, or should we just compute one? 
                Graph dynamicG = new Graph();
                for (EvalNode v : dynamicallyGroupedEvalNode) {
                    //System.out.println("evaluating " + v.image() + "# " + n.getId() + "# " + currentNode.image() + "# " + v.shapeNode.imageXML()); 
                    v.evaluate(currentNode, dynamicG);
                }

                String groupValue = dynamicG.imageXMLPrettyPrint(currentNode, "");
                //System.out.println("|" + groupValue + "|");
                if (dynamicGroupTable.containsKey(groupValue)) {

                    Node groupedNode = dynamicGroupTable.get(groupValue);
                    //System.out.println("found group value " + groupValue + " " + groupedNode.getId());
                    //currentNode.setGroupId(groupedNode.getId()); 
                    currentNode.setGroupNode(groupedNode);
                    currentNode.setGrouped();
                } else {
                    //System.out.println("new group value " + groupValue);
                    dynamicGroupTable.put(groupValue, currentNode);
                    //n.setGroupId(n.getId()); 
                    currentNode.setGroupNode(currentNode);
                    // Is set in Node, so redundant 
                    currentNode.setGrouped();
                }
            }


            if (childEvalNode.evaluate(node, graph)) {
                graph.addEdge(currentNode, node);
            }
        }

        if (freeMe && !rootNode) {
            //System.out.println("Can Free " + shapeNode.getBaseTypeId().getDLN() + " " + currentId.getDLN());
            freeCache();
        }
        return true;
    }
}
