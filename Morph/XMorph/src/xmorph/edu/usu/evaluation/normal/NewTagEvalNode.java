package xmorph.edu.usu.evaluation.normal;

import xmorph.edu.usu.shape.ShapeNode;
//import xmorph.edu.usu.shape.*;
//import xmorph.edu.usu.graph.TypeId;
//import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.Node;
//import xmorph.edu.usu.algebra.Comparator;
//import java.util.*;
import xmorph.edu.usu.database.Database;

/**
 * A NewTagEvalNode represents a node that generates a new ..
 *
 * @author Curtis
 */
public class NewTagEvalNode extends EvalNode {

    //Graph dynamicallyGroupedGraph;
    public NewTagEvalNode(ShapeNode shapeNode, Database db) {
        super(shapeNode, db);
    }

    public boolean evaluate(Node parent, ShapeNode parentShapeNode, Graph graph) {
        // For this parent, let's determine if we already have the appropriate children
        //System.out.println("EVALNODE " + currentId + " Evaluating " + parent.getId().toString());
        //System.out.println("EVAL " + parent);
       

            // Do the children of this node
            for (EvalNode eval : children) {
                //System.out.println("Evaluating children " + eval.image() + " " + n.getId().toString());
                //boolean success = eval.evaluate(n, shapeNode, graph);
                // If the child fails and I need it, then I'm done
            }
         return true;
    }
}
