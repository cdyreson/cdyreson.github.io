package xmorph.edu.usu.main;

/**
 * Simple test of Morph queries. runs main
 * @author Curtis
 */
import xmorph.edu.usu.api.XMC;
//import xmorph.edu.usu.grammar.*;
//import xmorph.edu.usu.algebra.*;
//import xmorph.edu.usu.shape.ShapeNode;
//import xmorph.edu.usu.reporting.*;
//import xmorph.edu.usu.evaluation.normal.Graph;
import xmorph.edu.usu.database.Database;
//import xmorph.edu.usu.evaluation.normal.Render;
//import xmorph.edu.usu.parser.XMLParser;
//import org.xml.sax.*;
//import org.xml.sax.helpers.XMLReaderFactory;
//import java.io.*;
//import java.util.*;
import xmorph.edu.usu.globals.Globals;

//import org.antlr.runtime.*;

public class XMorph {
    static boolean verbose = false;
    
    public static void main(String args[]) throws Exception {
        ParseCmdLine.parse(args);
        XMC xmc;
        if (Database.inMemoryDB) {
            xmc = new XMC();
            if (verbose) System.out.println("Setting xmc");
            Globals.setXMC(xmc);
        } else {
            xmc = new XMC(ParseCmdLine.dbDirectory);
            Globals.setXMC(xmc);
        }

        if (ParseCmdLine.parseFlag) {
            String fileName = ParseCmdLine.inputXMLFileName;
            if (verbose) System.out.println("Shredding " + fileName);
            xmc.parseXMLFile(fileName);
        }

        // Evaluate a query
        if (verbose) System.out.println("Running xmc ");
        System.out.println(xmc.run(ParseCmdLine.xmorphQueryFileName));
        //xmc.run(ParseCmdLine.xmorphQueryFileName);
        System.out.println(xmc.typeInformation());
        System.out.println(xmc.errorInformation());
        xmc.close();
    }
}
