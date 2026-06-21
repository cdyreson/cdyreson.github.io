package xmorph.edu.usu.algebra;

import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.graph.TypeId;
import java.util.*;

public class Drop extends Type {

    public Drop(Operator c) {
        child = c;
    }

    @Override
    /* Drop drops the roots of the Shape forest.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // Evaluate the child's Shape
        //System.out.println("Evaluating drop " + child);
        ShapeNode childsShape = child.evaluate(db, dataShape);

        boolean found = false;
        // Look for the type to drop
        for (ShapeNode c : childsShape.getChildren()) {
            //System.out.println("DROPPING " + c.getBaseTypeId());
            found = walkAndReplace(dataShape, c.getBaseTypeId()) || found;
        }
        // Did we find it?
        if (!found) {
            // Warn the user that it wasn't found
            System.err.println("Warning, desired part of shape not dropped " + dataShape.imageXML());

        }

        return childsShape;
    }

    @Override
    /* By default, the list of types is the types of the
     * child, need to remove from these types!
     */
    public HashSet<TypeId> typeOf(Database db) {
        // Initialize the types instance variable
        types = child.typeOf(db);
        return types;
    }

    private boolean walkAndReplace(ShapeNode shape, TypeId t) {

        // We can only drop if it is a child
        boolean found = false;
        List<ShapeNode> children = new ArrayList();
        for (ShapeNode c : shape.getChildren()) {
            TypeId baseTypeId = c.getBaseTypeId();

            if (baseTypeId != null && baseTypeId.equals(t)) {
                // found it!  let's drop it
                found = true;
                //System.out.println("found it");
                //System.out.println("Drop.java evaluate " + t + " " + shape.imageXML());
                // Skip grandchildren!
                //for (ShapeNode gc : c.getChildren()) {
                //    children.addChild(gc);
                //}
            } else {
                found = walkAndReplace(c, t) || found;
                children.add(c);
            }
        }
        if (found) {
            // replace children
            shape.replaceChildren(children);
        }
        return found;
    }
}
