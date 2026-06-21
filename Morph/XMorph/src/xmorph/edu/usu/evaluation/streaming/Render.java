package xmorph.edu.usu.evaluation.streaming;

//import xmorph.edu.usu.evaluation.normal.*;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.DocumentRoot;
import xmorph.edu.usu.graph.Node;

/**
 *
 * @author Kiran and Curtis
 */
public class Render {

    Database db;
    ShapeNode shape;
    Node root;
    Graph graph;

    public Render(Database db, ShapeNode shape) {
        this.db = db;
        this.shape = shape;
        root = new DocumentRoot();
        graph = new Graph();
    }

    public Graph render() {
        //root = new DocumentRoot();
        //Start from root at this level
        for (ShapeNode c : shape.getChildren()) {
            //System.out.println("RENDER has next " + c.imageXML());
            EvalNode current = new EvalNode(c, db);
            do {
                boolean success = current.evaluate(root, new ShapeNode(), graph);
                /*
                if (!c.isOptional() && !success) {
                    System.out.println("lkasdf");
                    return new Graph();
                }*/
            } while (current.hasNext());
        }
        return graph;
    }
}

