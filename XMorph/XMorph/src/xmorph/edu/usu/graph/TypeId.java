package xmorph.edu.usu.graph;

import xmorph.org.exist.numbering.DLN;
import xmorph.org.exist.numbering.NodeId;
import java.io.Serializable;

/**
 * The TypeId class establishes an id for each type. The id can be used to
 * determine relationships between types. Currently the id is a dewey level
 * number (DLN).
 *
 * @author Curtis and Aswani
 */
public class TypeId implements Serializable, Comparable {

    String name;
    DLN nodeId;

    /*
     * Create a new DLN for this named type. It will always allocate a root DLN.
     * If you want to use a specific DLN, you must call some other constructor.
     *
     * @param name - a Sring that is the name of the type.
     */
    public TypeId(String name) {
        nodeId = new DLN();
        this.name = name;
    }

    /*
     * Pair the name of this type with this DLN.
     *
     * @param name - a Sring that is the name of the type. @param n - a NodeId,
     * the DLN for this TypeId.
     */
    public TypeId(String name, NodeId n) {
        nodeId = (DLN) n;
        this.name = name;
    }

    /*
     * Pair the name of this type with this DLN.
     *
     * @param name - a Sring that is the name of the type. @param n - a value
     * representing the DLN for this type.
     */
    public TypeId(String name, long n) {
        nodeId = new DLN((int) n);
        this.name = name;
    }

    @Override
    /*
     * We want to be able to hash TypeIds. Since every TypeId has a unique DLN,
     * use the node id for the hash code.
     */
    public int hashCode() {
        //System.out.println("hashCode is " + nodeId.hashCode());    
        return nodeId.hashCode();
    }

    @Override
    /*
     * We want to be able to hash TypeIds.
     */
    public int compareTo(Object obj) {
        if (obj.getClass() != TypeId.class) {
            return -1;
        }
        TypeId i = (TypeId) obj;
        return nodeId.compareTo(i.nodeId);
    }

    /*
     * Figure out the parent (a manufactured typeID) of self.
     *
     * @returns A TypeId, which is a "fake parent" in the sense that it has a
     * DLN, but not a good name
     */
    public TypeId getParent() {
        return new TypeId("", nodeId.getParentId());
    }

    /*
     * Compute the distance to the LCA of this TypeId with some other TypeId. 
     * @param other - The TypeId of the other for which this LCA is to be computed
     *
     * @returns The distance as an integer.
     */
    public int distanceToLCA(TypeId other) {
        int[] myInts = nodeId.getLevelIds();
        //System.out.println("my TypeId distanceToLCA " + nodeId.toString() + " " + other.getDLN().toString());
        int[] otherInts = other.getDLN().getLevelIds();
        int i = 0;
        for (; i < myInts.length && i < otherInts.length; i++) {
            if (myInts[i] != otherInts[i]) {
                break;
            }
        }
        //System.out.println(" i is " + i);
        // Check to see if these two types are in the same file
        if (i == 0) {
            return Integer.MAX_VALUE;
        }
        return ((otherInts.length - i) + (myInts.length - i));
    }

    /*
     * Figure out the least common ancestor (lca) TypeID of two types. If the two
     * types are not in the same shape tree, at the root they start out 
     * differently.  This will return a TypeID without a meaningful name
     * (Which could be fixed if needed).
     *
     * @param other - TypeId to compare with 
     * @returns A TypeId
     */
    public TypeId lcaTypeId(TypeId other) {
        return new TypeId("", lca(other));
    }

    /*
     * Figure out the least common ancestor (lca) of two types. If the two
     * types are not in the same shape tree, at the root they start out 
     * differently.
     *
     * @param other - TypeId to compare with @returns A NodeId, but shouldn't
     * this be a TypeId?
     */
    public NodeId lca(TypeId other) {
        int[] myInts = nodeId.getLevelIds();
        //System.out.println("my nodeID TypeId " + nodeId);
        //System.out.println("my other TypeId " + other);
        int[] otherInts = other.getDLN().getLevelIds();
        /*
        for (int i = 0; i < myInts.length; i++) {
        System.out.println("MyInts " + i + " " + myInts[i]);
        }
        for (int i = 0; i < otherInts.length; i++) {
        System.out.println("OtInts " + i + " " + otherInts[i]);
        }
         */
//        System.out.println("In lca " + myInts.length + " otherLength " + otherInts.length);
        DLN lcaId = null;
        for (int i = 0; i < myInts.length && i < otherInts.length; i++) {
//            System.out.println("In loop " + i + " " + myInts[i] + " " + otherInts[i]);
            if (lcaId == null) {
                lcaId = new DLN(myInts[i]);
            } else {
                if (myInts[i] != otherInts[i]) {
                    break;
                } else {
                    lcaId.addLevelId(myInts[i], false);
                }
            }
        }
        //System.out.println("TypeId " + lcaId.toString());
        return lcaId;
    }

    /*
     * Figure out the XPath expression from this type to the other type.
     *
     * @param other - TypeId to generate a path to @returns A String, which is
     * the XPath expression
     */
    public String generateXPathTo(TypeId other) {
        int[] myInts = nodeId.getLevelIds();
        int[] otherInts = other.getDLN().getLevelIds();
        int thisLength = myInts.length;
        int otherLength = myInts.length;
        //System.out.println("Path " + myInts.length + " otherLength " + otherInts.length);
        //System.out.println("Path " + this.name + " to " + other.name);

        String path = "";
        int i;
        for (i = 1; i < myInts.length && i < otherInts.length; i++) {
            //System.out.println("In loop " + i + " " + myInts[i] + " " + otherInts[i]);
            if (myInts[i] != otherInts[i]) {
                break;
            } else {
                //path += "../";
            }
        }
        for (int j = thisLength - i; j > 0; j--) {
            path += "../";
        }

        for (int k = otherLength - i; k > 0; k--) {
            path += "*/";
        }
        path += other.name;
        //System.out.println("Path " + path);
        return path;
    }

    /*
     * Compare this TypeId with some other.
     *
     * @param other - TypeId to compare with @returns Are they the equivalent
     * TypeIds?
     */
    public boolean equals(TypeId other) {
        //DLNBase d = nodeId;
        //return d.equals((DLNBase)other.nodeId);
        //Boolean value = nodeId.equals((org.exist.numbering.DLNBase)other.getDLN());
        //Boolean value = nodeId.equals((NodeId)other.getDLN());
        //System.out.println("Other class is testing!" + nodeId + " " + other.getDLN() + " " + value);
        return nodeId.equals((xmorph.org.exist.numbering.DLNBase) other.getDLN());
    }

    @Override
    /*
     * Compare this TypeId with some other.
     *
     * @param other - Object to compare with @returns Are they the equivalent
     * TypeIds? Any other kind of object will return false.
     */
    public boolean equals(Object other) {
        return equals((TypeId) other);
    }

    /*
     * Fetch the DLN.
     *
     * @returns DLN for this TypeId
     */
    public DLN getDLN() {
        return nodeId;
    }

    /*
     * Allocate a new TypeId that is a child of this TypeId.
     *
     * @param name - name of this type @returns Allocated TypeID.
     */
    public TypeId newChild(String name) {
        NodeId newNodeId = nodeId.newChild();
        return new TypeId(name, newNodeId);
    }

    /*
     * Allocate a new TypeId that is a sibling of this TypeId.
     *
     * @param name - name of this type @returns Allocated TypeID.
     */
    public TypeId nextSibling(String name) {
        NodeId newNodeId = nodeId.nextSibling();
        return new TypeId(name, newNodeId);
    }

    @Override
    /*
     * Convert this type to a String for error reporting purposes (mostly). The
     * String will be a representation of its DLN.
     *
     * @returns String representation of the TypeId.
     */
    public String toString() {
        return nodeId.toString();
    }

    /*
     * Change the name associated with this TypeId.
     *
     * @param s - new name of TypeId
     */
    public void setName(String s) {
        name = s;
    }

    /*
     * Fetch the name of this TypeId.
     *
     * @returns name.
     */
    public String getName() {
        return name;
    }
    /*
     * These are not used? public boolean before(Id other, boolean prec) {
     * return nodeId.before(other.nodeId, prec); }
     *
     * public boolean after(Id other, boolean fol) { return
     * nodeId.after(other.nodeId, fol); }
     *
     * public boolean isDescendantOrSelfOf(Id other) { return
     * nodeId.isDescendantOrSelfOf(other.nodeId); }
     */
}
