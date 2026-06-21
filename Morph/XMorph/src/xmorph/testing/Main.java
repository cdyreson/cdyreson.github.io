/*
 * Run the tests.
 */
package xmorph.testing;

import xmorph.edu.usu.api.XMC;
import java.io.*;
import java.util.Scanner;
import org.custommonkey.xmlunit.*;

/**
 *
 * @author Curtis Dyreson
 */
public class Main {

    public static void main(String args[]) throws Exception {
        try {
            // Set up JaxP
            XMLUnit.setControlParser("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            XMLUnit.setTestParser("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
            XMLUnit.setSAXParserFactory("org.apache.xerces.jaxp.SAXParserFactoryImpl");
            XMLUnit.setIgnoreAttributeOrder(true);
            XMLUnit.setIgnoreWhitespace(true);
            //XMLUnit.setTransformerFactory("org.apache.xalan.processor.TransformerFactoryImpl");


            String testCasesDir = "src/testing/testCases/";
            File file = new File(testCasesDir);
            //System.out.println(file.getAbsolutePath());
            String[] fileNames = file.list();
            for (String s : fileNames) {
                MyXMLTestCase myTester = new MyXMLTestCase(s);
                // Iterate through the test cases
                System.out.println("Shredding " + s);
                XMC xmc = new XMC();
                xmc.parseXMLFile(testCasesDir + s + "/input.xml");

                // Read expected output
                StringBuilder text = new StringBuilder();
                String NL = System.getProperty("line.separator");
                Scanner scanner = new Scanner(new FileInputStream(testCasesDir + s + "/expected.xml"));
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine() + NL);
                }
                //System.out.println("java is " + text);
                scanner.close();

                // Evaluate a query
                String result = "<result>" + xmc.run(testCasesDir + s + "/query.mrf") + "</result>";
                myTester.testForEquality(result, text.toString());
                System.out.println(result);
                System.out.println(xmc.typeInformation());
                System.out.println(xmc.errorInformation());

                xmc.close();
            }
        } catch (Exception e) {
            System.out.println("Open error ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
