/*
 * QueueEvents is a queue for SAX events, in building a SAX writer.
 */
package xmorph.edu.usu.sax;

import xmorph.edu.usu.sax.events.*;
import java.util.*;
//import org.exist.util.serializer.XMLWriter;
//import org.xml.sax.Attributes;

/**
 * QueueEvents is a queue for SAX events. Each time an "edge" is added in the
 * graph, we should construct add to a queue of SAX events.
 *
 * @author Curtis Dyreson
 */
public class EventsQueue {

    Stack<List<Event>> eventsStack;
    List<Event> currentEventsList;
    XMLWriter writer;
    Stack<StartElement> elementStack;
    StartElement currentElement;

    //List<Event> events;
    public EventsQueue(XMLWriter w) {
        eventsStack = new Stack();
        elementStack = new Stack();
        currentEventsList = null;
        currentElement = null;
        writer = w;
        //eventsStack.push(currentEventsList);
    }

    /*
     * Add an event to the event stream
     */
    public void add(Event node) {
        currentEventsList.add(node);
    }

    /*
     * Add an event to the event stream
     */
    public void addStartElementEvent(StartElement node) {
        eventsStack.push(currentEventsList);
        currentEventsList = new ArrayList();
        currentEventsList.add(node);
    }

    public void addAttribute(String uri, String localName, String qName, String typeOf, String value) {
        currentElement.addAttribute(uri, localName, qName, typeOf, value);
    }

    /*
     * Add an event to the event stream
     */
    public void addEndElementEvent(EndElement node) {
        for (Event e : currentEventsList) {
            generate(e);
        }
        currentEventsList = eventsStack.pop();
    }

    public void generate(Event e) {
    }

    public void generate(StartElement e) throws Exception {
        writer.startElement(e.qName);
    }

    public void generate(EndElement e) throws Exception {
        writer.endElement(e.qName);
    }

    public void generate(StartDocument e) throws Exception {
        writer.startDocument();
    }

    public void generate(EndDocument e) throws Exception {
        writer.endDocument();
    }
}
