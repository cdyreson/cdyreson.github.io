package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;

/* The Operator class models an operator in the algebra.
 * Each operator constructs a shape when evaluated.
 * @author Curtis
 */
abstract public class Operator {

    Operator child = null;
    Operator right = null;
    HashSet<TypeId> types;

    /* For a binary operator, return the left operand.
     * For a unary operator, return the only operand.
     */
    public Operator left() {
        return child;
    }

    /* For a binary operator, return the right operand.
     */
    public Operator right() {
        return null;
    }

    /* By default, the list of types is the types of the
     * child
     */
    public HashSet<TypeId> typeOf(Database db) {
        // Initialize the types instance variable
        types = child.typeOf(db);
        return types;
    }

    /* The types of the operator can be restricted in the
     * second phase of type analysis. This limits the types
     * for the operator to the passed types.
     */
    public void restrictType(HashSet<TypeId> t) {
        if (child != null) child.restrictType(t);
        types = t;
    }
    
    /* Evaluate the operator generating a Shape. 
     * The Shape will be merged with other Shapes.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        if (child != null) return child.evaluate(db, dataShape);
        //System.out.println("Creating new shape in Operator");
        return new ShapeNode();
    }

    public void translate(Dictionary d) {
        child.translate(d);
    }
}

