package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;

/* The AsElement class models a force element operator.
 * Currently does not throw a semantic error if the shape is not a leaf!
 * @author Curtis
 */
public class AsElement extends Operator {

    public AsElement(Operator c) {
        child = c;
    }

    /* Evaluate the operator generating a shape, turn on the element of that
     * shape.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        if (child != null) {
            ShapeNode c = child.evaluate(db, dataShape);
            for (ShapeNode s : c.getChildren()) {
                s.setForceElement();
            }
            return c;
        }
        //System.out.println("Creating new shape in Operator");
        return new ShapeNode();
    }
}
