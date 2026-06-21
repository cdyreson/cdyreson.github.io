package xmorph.edu.usu.evaluation.shapelyidmerge;

import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.DocumentRoot;
import xmorph.edu.usu.graph.Node;
//import xmorph.edu.usu.graph.TypeId;
//import xmorph.org.exist.numbering.DLN;

/**
 * The Render class renders an XML data instance using the shape into
 * a Graph.
 * 
 * @author Curtis
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
//        shape.setBaseTypeId(new TypeId(" ", new DLN(0)));
        
        EvalNode rootEvalNode = new EvalNode(shape, db, true);
        //Node currentNode = rootEvalNode.kickStartRoot();
        rootEvalNode.evaluate(root, graph);
        /*
        for (ShapeNode c : shape.getChildren()) {
            System.out.println("RENDER has next " + c.imageXML());
            EvalNode current = new EvalNode(c, db);
            //Node currentNode = current.kickStartRoot();
            //while (current.hasNext()) {
            //System.out.println("RENDER looping ");
            //    boolean success = current.evaluate(currentNode, graph);
            current.evaluate(root, graph);
            //}
        }
       
        */
        return graph;
    }
}
