package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.shape.ShapeNode;
import java.util.*;

/* The TypeFill class models a type fill operator.
 * @author Curtis
 */
public class TypeFill extends Operator {
    
    public TypeFill (Operator c) {
        child = c;
    }

}

