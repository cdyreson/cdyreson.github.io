/*
 * Models the text string event
 */
package xmorph.edu.usu.sax.events;

/**
 * Models the characters event in a stream of SAX events
 *
 * @author Curtis Dyreson
 */
public class Characters extends Event {

    public Characters(char[] ch, int start, int length) {
    }
    
    public Characters(String s) {      
    }
    
}
