package xmorph.edu.usu.applet;

/**
 * Simple test of Morph queries.
 * @author Curtis
 */
import xmorph.edu.usu.grammar.XMorphLexer;
import xmorph.edu.usu.grammar.XMorphParser;
import xmorph.edu.usu.grammar.XMorphParseException;
//import xmorph.edu.usu.grammar.*;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.evaluation.shapelyidmerge.Graph;
import xmorph.edu.usu.evaluation.shapelyidmerge.Render;
import xmorph.edu.usu.database.memory.Database;
import xmorph.edu.usu.parser.XMLParser;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.*;

import org.antlr.runtime.*;

public class Morph {

    static Database db = new Database();
    public ShapeNode queryShape;
    public ShapeNode dataShape;

    public Morph() {
        queryShape = new ShapeNode();
        dataShape = new ShapeNode();
    }

    public void parse(String s) throws SAXParseException, Exception {

        // Use the default (non-validating) parser
        try {
            String fileName = "foo.xml";
            db = new Database();

            XMLReader xr = XMLReaderFactory.createXMLReader();
            XMLParser xp = new XMLParser(db);
            xp.setFileName(fileName);
            xr.setContentHandler(xp);
            xr.setErrorHandler(xp);
            StringReader r = new StringReader(s);

            xr.parse(new InputSource(r));
            dataShape = db.shapesTable.get("/" + fileName);

        } catch (SAXParseException t) {
            throw t;
        } catch (Exception t) {
            throw t;
        }

    }

    public void parseFromFile(String fileName) throws SAXParseException, Exception {

        // Use the default (non-validating) parser
        try {
            //String fileName = "foo.xml";
            db = new Database();

            XMLReader xr = XMLReaderFactory.createXMLReader();
            XMLParser xp = new XMLParser(db);
            xp.setFileName(fileName);
            xr.setContentHandler(xp);
            xr.setErrorHandler(xp);
            FileReader r = new FileReader(fileName);
            xr.parse(new InputSource(r));
            dataShape = db.shapesTable.get(fileName);

        } catch (SAXParseException t) {
            throw t;
        } catch (Exception t) {
            throw t;
        }

    }

    public String execute(
            String query) throws XMorphParseException, Exception {
        String output = "";
        String fileName = "foo.xml";
        // Use the default (non-validating) parser
        XMorphLexer lex = new XMorphLexer(new ANTLRStringStream(query));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        XMorphParser parser = new XMorphParser(tokens);
        try {
            System.out.println("Opened " + fileName);
            xmorph.edu.usu.algebra.Query q = parser.program(); // launch parsing
            System.out.println("Query is " + query);
            dataShape = db.shapesTable.get("/" + fileName);
            queryShape = q.evaluate(db, dataShape);
            System.out.println(queryShape.imageXML());
            Render rend = new Render(db, queryShape);
            Graph graph = rend.render();
            output = graph.imageXMLPrettyPrint();
            db.closeDatabase();

        // Set up output stream
        //out = new OutputStreamWriter (System.out, "UTF8");
        } catch (XMorphParseException e) {
            throw e;
        } catch (RecognitionException e) {
            throw new XMorphParseException(parser.getErrorMessage());
        } catch (Exception t) {
            throw t;
        }

        return output;

    }
}

    

