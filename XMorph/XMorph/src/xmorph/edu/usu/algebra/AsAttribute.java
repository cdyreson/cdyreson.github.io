package xmorph.edu.usu.algebra;

//import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
//import java.util.*;

/* The AsAtribute class models a force attribute operator.
 * Currently does not throw a semantic error if the shape is not a leaf!
 * But it prints a message that this is happening (and ignores the asAttribute).
 * 
 * @author Curtis
 */
public class AsAttribute extends Operator {

    public AsAttribute(Operator c) {
        child = c;
    }

    /**
     * Evaluate the operator generating a shape, turn on the attribute of that
     * shape.
     *
     * @param db - A Database object that is the database we are using
     * @param dataShape - A ShapeNode that represents the data's shape
     * @return
     */
    @Override
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        if (child != null) {
            ShapeNode c = child.evaluate(db, dataShape);
            if (c.getChildren().size() > 1) {
                /* This should not be possible to get */
                System.out.println("WARNING: more than one child when forcing an attribute, will only select first child");
            }
            for (ShapeNode s : c.getChildren()) {
                //System.out.println("ASATTRIBUTE algebra Forcing attribute " + s);
                s.setForceAttribute();
            }
            return c;
        }
        System.out.println("Creating new shape in AsAttribute Operator since function has no child");
        return new ShapeNode();
    }
}
