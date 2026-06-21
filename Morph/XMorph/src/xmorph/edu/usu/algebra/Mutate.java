package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;
import xmorph.edu.usu.database.Database;

/*
 * Mutate modifies the Shape of the data.
 */
public class Mutate extends Operator {

    public Mutate(Operator e) {
        child = e;
    }

    @Override
    public void restrictType(HashSet<TypeId> t) {
        types = t;
    }

    @Override
    /*
     * Evaluate builds a Shape. 
     */
    public HashSet<TypeId> typeOf(Database db) {
        //System.out.println("Mutate.java typeOf");
        types = child.typeOf(db);
        return types;
    }

    private List<ShapeNode> walk(ShapeNode current,
            Map<TypeId, ShapeNode> rootTable,
            Map<TypeId, ShapeNode> childShapeTable,
            Map<TypeId, ShapeNode> dataShapeTable,
            Map<TypeId, ShapeNode> movedChildShapeTable) {
        //System.out.println("walking " + current.getBaseTypeId().getName());

        List<ShapeNode> v = new ArrayList();

        // Make decisions about me
        // Is this a root in the ChildShape?
        if (rootTable.containsKey(current.getBaseTypeId())) {
            //System.out.println("contains root ");
            // Shape should already be formed
            v.add(childShapeTable.get(current.getBaseTypeId()));
        } else if (childShapeTable.containsKey(current.getBaseTypeId())
                || movedChildShapeTable.containsKey(current.getBaseTypeId())) {
            //System.out.println("in child ");
            // Then omit it from the constructed shape
            for (ShapeNode c : current.getChildren()) {
                List<ShapeNode> newShapeList = walk(c, rootTable, childShapeTable, dataShapeTable, movedChildShapeTable);
                for (ShapeNode x : newShapeList) {
                    v.add(x);
                }
            }
        } else {
            //System.out.println("in data ");
            ShapeNode result = new ShapeNode(current.getBaseTypeId(), current.getBaseTypeId());
            v.add(result);
            // Add to the constructed shape and continue
            // Construct the new child
            for (ShapeNode c : current.getChildren()) {
                List<ShapeNode> newShapeList = walk(c, rootTable, childShapeTable, dataShapeTable, movedChildShapeTable);
                for (ShapeNode x : newShapeList) {
                    result.addChild(x);
                }
            }
        }

        return v;
    }

    private ShapeNode descendantShape(ShapeNode current, Map<TypeId, ShapeNode> childShapeTable, Map<TypeId, ShapeNode> movedChildShapeTable) {
        //System.out.println("descendant Shape " + current.getBaseTypeId().getName());
        if (childShapeTable.containsKey(current.getBaseTypeId())) {

            return null;
        }

        // build children
        ShapeNode newShape = new ShapeNode(current.getBaseTypeId(), current.getBaseTypeId());
        if (!movedChildShapeTable.containsKey(current.getBaseTypeId())) {
            movedChildShapeTable.put(current.getBaseTypeId(), current);
        }
        for (ShapeNode c : current.getChildren()) {
            ShapeNode s = descendantShape(c, childShapeTable, movedChildShapeTable);
            if (s != null) {
                newShape.addChild(s);
            }
        }
        //System.out.println("new ahape " + newShape.getBaseTypeId().getName());
        return newShape;
    }

    private void fillShapeTable(ShapeNode current, Map<TypeId, ShapeNode> typeIdToShapeNode) {
        //System.out.println("filling " + current.getBaseTypeId().getName());
        typeIdToShapeNode.put(current.getBaseTypeId(), current);
        for (ShapeNode childShapeNode : current.getChildren()) {
            // Is this child part of the child shape?
            fillShapeTable(childShapeNode, typeIdToShapeNode);
        }

    }

    @Override
    /*
     * Evaluate builds a Shape.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // Evaluate the child's Shape
        ShapeNode childShape = child.evaluate(db, dataShape);

        // Fill the table for this Shape (the data's shape)
        Map<TypeId, ShapeNode> dataShapeTable = new HashMap();
        for (ShapeNode c : dataShape.getChildren()) {
            fillShapeTable(c, dataShapeTable);
        }

        // Fill the table for the Child's shape (the mutate pattern)
        Map<TypeId, ShapeNode> childShapeTable = new HashMap();
        //System.out.println("Mutate.java evaluate " + childShape.imageXML());
        for (ShapeNode c : childShape.getChildren()) {
            //fillChildShapeTable(c, childShapeTable, dataShapeTable);
            fillShapeTable(c, childShapeTable);
        }

        // Walk the Child's shape, moving non-naked junk over
        Map<TypeId, ShapeNode> movedChildShapeTable = new HashMap();
        for (TypeId t : childShapeTable.keySet()) {
            //System.out.println("Doing descendant " + t.getName());
            ShapeNode cs = childShapeTable.get(t);
            ShapeNode ds = dataShapeTable.get(t);
            if (ds == null) {
                //System.err.println("ERROR: Skipping missing type in MUTATE");
                continue;
            }
            //System.out.println("fdddo " + ds.imageXML());
            for (ShapeNode c : ds.getChildren()) {
                //System.out.println("xxxx");
                ShapeNode r = descendantShape(c, childShapeTable, movedChildShapeTable);
                //System.out.println("ffo");
                if (r != null) {
                    //System.out.println("Adding child " + t.getName() + r.imageXML());
                    if (!movedChildShapeTable.containsKey(r.getBaseTypeId())) {
                        movedChildShapeTable.put(r.getBaseTypeId(), r);
                    }
                    cs.addChild(r);
                }
            }
            //System.out.println("bbbbdo");

        }
        //System.out.println("xxxxxxo");
        // Move the roots to the root table
        Map<TypeId, ShapeNode> rootTable = new HashMap();
        for (ShapeNode c : childShape.getChildren()) {
            //System.out.println("Root is " + c.getBaseTypeId().getName());
            rootTable.put(c.getBaseTypeId(), c);
            //rootTable.put(childShape.getBaseTypeId(), childShape);
        }
        // Construct the mutated shape for the data
        ShapeNode mutatedShape = new ShapeNode();
        for (ShapeNode c : dataShape.getChildren()) {
            List<ShapeNode> v = walk(c, rootTable, childShapeTable, dataShapeTable, movedChildShapeTable);
            for (ShapeNode aChild : v) {
                //System.out.println("adding " + aChild.imageXML());
                mutatedShape.add(aChild);
            }
        }

        //System.out.println("Mutate.java one " + mutatedShape.image());
        // Add the mutated child's shape back into the data's shape
        /*
         for (ShapeNode c : mutatedShape.roots()) {
         for (ShapeNode root : childShape.roots()) {
         reconstructChild(c, root);
         }
        
         }
         * */

        //return parents;
        //System.out.println("Mutate.java mutated shape is " + mutatedShape.image());
        return mutatedShape;
    }
}
