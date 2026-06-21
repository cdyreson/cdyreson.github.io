package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.TypeId;
import java.util.*;

/* A NewLabel operator generates a stream
 * of new labels with the given string.
 */
public class NewLabel extends Operator {

    // Name of the label
    String s;

    /* Construct a NewLabel operator
     * The operator will generate a sequence of
     * Elements with the name specified as the
     * parameter in a constructor.
     */
    public NewLabel(String s) {
        types = new HashSet();
        this.s = s;
    }

    /* The type of this label is empty.
     */
    public HashSet<TypeId> typeOf() {
        return types;
    }
}

