package xmorph.edu.usu.graph;

import java.io.Serializable;

/**
 *
 * @author aswani
 */
public class ClosestNode implements Serializable {

    private Id toId;
    private TypeId fromTypeId;
    private TypeId toTypeId;
    private long distance;

    public ClosestNode(Id tid, TypeId ftype, TypeId ttype) {
        this.toId = tid;
        this.fromTypeId = ftype;
        this.toTypeId = ttype;
    }

    public TypeId getFromTypeId() {
        return this.fromTypeId;
    }

    public TypeId getToTypeId() {
        return this.toTypeId;
    }

    public long getDistance() {
        return this.distance;
    }

    public void setDistance(long dt) {
        this.distance = dt;
    }
}
