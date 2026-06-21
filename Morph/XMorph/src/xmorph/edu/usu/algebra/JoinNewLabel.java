package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import java.util.*;

/* A JoinNewLabel operator is generated when the new label
 * is the child in an LCAJoin operator
 */
public class JoinNewLabel extends Operator {

    public Operator parent;

    public JoinNewLabel(Operator left, NewLabel right) {
        //System.out.println("JoinNewLabel constructor");
        parent = left;
        child = right;

    }

    @Override
    public HashSet<TypeId> typeOf(Database db) {
        types = parent.typeOf(db);
        return types;
    }
}
