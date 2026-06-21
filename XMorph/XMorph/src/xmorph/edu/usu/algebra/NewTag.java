package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.Database;
import java.util.*;

/* A NewTag operator generates a stream
 * of new labels with the given string.
 */
public class NewTag extends Operator {

    // Name of the label
    String s;

    /* Construct a NewLabel operator
     * The operator will generate a sequence of
     * Elements with the name specified as the
     * parameter in a constructor.
     */
    public NewTag(String s, Operator child) {
        types = new HashSet();
        this.child = child;
        this.s = s;
    }

    /* The type of this label is empty.
     */
    public HashSet<TypeId> typeOf() {
        return types;
    }

    /* Evaluate the operator generating a Shape. 
     * The Shape will be merged with other Shapes.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        if (child != null) {
            return child.evaluate(db, dataShape);
        }
        //System.out.println("Creating new shape in Operator");

        ShapeNode n = new ShapeNode();
        n.setNewLabel(s);
        return n;
    }
}