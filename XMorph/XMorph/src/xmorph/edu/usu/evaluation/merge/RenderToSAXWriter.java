package xmorph.edu.usu.evaluation.merge;

import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.Database;
import xmorph.edu.usu.graph.DocumentRoot;
import xmorph.edu.usu.graph.Node;
import xmorph.xml.XMLWriter;
import javax.xml.transform.TransformerException;

/**
 * The Render class renders an XML data instance using the shape into a Graph.
 *
 * @author Curtis
 */
public class RenderToSAXWriter {

    Database db;
    ShapeNode shape;
    Node root;
    Graph graph;
    XMLWriter writer;

    public RenderToSAXWriter(Database db, ShapeNode shape, XMLWriter writer) {
        this.db = db;
        this.shape = shape;
        root = new DocumentRoot();
        graph = new Graph();
        this.writer = writer;
    }

    public void write() {
        //root = new DocumentRoot();
        //Start from root at this level
        try {
            writer.startDocument();
            for (ShapeNode c : shape.getChildren()) {
                //System.out.println("RENDER has next " + c.imageXML());
                WriterEvalNode current = new WriterEvalNode(c, db, writer);
                while (current.hasNext()) {
                    current.evaluate();
                }
            }
        } catch (TransformerException e) {
            System.out.println("Exception thrown, Transformer Exception RenderToSAXWriter");
        }

    }
}
