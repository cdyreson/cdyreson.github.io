package xmorph.edu.usu.evaluation.shapelyidmerge;

import xmorph.edu.usu.shape.ShapeNode;
//import xmorph.edu.usu.shape.*;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.graph.DocumentRoot;
import xmorph.edu.usu.algebra.Comparator;
import java.util.*;
import xmorph.edu.usu.database.Database;
import xmorph.xml.XMLWriter;
import javax.xml.transform.TransformerException;

//import org.exist.numbering.DLNBase;

/**
 * Merging evaluates the shape into a merged list of nodes.
 *
 * @author Curtis
 */
public class WriterEvalNode {

    Comparator wherePredicate;
    Iterator<Id> idIterator;
    Id lca;
    TreeMap<Id, Node> collectedNodes;
    Id currentId;
    ShapeNode shapeNode;
    List<WriterEvalNode> children;
    Database db;
    boolean nodesCached = false;
    Node cachedParent;
    static Id rootId = new Id();
    //int level = 0;
    List<WriterEvalNode> dynamicallyGroupedEvalNode;
    Map<String, Node> dynamicGroupTable;
    MergedListIterator childrenIterator;
    Map<Integer, WriterEvalNode> childrenEvalNodesMap;
    XMLWriter writer;

    //Graph dynamicallyGroupedGraph;
    public WriterEvalNode(ShapeNode shapeNode, Database db, XMLWriter writer) {
        this.db = db;
        this.writer = writer;
        lca = rootId;
        this.shapeNode = shapeNode;
        childrenIterator = new MergedListIterator();
        childrenEvalNodesMap = new HashMap();
        children = new ArrayList();
        collectedNodes = new TreeMap();
        dynamicGroupTable = new HashMap();
        idIterator = db.typedNodeTableCollection.iterator(shapeNode.getBaseTypeId());
        // Build an iterator of all my children
        for (ShapeNode child : shapeNode.getChildren()) {
            //System.out.println("shapenode " + db.typesLookupTable.get(child.getBaseTypeId()));
            childrenIterator.addFeed(db.typedNodeTableCollection.iterator(child.getBaseTypeId()));
            WriterEvalNode n = new WriterEvalNode(child, db, writer);
            //n.determineLCALevel(this);
            //System.out.println("getting baseid type " + n.getBaseTypeId());
            //System.out.println("lookup " + db.typesLookupTable.get(child.getBaseTypeId()));
            childrenEvalNodesMap.put(db.typesLookupTable.get(child.getBaseTypeId()), n);
            children.add(n);
        }

    }

    public TypeId getBaseTypeId() {
        return shapeNode.getBaseTypeId();
    }

    //public void determineLCALevel(WriterEvalNode parent) {
    //    level = this.getBaseTypeId().lca(parent.getBaseTypeId()).getTreeLevel();
    //System.out.println("LCAlevel " + level + "|" + parent.image() + "!" + this.image());
    //System.out.println(this.getBaseTypeId().toString() + "!" + parent.getBaseTypeId().toString());
    //level = this.shapeNode.getLCA().getDLN().getLevelCount(0);
    //}
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

    public List<WriterEvalNode> children() {
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

    // No parent node, parent is the root
    public boolean evaluate() {
        return evaluate(new DocumentRoot(), new ShapeNode());
    }

    public boolean evaluate(Node parent, ShapeNode parentShapeNode) {
        // For this parent, let's determine if we already have the appropriate children
        //System.out.println("EVALNODE " + currentId + " Evaluating " + parent.getId().toString());
        //System.out.println("EVAL " + parent);
        boolean needSelf = !shapeNode.isOptional();

        // Check if we have any more nodes
        if (!idIterator.hasNext()) {
            return false;
        }

        // Fetch the next node
        currentId = idIterator.next();
        //System.out.println("hasNext " + currentId.getDLN().toString());

        String childName = shapeNode.getTypeId().getName();

        // For each child that is in the "range" of the current node
        // add it to the graph
        while (childrenIterator.hasNext()) {
            ShapelyId shapelyId = childrenIterator.next();
            Id id = shapelyId.getId();
            int typeNum = id.getDLN().getTypeID();
            //System.out.println("typenum " + typeNum + " " + parent.getId().getDLN().getTypeID() + " " + childrenEvalNodesMap.get(typeNum));
            //if (currentId.getDLN().)
            WriterEvalNode n = childrenEvalNodesMap.get(typeNum);
            Node node = db.nodesTable.get(id);
            n.evaluate(node, this.shapeNode);
            try {
                writer.startElement(childName);
            } catch (TransformerException e) {
                System.out.println("Exception thrown, Transformer Exception RenderToSAXWriter");
            }
            //graph.addEdge(parent, node);
        }

        return true;
        //return needSelf;
/*
         * if (nodesCached) { // If we don't have children, is that a problem?
         * if (collectedNodes.isEmpty()) {
         * //System.out.println("EvalNode.evaluate No cached children.");
         * //System.exit(-1); } else { // Check to see if what has been cached
         * is a child for the parent Id firstKey = collectedNodes.firstKey();
         *
         *
         * if (firstKey.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
         * // Iterate through the cached children //System.out.println("EVALNODE
         * adding child " + firstKey.getDLN() + " " + parent.getId().getDLN());
         *
         * for (Node n : collectedNodes.values()) { graph.addEdge(parent, n); }
         * return true; } else { nodesCached = false; collectedNodes = new
         * TreeMap(); } } }
         *
         * // Check to see if this child has a common LCA with the parent
         * //System.out.println("Checking commonLCA " +
         * currentId.getDLN().toString() + " " +
         * parent.getId().getDLN().toString() + " " + level); if
         * (!currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
         * //System.out.println("No commonLCA "); // No common LCA // Check if
         * parent is before child, exit if
         * (parent.getId().getDLN().before(currentId.getDLN(), true)) { // This
         * parent has no children return needSelf; }
         *
         * // Advance child until it is caught up with the parent while
         * (currentId.getDLN().before(parent.getId().getDLN(), true) &&
         * !currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
         * nextId(); // Have we reached the end of our types? if (currentId ==
         * null) { // This parent has no children return needSelf; } } // The
         * child is after the parent, so if it lacks a common ancestor then //
         * parent has no children. if
         * (!currentId.getDLN().hasCommonLCA(parent.getId().getDLN(), level)) {
         * return needSelf; } }
         *
         * // Now we know that we have at least one child for this parent //
         * Let's compute the children for this node collectedNodes = new
         * TreeMap(); nodesCached = true;
         *
         * while (currentId.getDLN().hasCommonLCA(parent.getId().getDLN(),
         * level)) { Node n = db.nodesTable.get(currentId);
         * //System.out.println(parent.getId().toString() + " Cloning " +
         * n.getId().toString() + " " + level); n = n.cloneMe(); // Fill in the
         * name of this node n.setName(shapeNode.getBaseTypeId().getName());
         *
         * // Do I have a where clause? if (shapeNode.hasWhere()) { // Skip this
         * node unless it satisfies the where clause if
         * (!shapeNode.getWhere().evaluate(n)) { currentId = nextId(); if
         * (currentId == null) { break; } continue; } }
         *
         * // Got me past the where clause!
         *
         * // Am I hidden? if (shapeNode.isHidden()) { n.hide(); }
         *
         * // Am I grouped? if (shapeNode.isGrouped()) {
         * //System.out.println("I'm grouped"); n.setGrouped(); }
         *
         * // Should I be an attribute? if (shapeNode.isForceAttribute()) {
         * n.setAttribute(); }
         *
         * // Should I be an element? if (shapeNode.isForceElement()) {
         * n.clearAttribute(); } // Am I dynamically grouped? if
         * (shapeNode.isDynamicallyGrouped()) {
         * //System.out.println("Dynamically grouped");
         *
         * // We have to compute the group value if (dynamicGroupTable == null)
         * { dynamicGroupTable = new HashMap(); }
         *
         * if (dynamicallyGroupedEvalNode == null) { dynamicallyGroupedEvalNode
         * = new ArrayList(); for (ShapeNode s :
         * shapeNode.getDynamicallyGrouped().getChildren()) {
         * //System.out.println("Dynamically g " + s.image() + s.imageXML());
         *
         * EvalNode f = new EvalNode(s, db); f.determineLCALevel(this, db); //
         * We have to set the LCA explicitly dynamicallyGroupedEvalNode.add(f);
         * } }
         *
         * // Compute this graph, or should we just compute one? Graph dynamicG
         * = new Graph(); for (EvalNode v : dynamicallyGroupedEvalNode) {
         * //System.out.println("evaluating " + v.image() + "# " + n.getId() +
         * "# " + n.image() + "# " + v.shapeNode.imageXML()); v.evaluate(n,
         * shapeNode, dynamicG); }
         *
         * String groupValue = dynamicG.imageXMLPrettyPrint(n, "");
         * //System.out.println("|" + groupValue + "|"); if
         * (dynamicGroupTable.containsKey(groupValue)) {
         * //System.out.println("found group value " + groupValue); Node
         * groupedNode = dynamicGroupTable.get(groupValue);
         * //n.setGroupId(groupedNode.getId()); n.setGroupNode(groupedNode);
         * n.setGrouped(); } else { //System.out.println("new group value " +
         * groupValue); dynamicGroupTable.put(groupValue, n);
         * //n.setGroupId(n.getId()); n.setGroupNode(n); // Is set in Node, so
         * redundant n.setGrouped(); }
         *
         * }
         * // Do the children of this node for (EvalNode eval : children) {
         * //System.out.println("Evaluating children " + eval.image() + " " +
         * n.getId().toString()); boolean success = eval.evaluate(n, shapeNode,
         * graph); // If the child fails and I need it, then I'm no good
         *
         * }
         *
         * // If some of my children failed, then maybe skip this one if
         * (!haveSelf) { currentId = nextId(); if (currentId == null) { break; }
         * continue; }
         *
         * Id id = n.getId(); collectedNodes.put(id, n);
         *
         * currentId = nextId(); if (currentId == null) { break; } }
         *
         * // Iterate through the cached children for (Node n :
         * collectedNodes.values()) { //System.out.println("Adding child " +
         * n.imageXML() + " " + parent.imageXML()); //System.out.println("Adding
         * child " + n.image() + " " + parent.image()); //parent.addChild(n);
         * graph.addEdge(parent, n); }
         *
         * return haveSelf;
         */
    }
}
