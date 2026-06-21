/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmorph.testing;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import org.custommonkey.xmlunit.*;

/**
 *
 * @author Curtis Dyreson
 */
public class MyXMLTestCase extends XMLTestCase {

    public MyXMLTestCase(String name) {
        super(name);
    }

    public void testForEquality(String myControlXML, String myTestXML) throws Exception {
        assertXMLEqual("comparing test xml to control xml", myControlXML, myTestXML);
        //assertXMLNotEqual("test xml not similar to control xml", myControlXML, myTestXML);
    }
}
