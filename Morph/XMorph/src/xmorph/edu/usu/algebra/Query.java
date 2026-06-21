package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
//import database.shape.ShapeNode;
import java.util.*;
import xmorph.edu.usu.database.Database;

/*
 * Query is a top-level query object, there should only be one query in 
 * the entire translation of a Morph query
 */
public class Query extends Operator {

    public Query(Operator e) {
        child = e;
    }

    /* RestrictType pushes a set of types down the tree, 
     * restricting the types available at each node. 
    */
    @Override
    public void restrictType(HashSet<TypeId> t) {
        types = t;
        child.restrictType(t);
    }

    @Override
    /*
     * Evaluate builds a Shape. 
     */
    public HashSet<TypeId> typeOf(Database db) {
        types = child.typeOf(db);
        /*
        List<TypeId> childTypes = child.typeOf(db);
        Iterator<TypeId> childTypesIterator = childTypes.iterator();
        //System.out.println("Starting to evaluate");
        while (childTypesIterator.hasNext()) {
        TypeId t = childTypesIterator.next();
        //System.out.println(x + "");
        }
         */
        return types;
    }

    @Override
    /*
     * Evaluate builds a Shape.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // First do a type analysis if necessary
        if (types == null) {
            //System.out.println("In query.java, doing type analysis.");
            types = this.typeOf(db);
            //System.out.println("In query.java, restricting types.");
            this.restrictType(types);
        }

        // Now create the shape
        return child.evaluate(db, dataShape);
    }
}

