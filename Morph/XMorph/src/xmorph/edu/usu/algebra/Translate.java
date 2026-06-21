package xmorph.edu.usu.algebra;

import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;

/**
 * The Translate class performs a translation of names in the shape to 
 * new names in the output
 * @author Kiran and Curtis
 */
public class Translate extends Operator {

    Dictionary dictionary;
    boolean before = false;
    boolean after = false;

    public Translate(Dictionary d) {
        dictionary = d;
        child = null;
    }

    /* Apply a translate before a Shape is created,
     * in the algebra.
     */
    public Translate(Translate t, Operator e) {
        dictionary = t.dictionary;
        child = e;
        before = true;
    }

    /* Apply a Translate after the Shape is created
     */
    public Translate(Operator e, Translate t) {
        dictionary = t.dictionary;
        child = e;
        after = true;
    }

    @Override
    public HashSet<TypeId> typeOf(Database db) {
        if (before) {
            // Walk the algebra, changing the Type name if necessary
            //System.out.println("child ");
            child.translate(dictionary);
        }
        return child.typeOf(db);
    }

    @Override
    public ShapeNode evaluate(Database db, ShapeNode shape) {
        // First evaluate the child
        ShapeNode newShape = child.evaluate(db, shape);
        if (after) {
            // Now walk the child, replacing each name found in the dictionary
            walkShape(newShape);
        }
        return newShape;
    }

    private void walkShape(ShapeNode shapeNode) {
        TypeId baseTypeId = shapeNode.getBaseTypeId();
        if (baseTypeId != null) {
            String name = baseTypeId.getName();
            if (dictionary.containsKey(name)) {
                baseTypeId.setName(dictionary.get(name));
            }
        }
        for (ShapeNode c : shapeNode.getChildren()) {
            walkShape(c);
        }
    }
}