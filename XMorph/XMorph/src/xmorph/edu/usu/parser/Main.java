package xmorph.edu.usu.parser;

/**
 * Test package to parse an XML document
 * @author Curtis
 */
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;
import xmorph.edu.usu.database.memory.Database;
//import database.berkeleyDB.Database;

public class Main {

    public static void main(String argv[]) {
        // Use the default (non-validating) parser
        try {
            String fileName = argv[1];
            if (XMLParser.verbose) {
                System.out.println("Shredding " + fileName);
            }
            Database db = new Database();

            XMLReader xr = XMLReaderFactory.createXMLReader();
            XMLParser xp = new XMLParser(db);
            xp.setFileName(fileName);
            xr.setContentHandler(xp);
            xr.setErrorHandler(xp);
            FileReader r = new FileReader(fileName);

            xr.parse(new InputSource(r));
            db.closeDatabase();

        } catch (Exception t) {
            t.printStackTrace();
        }
        System.exit(0);
    }
}
