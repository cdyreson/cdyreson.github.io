package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;

/* The Descendants class models a descendants operator.
 * @author Curtis
 */
public class Descendants extends Operator {

    public Descendants(Operator c) {
        child = c;
    }

    public Descendants() {
        child = null;
    }

    private ShapeNode walkAndFind(ShapeNode shape, TypeId t) {
        //System.out.println("Des.java evaluate " + t + " " + shape.imageXML());
        TypeId baseTypeId = shape.getBaseTypeId();
        if (baseTypeId != null && baseTypeId.equals(t)) {
            return shape;
        }
        for (ShapeNode c : shape.getChildren()) {
            ShapeNode v = walkAndFind(c, t);
            if (v != null) {
                if (v.getBaseTypeId() != null) {
                    return v;
                }
            }
        }
        return null;
    }

    @Override
    /*
     * Evaluate builds a Shape. In this case, the shape is taken from
     * the current shape of the data.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // Evaluate the child's Shape
        ShapeNode childShape = child.evaluate(db, dataShape);

        // Add to the child's shape all of the nodes from the data's shape
        for (ShapeNode c : childShape.getChildren()) {
            //System.out.println("FOOO " + c.imageXML() + "asdf" + c.getBaseTypeId() + "asdf");
            ShapeNode foundNode = walkAndFind(dataShape, c.getBaseTypeId());
            c.getChildren().addAll(foundNode.getChildren());
        }
        return childShape;
    }
}
