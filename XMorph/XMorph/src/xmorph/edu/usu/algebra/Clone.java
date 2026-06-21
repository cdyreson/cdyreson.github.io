package xmorph.edu.usu.algebra;

import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.graph.TypeId;
import java.util.*;

public class Clone extends Type {

    public Clone(Operator c) {
        child = c;
    }

    @Override
    /* Clone clones the roots of the Shape forest.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // The Type should be a leaf, so no child should be present
        ShapeNode shapeNode = child.evaluate(db, dataShape);
        for (ShapeNode c: shapeNode.getChildren()) {
            c.setCloned();
        }
        return shapeNode;
    }

    @Override
    /* By default, the list of types is the types of the
     * child
     */
    public HashSet<TypeId> typeOf(Database db) {
        // Initialize the types instance variable
        types = child.typeOf(db);
        return types;
    }
}

