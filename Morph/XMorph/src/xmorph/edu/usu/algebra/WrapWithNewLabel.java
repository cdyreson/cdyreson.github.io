package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import java.util.*;

public class WrapWithNewLabel extends Operator {

    public Operator parent;

    /* A WrapWithNewLabel operator is generated when the new label
     * is the parent in an LCAJoin operator
     */
    public WrapWithNewLabel(NewLabel left, Operator right) {
        //System.out.println("WraphWithNewLabel constructor");
        parent = left;
        child = right;

    }

    @Override
    public HashSet<TypeId> typeOf(Database db) {
        types = child.typeOf(db);
        return child.typeOf(db);
    }
}
