/*
 * A ShapelyId is an Id plus a ShapeNode.  The ShapeNode is needed
 * because evaluation for grouping, attributes, etc. need the shape
 * information.
 */
package xmorph.edu.usu.evaluation.shapelyidmerge;
//import java.util.*;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.shape.ShapeNode;

/**
 * A ShapeNode plus an Id
 *
 * @author Curt
 */
public class ShapelyId implements Comparable {

    ShapeNode shapeNode;
    Id id;

    public ShapelyId(Id id, ShapeNode shapeNode) {
        this.id = id;
        this.shapeNode = shapeNode;
    }

    public Id getId() {
        return id;
    }

    public ShapeNode getShape() {
        return shapeNode;
    }

    @Override
    public int compareTo(Object obj) {
        if (obj.getClass() != ShapelyId.class) {
            return -1;
        }
        ShapelyId i = (ShapelyId) obj;
        return id.compareTo(i.getId());
    }
}
