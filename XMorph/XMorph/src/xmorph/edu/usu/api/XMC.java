/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmorph.edu.usu.api;

//import xmorph.edu.usu.grammar.XMorphLexer;
//import xmorph.edu.usu.grammar.XMorphParser;
//import xmorph.edu.usu.reporting.ErrorInformation;
//import xmorph.edu.usu.reporting.LossInformation;
//import xmorph.edu.usu.grammar.XMorphParseException;
//import xmorph.edu.usu.reporting.TypeInformation;
//import xmorph.edu.usu.algebra.Query;
//import xmorph.edu.usu.evaluation.virtual.VDLNMap;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import xmorph.edu.usu.grammar.*;
import xmorph.edu.usu.algebra.*;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.reporting.*;
//import xmorph.evaluation.normal.Graph;
//import xmorph.evaluation.normal.Render;
import xmorph.edu.usu.evaluation.shapelyidmerge.Graph;
import xmorph.edu.usu.evaluation.shapelyidmerge.Render;
//import xmorph.edu.usu.evaluation.merge.Graph;
//import xmorph.edu.usu.evaluation.merge.Render;
//import xmorph.evaluation.merge.*;
import xmorph.edu.usu.database.Database;

import xmorph.edu.usu.parser.XMLParser;
//import xmorph.edu.usu.evaluation.virtual.*;
//import xmorph.edu.usu.evaluation.virtual.VDLNMap;
import xmorph.edu.usu.globals.Globals;
import java.io.*;
import java.util.*;
import org.antlr.runtime.*;

/**
 * XMC is the XMorph API. It sets the 
 * @author Curtis Dyreson
 */
public class XMC {

    private Database db = null;
    private boolean verbose = false;

    /**
     * Open a new "inMemory" connection to XMorph 
     */
    public XMC() {
        reset();
        //System.out.println("new XMC");
        if (db == null) {
            db = new xmorph.edu.usu.database.memory.Database();
        }
    }

    /**
     * Open a new persistent connnection to XMorph
     * String s - Name of the persistent db to use
     */
    public XMC(String s) {
        reset();
        //ystem.out.println("new XMC plus " + s);
        if (db == null) {
            xmorph.edu.usu.database.berkeleyDB.Database.setDbHome(s);
            db = new xmorph.edu.usu.database.berkeleyDB.Database();
        }
    }

    public Database getDb() {
        return db;
    }

    /** 
     * Load an XML file and parse it.  Passed the name of the file.
     * Exceptions are caught on opening the file and parse errors.
     * Exceptions will print the stack.
     */
    public void parseXMLFile(String fileName) {
        try {
            //System.out.println("Shredding " + fileName);
            XMLReader xr = XMLReaderFactory.createXMLReader();
            XMLParser xp = new XMLParser(db);
            xp.setFileName(fileName);
            xr.setContentHandler(xp);
            xr.setErrorHandler(xp);
            FileReader r = new FileReader(fileName);
            xr.parse(new InputSource(r));
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    /** 
     * Open, parse, and evaluate the XMorph program.
     */
    public String run(String fileName) {
        String s = renderAsXML(evaluate(compile(fileName)));
        //System.out.println("length is " + s.length());
        return s;
        //return renderAsXML(evaluate(compile(fileName)));
    }

    /**
     * Compile an XMorph program passed as a String.
     * Returns a Query object.
     */
    public Query compileString(String s) {
        XMorphLexer lex = null;

        try {
            if (verbose) System.out.println("Running query on " + s);
            lex = new XMorphLexer(new ANTLRStringStream(s));
        } catch (Exception t) {
            System.out.println("Error building the XMorph lexer: for the string " + s);
            t.printStackTrace();
            //System.exit(-1);
        }
        CommonTokenStream tokens = new CommonTokenStream(lex);
        XMorphParser parser = new XMorphParser(tokens);
        try {
            Query q = parser.program();
            //System.out.println("in here " + q);
            return q;
        } catch (XMorphParseException t) {
            System.out.println("XMC.java: Error " + t.msg);
            //t.printStackTrace();
            //System.exit(-1);
        } catch (RecognitionException t) {
            System.out.println("XMC.java: Parse Error " + t.getMessage() + " " + parser.getErrorMessage());
            //t.printStackTrace();
            //System.exit(-1);
        } catch (Exception t) {
            System.out.println("XMC.java: other error ");
            t.printStackTrace();
            //System.exit(-1);
        }
        return null;
    }

    /**
     * Compile an XMorph program stored in a file. The name
     * of the file is passed as a String.
     */
    public Query compile(String fileName) {
        XMorphLexer lex = null;

        try {
            if (verbose) System.out.println("Running query on " + fileName);
            lex = new XMorphLexer(new ANTLRFileStream(fileName));
        } catch (Exception t) {
            System.out.println("Error building the XMorph lexer: probably could not open file " + fileName);
            t.printStackTrace();
            //System.exit(-1);
        }
        CommonTokenStream tokens = new CommonTokenStream(lex);
        XMorphParser parser = new XMorphParser(tokens);
        try {
            Query q = parser.program();
            //System.out.println("in here " + q);
            return q;
        } catch (XMorphParseException t) {
            System.out.println("XMC.java: Error " + t.msg);
            //t.printStackTrace();
            //System.exit(-1);
        } catch (RecognitionException t) {
            System.out.println("XMC.java: Parse Error " + parser.getErrorMessage());
            //t.printStackTrace();
            //System.exit(-1);
        } catch (Exception t) {
            System.out.println("XMC.java: other error ");
            t.printStackTrace();
            //System.exit(-1);
        }
        return null;
    }

    /**
     * Evaluate the XMorph program, producing a list of ShapeNodes
     * which represents the shape(s) to evaluate.
     */
    public List<ShapeNode> evaluate(Query query) {
        List<ShapeNode> shapes = new ArrayList();
        try {
            //System.out.println("Shape table is " + db.shapesTable.keySet().size());
            //System.out.println("Query is " + query);
            // XMorph tries on all the shapes in the Shapes Table
            if (Globals.dataShapes.isEmpty()) {
                for (String fname : db.shapesTable.keySet()) {
                    //System.out.println("Querying " + fname);
                    if (verbose) System.out.println("Shape of " + fname + " is \n" + db.shapesTable.get(fname).imageXML());
                    ShapeNode s = query.evaluate(db, db.shapesTable.get(fname));
                    if (verbose) System.out.println("Shape of query is \n" + s.imageXML());
                    shapes.add(s);
                }
            } // Exist tries on all the shapes in the dataShapes list
            else {
                for (ShapeNode d : Globals.dataShapes) {
                    //System.out.println("Querying " + fname);
                    if (verbose) System.out.println("Shape of data is \n" + d.imageXML());
                    ShapeNode s = query.evaluate(db, d);
                    if (verbose) System.out.println("Shape of query is \n" + s.imageXML());
                    shapes.add(s);

                }
            }
        } catch (Exception t) {
            System.out.println("XMC.java: Run-time error in running an XMorph program " + t.getMessage());
            t.printStackTrace();
            System.exit(-1);
        }
        return shapes;
    }

    /**
     * Get a String of information about the XMorph evaluation
     * matched types between the query shape and the data shape.
     */
    public String typeInformation() {
        return TypeInformation.report();
    }

    /*
     * Get a String of information about Information loss in the
     * evaluation of an XMorph query. 
     */
    public String errorInformation() {
        return LossInformation.report();
    }


    /** 
     * Render to XML.  Need to rewrite this to act as an XML Stream Reader.
     */
    public String renderAsXML(List<ShapeNode> shapes) {
        //System.out.println("render as xml");
        String result = "";
        for (ShapeNode shape : shapes) {
            result += renderAsXML(shape);
        }
        return result;
    }

    /**
     * Render to XML.
     */
    public String renderAsXML(ShapeNode shape) {
        try {
            Render rend = new Render(db, shape);
            Graph graph = rend.render();
            return graph.imageXMLPrettyPrint();
        } catch (Exception t) {
            System.out.println("XMC.java: Run-time error in running an XMorph program. " + t.getMessage());
            t.printStackTrace();
            System.exit(-1);
        }
        return "";
    }

    /**
     * An XMorph query evaluation produces a "state" consisting of
     * captured Type information, Error information, Loss information,
     * and other Global values (see Globals).  Also closes any open
     * databases.
     */
    public void reset() {
        TypeInformation.reset();
        LossInformation.reset();
        ErrorInformation.reset();
        Globals.reset();
        close();
    }

    /**
     * Close up any open databases.
     */
    public void close() {
        if (db != null) {
            db.closeDatabase();
            db = null;
        }
    }
}
