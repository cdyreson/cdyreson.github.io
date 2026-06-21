package xmorph.edu.usu.shape;

import java.io.Serializable;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.algebra.Comparator;
import java.util.*;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.MutableTreeNode;

/**
 * A ShapeNode class implements a node in a shape. The node could be grouped,
 * cloned, optional, and/or hidden, but in general it represents a TypeId.
 *
 * @author Curtis
 */
public class ShapeNode implements Serializable {

    transient boolean verbose = false;
    boolean hasWhere = false;
    boolean isGrouped = false;
    boolean isCloned = false;
    boolean isHidden = false;
    boolean isOptional = false;
    boolean isDynamicallyGrouped = false;
    boolean forceAttribute = false;
    boolean forceElement = false;
    boolean isNewLabel = false;
    String newLabel = "";
    ShapeNode dynamicallyGroupedPattern;
    TypeId typeId;
    TypeId baseTypeId;
    long shapeTypeId;
    transient static long currentShapeTypeId = 1;
    Comparator wherePredicate;
    TypeId lca;
    List<ShapeNode> children;

    /*
     * Construct a new ShapeNode with no TypeID. This constuctor should only be
     * called for the "root" of a ShapeNode tree.
     */
    public ShapeNode() {
        //super();
        //System.out.println("SHAPENODE ROOT");
        shapeTypeId = 0;
        //userObject = "root";
        lca = new TypeId("");
        children = new ArrayList();
    }

    /*
     * Construct a ShapeNode for a given TypeId.
     *
     * @param typeId - The TypeId for this ShapeNode. @param lca - The TypeId of
     * the lca for this type
     */
    public ShapeNode(TypeId typeId, TypeId lca) {
        //super();
        //System.out.println("SHAPENODE " + typeId.toString() + " lca " + lca.toString());
        //System.out.println("SHAPENODE creating " + typeId.toString() + " " + globalCounter);
        this.typeId = typeId;
        this.baseTypeId = typeId;
        this.lca = lca;
        shapeTypeId = currentShapeTypeId++;
        children = new ArrayList();
        //userObject = typeId.getName();
    }

    /*
     * Construct a ShapeNode for a given TypeId.
     *
     * @param typeId - The TypeId for this ShapeNode. @param lca - The TypeId of
     * the lca for this type
     */
    public ShapeNode cloneMeWithoutChildren() {
        //ShapeNode s = (ShapeNode) this.clone();
        //System.out.println("SHAPENODE cloning " + typeId.toString());
        ShapeNode s = new ShapeNode();
        children = new ArrayList();

        s.verbose = this.verbose;
        s.hasWhere = this.hasWhere;
        s.isGrouped = this.isGrouped;
        s.isCloned = this.isCloned;
        s.isHidden = this.isHidden;
        s.isOptional = this.isOptional;
        s.isDynamicallyGrouped = this.isDynamicallyGrouped;
        s.forceAttribute = this.forceAttribute;
        s.forceElement = this.forceElement;
        s.isNewLabel = this.isNewLabel;
        s.newLabel = this.newLabel;
        s.dynamicallyGroupedPattern = this.dynamicallyGroupedPattern;
        s.typeId = this.typeId;
        s.baseTypeId = this.baseTypeId;
        s.shapeTypeId = this.shapeTypeId;
        s.wherePredicate = this.wherePredicate;
        s.lca = this.lca;
        return s;
    }

    @Override
    /*
     * For hashing purposes, equals tests if each ShapeNode has the same TypeId.
     *
     * @param other - The other ShapeNode to compare. @returns true if the
     * ShapeNodes are equal, false otherwise.
     */
    public boolean equals(Object other) {
        if (other.getClass() == ShapeNode.class) {


            return this.equals(
                    (ShapeNode) other);
        }
        return false;
    }

    /*
     * For hashing purposes, equals tests if each ShapeNode has the same TypeId
     */
    public boolean equals(ShapeNode other) {
        return shapeTypeId == other.shapeTypeId;
    }

    /*
     * For hashing purposes, generate a hash code based on the TypeId
     */
    @Override
    public int hashCode() {
        return (int) shapeTypeId;
    }

    /*
     * Return the TypeId for this ShapeNode
     */
    public TypeId getTypeId() {
        return typeId;
    }

    /*
     * Return the TypeId for this ShapeNode
     */
    public void setBaseTypeId(TypeId t) {
        baseTypeId = t;
    }

    /*
     * Return the base (original) TypeId for this ShapeNode
     */
    public TypeId getBaseTypeId() {
        //if (baseTypeId == null) {
        //    baseTypeId = typeId;
        //}
        return baseTypeId;
    }

    /*
     * Set the LCA TypeId for this ShapeNode
     */
    public void setLCA(TypeId t) {
        lca = t;
    }

    /*
     * Set the that this ShapeNode is a manufactured label
     */
    public void setNewLabel(String s) {
        isNewLabel = true;
        newLabel = s;
    }

    /*
     * Get the LCA TypeId
     */
    public TypeId getLCA() {
        return lca;
    }

    /*
     * Return the TypeId for this ShapeNode
     */
    public long getId() {
        return shapeTypeId;
    }

    /*
     * Does this ShapeNode have a Where clause?
     */
    public boolean hasWhere() {
        return hasWhere;
    }

    /*
     * Is this a forced attribute type?
     */
    public boolean isForceAttribute() {
        //System.out.println("SHAPENODE testing attribute forcing " + " " + forceAttribute + " " + this + " " + this.imageXML());
        return forceAttribute;
    }

    /*
     * Is this a forced element thype?
     */
    public boolean isForceElement() {
        return forceElement;
    }

    /*
     * Is this a manufactured label?
     */
    public boolean isNewLabel() {
        return isNewLabel;
    }

    /*
     * Is this a cloned ShapeNode?
     */
    public boolean isCloned() {
        return isCloned;
    }

    /*
     * Is this ShapeNode dynamically grouped?
     */
    public boolean isDynamicallyGrouped() {
        //System.out.println("Checking is dynmaically grouped " + this + isDynamicallyGrouped);
        return isDynamicallyGrouped;
    }

    /*
     * Is this ShapeNode optional (or required to be present)?
     */
    public boolean isOptional() {
        return isOptional;
    }

    /*
     * Is this ShapeNode hidden in the output?
     */
    public boolean isHidden() {
        return isHidden;
    }

    /*
     * Am I grouped?
     */
    public boolean isGrouped() {
        return isGrouped;
    }

    /*
     * Set up dynamic grouping. The dynamic grouping is a pattern.
     *
     * @param groupNode - The root of the pattern to group by.
     */
    public void setDynamicallyGrouped(ShapeNode groupNode) {
        //System.out.println("go asdfas" + this);
        dynamicallyGroupedPattern = groupNode;
        isDynamicallyGrouped = true;
    }

    /*
     * Get the dynamic grouping pattern.
     *
     * @returns groupNode - The root of the pattern to group by.
     */
    public ShapeNode getDynamicallyGrouped() {
        return dynamicallyGroupedPattern;
    }

    /*
     * Set this to be optional, rather than required.
     */
    public void setOptional() {
        isOptional = true;
    }

    /*
     * I'm grouped.
     */
    public void setGrouped() {
        isGrouped = true;
    }

    /*
     * I'm hidden, so I won't have any output.
     */
    public void setHidden() {
        isHidden = true;
    }

    /*
     * I'm cloned.
     */
    public void setCloned() {
        isCloned = true;
    }

    /*
     * I'm an attribute.
     */
    public void setForceAttribute() {
        //System.out.println("SHAPENODE forcing attribute " + " " + this.counter + " " + this.imageXML() + " " + this);
        forceAttribute = true;
    }
    /*
     * I'm an element.
     */

    public void setForceElement() {
        forceElement = true;
    }

    /*
     * Fetch my Where clause.
     */
    public Comparator getWhere() {
        return wherePredicate;
    }

    /*
     * Set the Where clause.
     */
    public void setWhere(Comparator c) {
        hasWhere = true;
        wherePredicate = c;
    }

    /*
     * Construct a formatted, indented String that is the image of this
     * ShapeNode.
     */
    public String imageXML() {
        return this.imageXML("   ");
    }

    /*
     * Construct a formatted, indented String that is the image of this
     * ShapeNode.
     */
    public String imageXML(String indent) {
        String s = "";
        //System.out.println("iamgeXML");
        if (typeId != null) {
            //System.out.println("ff");
            s = indent + typeId.getName() + "\n";
            //indent = "    ";
        }
        //s = s + indent + this.getBaseTypeId().getName();
        //if (this.getBaseTypeId() != null) {
        //    s = s + indent + this.getBaseTypeId().getName();
        //}
        for (ShapeNode t : children) {
            s = s + t.imageXML("    " + indent);
            //s += indent + "<type> " + t.image() + "\n" + t.imageXML("  " + indent) + indent + "</type>\n";
        }
        return s;
    }

    /*
     * Construct a String representing the information in this ShapeNode, for
     * debugging and informational purposes.
     */
    public String image() {
        String s = "";
        if (typeId != null) {
            s += typeId.toString() + " " + shapeTypeId;
        } else {
            s += " no typeId ";
        }
        if (isCloned) {
            s += ",cloned";
        }
        if (isHidden) {
            s += ",hidden";
        }
        if (isGrouped) {
            s += ",grouped";
        }
        if (hasWhere) {
            s += ",where";
        }
        if (forceAttribute) {
            s += ",forceAttribute";
        }
        if (forceElement) {
            s += ",forceElement";
        }
        return s;
    }

    /*
     * So we can use sane syntax for iteration over children, this method
     * returns the List of the children.
     */
    public List<ShapeNode> getChildren() {
        return children;
    }

    /*
     * So we can use sane syntax for iteration over children, this method
     * returns the List of the children.
     */
    public void replaceChildren(List<ShapeNode> newChildren) {
        children = newChildren;
    }

    /*
     * Add a new child to this ShapeTree.
     *
     * @param child - Child to add.
     */
    public void addChild(ShapeNode child) {
        children.add(child);
    }

    /*
     * Add a new child to this ShapeTree.
     *
     * @param child - Child to add.
     */
    public void add(ShapeNode child) {
        this.addChild(child);
    }
}
