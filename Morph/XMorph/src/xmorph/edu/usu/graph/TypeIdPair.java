package xmorph.edu.usu.graph;

import xmorph.org.exist.numbering.DLN;
import xmorph.org.exist.numbering.NodeId;
import java.io.Serializable;

/**
 * The TypeId class establishes an id for each type. The id can be
 * used to determine relationships between types. Currently the id is
 * a dewey level number (DLN).
 * @author Curtis and Aswani
 */
//public class TypeIdPair implements Serializable, Comparable {
public class TypeIdPair  {

    String nameFrom;
    String nameTo;
    DLN nodeIdFrom;
    DLN nodeIdTo;

    /* Create a new DLN for this named type. It will always allocate a 
     * root DLN. If you want to use a specific DLN, you must call some
     * other constructor.
     * 
     * @param name - a Sring that is the name of the type.
     */
    public TypeIdPair(String nameFrom, String nameTo) {
        nodeIdFrom = new DLN();
        this.nameFrom = nameFrom;
        nodeIdTo = new DLN();
        this.nameTo = nameFrom;
    }

    /* Pair the names of these types with these DLNs.
     * 
     * @param nameFrom - a Sring that is the name of the type.
     * @param fromId - a NodeId, the DLN for this TypeId.
     *      * @param nameTo - a Sring that is the name of the type.
     * @param toId - a NodeId, the DLN for this TypeId.
     */
    public TypeIdPair(String nameFrom, NodeId fromId, String nameTo, NodeId toId) {
        nodeIdFrom = (DLN) fromId;
        this.nameFrom = nameFrom;
        nodeIdTo = new DLN();
        this.nameTo = nameFrom;
    }


//    @Override
//    /* We want to be able to hash TypeIds. Since every TypeId
//     * has a unique DLN, use the node id for the hash code.
//     */
//    public int hashCode() {
//        //System.out.println("hashCode is " + nodeId.hashCode());
//        return fromId.hashCode() + toId.hashCode();
//    }
//
//    @Override
//    /* We want to be able to hash TypeIds.
//     */
//    public int compareTo(Object obj) {
//
//        if (obj.getClass() != Id.class) {
//            return -1;
//        }
//        Id i = (Id) obj;
//        return nodeId.compareTo(i.nodeId);
//    }
//
//    /* Figure out the least common ancestor (lca) of two types.
//     *
//     * @param other - TypeId to compare with
//     * @returns A NodeId, but shouldn't this be a TypeId?
//     */
//    public NodeId lca(TypeIdPair other) {
//        int[] myInts = nodeId.getLevelIds();
//        int[] otherInts = other.getDLN().getLevelIds();
//        //System.out.println("In lca " + myInts.length + " otherLength " + otherInts.length);
//        DLN lcaId = new DLN();
//        for (int i = 1; i < myInts.length && i < otherInts.length; i++) {
//            //System.out.println("In loop " + i + " " + myInts[i] + " " + otherInts[i]);
//            if (myInts[i] != otherInts[i]) {
//                break;
//            } else {
//                lcaId.addLevelId(myInts[i], false);
//            }
//        }
//        return lcaId;
//    }
//
//    /* Compare this TypeId with some other.
//     *
//     * @param other - TypeId to compare with
//     * @returns Are they the equivalent TypeIds?
//     */
//    public boolean equals(TypeIdPair other) {
//        //DLNBase d = nodeId;
//        //return d.equals((DLNBase)other.nodeId);
//        //System.out.println("Other class is testing!");
//        return nodeId.equals((NodeId) other.nodeId);
//    }
//
//    @Override
//    /* Compare this TypeId with some other.
//     *
//     * @param other - Object to compare with
//     * @returns Are they the equivalent TypeIds? Any other kind of
//     * object will return false.
//     */
//    public boolean equals(Object other) {
//        return equals((TypeIdPair) other);
//    }
//
//    /* Fetch the DLN.
//     *
//     * @returns DLN for this TypeId
//     */
//    public DLN getDLN() {
//        return nodeId;
//    }
//
//    /* Allocate a new TypeId that is a child of this TypeId.
//     *
//     * @param name - name of this type
//     * @returns Allocated TypeID.
//     */
//    public TypeIdPair newChild(String name) {
//        NodeId newNodeId = nodeId.newChild();
//        return new TypeIdPair(name, newNodeId);
//    }
//
//    /* Allocate a new TypeId that is a sibling of this TypeId.
//     *
//     * @param name - name of this type
//     * @returns Allocated TypeID.
//     */
//    public TypeIdPair nextSibling(String name) {
//        NodeId newNodeId = nodeId.nextSibling();
//        return new TypeIdPair(name, newNodeId);
//    }
//
//    @Override
//    /* Convert this type to a String for error reporting purposes (mostly).
//     * The String will be a representation of its DLN.
//     *
//     * @returns String representation of the TypeId.
//     */
//    public String toString() {
//        return nodeId.toString();
//    }
//
//    /* Change the name associated with this TypeId.
//     *
//     * @param s - new name of TypeId
//     */
//    public void setName(String s) {
//        name = s;
//    }
//
//    /* Fetch the name of this TypeId.
//     *
//     * @returns name.
//     */
//    public String getName() {
//        return name;
//    }
//    /* These are not used?
//    public boolean before(Id other, boolean prec) {
//    return nodeId.before(other.nodeId, prec);
//    }
//
//    public boolean after(Id other, boolean fol) {
//    return nodeId.after(other.nodeId, fol);
//    }
//
//    public boolean isDescendantOrSelfOf(Id other) {
//    return nodeId.isDescendantOrSelfOf(other.nodeId);
//    }
//     */
}
