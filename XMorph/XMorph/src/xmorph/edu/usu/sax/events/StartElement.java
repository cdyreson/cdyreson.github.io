/*
 * Represents an element node
 */
package xmorph.edu.usu.sax.events;

//import xmorph.edu.usu.sax.events.Event;
//import org.xml.sax.Attributes;
import org.xml.sax.helpers.*;

/**
 * Models the start element event in a 
 * @author Curtis Dyreson
 */
public class StartElement extends Event {

    public String uri;
    public String localName;
    public String qName;
    public AttributesImpl attr;

    /*
     * Construct a new Element
     * @param uri
     * @param localName
     * @param qName
     */
    public StartElement(String u, String l, String q) {
        uri = u;
        localName = l;
        qName = q;
        attr = new AttributesImpl();
    }
    
    public void addAttribute(String uri, String localName, String qName, String typeOf, String value) {
        attr.addAttribute(uri, localName, qName, typeOf, value);
    }
}
