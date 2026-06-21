package xmorph.edu.usu.grammar;

/**
 * Simple test of Morph queries.
 * @author Curtis and Aswani
 */
//import xmorph.edu.usu.reporting.LossInformation;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.reporting.*;
import xmorph.edu.usu.evaluation.normal.Graph;
import xmorph.edu.usu.database.memory.Database;
//import database.berkeleyDB.Database;
import xmorph.edu.usu.evaluation.normal.Render;
import xmorph.edu.usu.parser.XMLParser;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.*;

import org.antlr.runtime.*;

public class Test {

    public static void main(String args[]) throws Exception {
        Database db = new Database();

        // Use the default (non-validating) parser
        try {
            String fileName = args[2];
            System.out.println("Shredding " + fileName);

            XMLReader xr = XMLReaderFactory.createXMLReader();
            XMLParser xp = new XMLParser(db);
            xp.setFileName(fileName);
            xr.setContentHandler(xp);
            xr.setErrorHandler(xp);
            FileReader r = new FileReader(fileName);
            xr.parse(new InputSource(r));

            if (args[3] != null) {
                fileName = args[3];
                System.out.println("Shredding " + fileName);

                XMLReader xr2 = XMLReaderFactory.createXMLReader();
                XMLParser xp2 = new XMLParser(db);
                xp2.setFileName(fileName);
                xr2.setContentHandler(xp2);
                xr2.setErrorHandler(xp2);
                FileReader r2 = new FileReader(fileName);
                xr2.parse(new InputSource(r2));
            }


        } catch (Exception t) {
            t.printStackTrace();
        }

        XMorphLexer lex = new XMorphLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        XMorphParser parser = new XMorphParser(tokens);

        try {
            xmorph.edu.usu.algebra.Query query = parser.program(); // launch parsing
            //System.out.println("Query is " + query);
            for (String fname : db.shapesTable.keySet()) {
                System.out.println("Querying " + fname);
                //System.out.println("Shape of " + fname + " is " + db.shapesTable.get(fname).imageXML());
                ShapeNode shape = query.evaluate(db, db.shapesTable.get(fname));
                System.out.println("Shape of query is " + shape.imageXML());
                Render rend = new Render(db, shape);
                Graph graph = rend.render();
                System.out.println(graph.imageXMLPrettyPrint());
                System.out.println(LossInformation.report());
            }
        } catch (XMorphParseException t) {
            System.out.println(t.msg);
            //t.printStackTrace();
            System.exit(-1);
        } catch (RecognitionException t) {
            System.out.println(parser.getErrorMessage());
            //t.printStackTrace();
            System.exit(-1);
        } catch (Exception t) {
            t.printStackTrace();
            System.exit(-1);
        }

        db.closeDatabase();
    }
}
    

