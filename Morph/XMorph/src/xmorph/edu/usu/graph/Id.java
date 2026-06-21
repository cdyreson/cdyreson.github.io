package xmorph.edu.usu.graph;

import java.io.Serializable;
import xmorph.org.exist.numbering.DLN;
//import org.exist.numbering.DLNBase;
import xmorph.org.exist.numbering.NodeId;

/**
 * An Id is a class wrapping (at present) a DLN value which represents
 * a node id.
 * @author Curtis
 */
public class Id implements Serializable, Comparable {

    DLN nodeId;

    // Construct a new Id
    public Id() {
        nodeId = new DLN();
    }

    // Construct a new Id
    public Id(NodeId n) {
        nodeId = (DLN) n;
    }

        // Construct a new Id
    public Id(long n) {
        nodeId = new DLN((int)n);
    }
    
    @Override
    public int hashCode() {
        //System.out.println("hashCode is " + nodeId.hashCode());
        return nodeId.hashCode();
    }

    @Override
    public int compareTo(Object obj) {
        if (obj.getClass() != Id.class) {
            return -1;
        }
        Id i = (Id) obj;
        return nodeId.compareTo(i.nodeId);
    }

    public boolean equals(Id other) {
        //DLNBase d = nodeId;
        //return d.equals((DLNBase)other.nodeId);
        //System.out.println("Other class is testing!");
        return nodeId.equals((NodeId) other.nodeId);
    }

    @Override
    public boolean equals(Object other) {
        return equals((Id) other);
    }

    public DLN getDLN() {
        return nodeId;
    }

    public boolean before(Id other, boolean prec) {
        return nodeId.before(other.nodeId, prec);
    }

    public boolean after(Id other, boolean fol) {
        return nodeId.after(other.nodeId, fol);
    }

    public boolean isDescendantOrSelfOf(Id other) {
        return nodeId.isDescendantOrSelfOf(other.nodeId);
    }
    //@Override
    public Id newChild() {
        NodeId newNodeId = nodeId.newChild();
        return new Id(newNodeId);
    }

    //@Override
    public Id nextSibling() {
        NodeId newNodeId = nodeId.nextSibling();
        return new Id(newNodeId);
    }

    @Override
    public String toString() {
        return nodeId.toString();
    }
}
