/*
 * Represents an end element event in a XML stream of events
 */
package xmorph.edu.usu.sax.events;

/**
 * Represents an end element event in an XML stream of events
 * @author Curtis Dyreson
 */
public class EndElement extends Event {
    
    /* 
     * Construct an end element node 
     */
    public String uri;
    public String localName;
    public String qName;
    
    public EndElement(String u, String l, String q) {
        uri = u;
        localName = l;
        qName = q;
    }
}
