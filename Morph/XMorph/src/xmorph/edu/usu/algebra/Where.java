package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.reporting.ErrorInformation;
import java.util.HashSet;

// author Kiran
public class Where extends Type {

    public Comparator comparator;
    public int op;
    public final static int EQ = 0,  LT = 1,  LE = 2,  GT = 3,  GE = 4,  NE = 5;

    public Where(Operator child, Operator comparator) {
        this.child = child;
        if (comparator.getClass() != Comparator.class) {
            System.err.println("Bad comparator in Where");
            ErrorInformation.appendLn("Bad comparator in Where");
            //System.exit(-1);
        }
        this.comparator = (Comparator) comparator;
    }

    public Where(Operator child, Comparator comparator) {
        this.child = child;
        this.comparator = comparator;
    }

    @Override
    public HashSet<TypeId> typeOf(Database db) {
        return child.typeOf(db);
    }

    @Override
    /* Group groups the roots of the Shape forest.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        // The Type should be a leaf, so no child should be present
        ShapeNode shape = child.evaluate(db, dataShape);
        for (ShapeNode c : shape.getChildren()) {
            c.setWhere(comparator);
        }
        return shape;
    }
}