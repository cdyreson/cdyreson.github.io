package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Path;
import xmorph.edu.usu.shape.ShapeNode;
//import database.shape.ShapeNode;
import java.util.*;
import xmorph.edu.usu.database.Database;

/*
 * Pipe represents the pipe operator to connect different Morph functions.
 * A Pipe always has two children, but one may be null.
 */
public class Pipe extends Operator {

    Path path = new Path();

    /* Build a Pipe, the Pipe pipes the shape produced by the 
     * left to the right Operator.
     * @param left - Operator to execute first
     * @param right - Operator to execute next
     */
    public Pipe(Operator left, Operator right) {
        child = left;
        this.right = right;
    }

    /* Build a Pipe where the left side is a translate
     * the Pipe pipes the shape produced by the
     * right Operator.
     * @param left - Operator to execute first
     * @param right - Operator to execute next
     */
    public Pipe(Translate left, Operator right) {
        child = new Translate(left, right);
        right = null;
    }

    /* Build a Pipe, the Pipe pipes the shape produced by the
     * left to the right Operator.
     * @param left - Operator to execute first
     * @param right - Operator to execute next
     */
    public Pipe(Operator left, Translate right) {
        child = new Translate(left, right);
        right = null;
    }

    /* Build a Pipe, the Pipe pipes the shape produced by the
     * left to the right Operator.
     * @param left - Operator to execute first
     * @param right - Operator to execute next
     */
    public Pipe(Translate left, Translate right) {
        child = left;
        this.right = right;
    }

    @Override
    /* Restrict the Pipe type
     */
    public void restrictType(HashSet<TypeId> t) {
        types = t;
    }

    @Override
    /*
     * Evaluate builds a Shape. 
     */
    public HashSet<TypeId> typeOf(Database db) {
        types = child.typeOf(db);
        return types;
    }

    @Override
    /*
     * Evaluate builds a Shape.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // First do a type analysis if necessary
        if (types == null) {
            types = this.typeOf(db);
            this.restrictType(types);
        }

        // Create the shape, from the left hand child
        ShapeNode shape = child.evaluate(db, dataShape);

        // If only one child, then a special kind of pipe
        if (right == null) {
            return shape;
        }

        // Parse the shape, creating new tables for the type information
        Database newDb = new xmorph.edu.usu.database.memory.Database();
        // Move the data tables from the existing shape
        newDb.nodesTable = db.nodesTable;
        newDb.typedNodeTableCollection = db.typedNodeTableCollection;
        newDb.textTable = db.textTable;
        newDb.documentsTable = db.documentsTable;
        newDb.metadataTable = db.metadataTable;
        parseShape(shape, newDb);

        // Evaluate the right child in the context of the new Shape
        types = right.typeOf(newDb);
        right.restrictType(types);

        return right.evaluate(newDb, shape);
    }

    /* 
     * Parse the shape, building new tables for the relevant stuff in
     * the shape, basically all the type information
     */
    private void parseShape(ShapeNode current, Database db) {
        TypeId baseTypeId = current.getBaseTypeId();
        //System.out.println("parseing shape " + baseTypeId);
        if (baseTypeId != null) {
            String name = baseTypeId.getName();
            path.extend(name);
            db.typesTable.put(baseTypeId, path);
            db.pathsTable.put(path, baseTypeId);
            String pathName = name;
            int i = path.size();
            do {
                //System.out.println("Putting type " + pathName + " " + baseTypeId.toString());
                db.abbreviatedTypesTable.put(pathName, baseTypeId);
                if (--i > 0) {
                    pathName = path.peekAt(i - 1) + "." + pathName;
                }
            } while (i > 0);
        }
        // Do the children
        for (ShapeNode c : current.getChildren()) {
            parseShape(c, db);
            if (baseTypeId == null) {
                db.lcaTable.put(c.getBaseTypeId(), c.getLCA());
            } else {
                // Compute the LCA as the lca of the parent and the child
                TypeId lca = new TypeId("",c.getLCA().lca(baseTypeId));
                db.lcaTable.put(c.getBaseTypeId(), lca);
                c.setLCA(lca);
                //System.out.println("lca is " + lca.toString() +
                //  " current " + baseTypeId.toString() + " " + current.getLCA().toString() +
                //  " child "+ c.getBaseTypeId().toString() + " " + c.getLCA().toString());
            }
        }

        if (baseTypeId != null) {
            path.remove();
        }
    }
}

