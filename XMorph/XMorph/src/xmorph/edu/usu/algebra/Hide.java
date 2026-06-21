package xmorph.edu.usu.algebra;

import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/* The Hide class implements a hide operation. It will
 * add to the shape the fact that a particular node is hidden.
 */
public class Hide extends Type {

    public Hide(Operator c) {
        child = c;
    }

    @Override
    /* Hide hides the roots of the Shape forest.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // The Type should be a leaf, so no child should be present
        ShapeNode shapeNode = child.evaluate(db, dataShape);
        for (ShapeNode c : shapeNode.getChildren()) {
            c.setHidden();
        }
        return shapeNode;
    }

    @Override
    /* The list of types is the types of the
     * child
     */
    public HashSet<TypeId> typeOf(Database db) {
        // Initialize the types instance variable
        types = child.typeOf(db);
        return types;
    }
}

