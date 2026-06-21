/*
 * The XMLSerializer is used to render a Shape into an XML string.
 * It implements ContentHandler to support SAX based parsing of the
 * resulting string.
 */
package xmorph.serializers;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import xmorph.xml.XMLWriter;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import xmorph.org.exist.dom.QName;
import xmorph.org.exist.util.XMLString;

/**
 *
 * @author Curt
 */
public class XMLSerializer implements ContentHandler {

//public class SAXSerializer implements ContentHandler, LexicalHandler, Receiver {
    private final static Properties defaultProperties = new Properties();

    static {
        defaultProperties.setProperty(OutputKeys.ENCODING, "UTF-8");
        defaultProperties.setProperty(OutputKeys.INDENT, "false");
    }
    private XMLWriter writer = new XMLWriter();
    protected XMLWriter receiver;
    protected Properties outputProperties = defaultProperties;
//	protected NamespaceSupport nsSupport = new NamespaceSupport();
    protected HashMap namespaceDecls = new HashMap();
    protected HashMap optionalNamespaceDecls = new HashMap();

    public XMLSerializer() {
        super();
        receiver = writer;
    }

    public XMLSerializer(Writer writer, Properties outputProperties) {
        super();
        setOutput(writer, outputProperties);
    }

    public void setOutput(Writer writer, Properties properties) {
        if (properties == null) {
            outputProperties = defaultProperties;
        } else {
            outputProperties = properties;
        }
        //String method = outputProperties.getProperty("method", "xml");

        receiver.setWriter(writer);
        receiver.setOutputProperties(outputProperties);
    }

    /*
    public Writer getWriter() {
    return (Writer)writer;
    }
     */
    public void setReceiver(XMLWriter receiver) {
        this.receiver = receiver;
    }

    /*
    public void reset() {
    nsSupport.reset();
    namespaceDecls.clear();
    optionalNamespaceDecls.clear();
    for (int i = 0; i < writers.length; i++) {
    writers[i].reset();
    }
    }
     */

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
    public void setDocumentLocator(Locator arg0) {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        try {
            receiver.startDocument();
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
        try {
            receiver.endDocument();
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
    public void startPrefixMapping(String prefix, String namespaceURI)
            throws SAXException {
        if (prefix == null) {
            prefix = "";
        }
        //String ns = nsSupport.getURI(prefix);
        String ns = "NAMESPACEDUMMY";
        if (ns == null || (!ns.equals(namespaceURI))) {
            optionalNamespaceDecls.put(prefix, namespaceURI);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        optionalNamespaceDecls.remove(prefix);
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(
            String namespaceURI,
            String localName,
            String qname,
            Attributes attribs)
            throws SAXException {
        try {
            namespaceDecls.clear();
            //nsSupport.pushContext();
            receiver.startElement(qname);
            String elemPrefix = "";
            int p = qname.indexOf(':');
            if (p > 0) {
                elemPrefix = qname.substring(0, p);
            }
            if (namespaceURI == null) {
                namespaceURI = "";
            }
            /*
            if (nsSupport.getURI(elemPrefix) == null) {
            namespaceDecls.put(elemPrefix, namespaceURI);
            nsSupport.declarePrefix(elemPrefix, namespaceURI);
            }
             */

            // check attributes for required namespace declarations
            String attrName;
            String uri;
            if (attribs != null) {
                for (int i = 0; i < attribs.getLength(); i++) {
                    attrName = attribs.getQName(i);
                    /*
                    if (attrName.equals("xmlns")) {
                    if (nsSupport.getURI("") == null) {
                    uri = attribs.getValue(i);
                    namespaceDecls.put("", uri);
                    nsSupport.declarePrefix("",  uri);
                    }
                    } else if (attrName.startsWith("xmlns:")) {
                    String prefix = attrName.substring(6);
                    if (nsSupport.getURI(prefix) == null) {
                    uri = attribs.getValue(i);
                    namespaceDecls.put(prefix, uri);
                    nsSupport.declarePrefix(prefix, uri);
                    }
                    } else if ((p = attrName.indexOf(':')) > 0) {
                    String prefix = attrName.substring(0, p);
                    uri = attribs.getURI(i);
                    if (nsSupport.getURI(prefix) == null) {
                    namespaceDecls.put(prefix, uri);
                    nsSupport.declarePrefix(prefix, uri);
                    }
                    }
                     */
                }
            }
            Map.Entry nsEntry;
            for (Iterator i = optionalNamespaceDecls.entrySet().iterator(); i.hasNext();) {
                nsEntry = (Map.Entry) i.next();
                String prefix = (String) nsEntry.getKey();
                uri = (String) nsEntry.getValue();
                receiver.namespace(prefix, uri);
                //nsSupport.declarePrefix(prefix, uri); //nsSupport.declarePrefix(prefix, namespaceURI);
            }
            // output all namespace declarations
            for (Iterator i = namespaceDecls.entrySet().iterator(); i.hasNext();) {
                nsEntry = (Map.Entry) i.next();
                String prefix = (String) nsEntry.getKey();
                uri = (String) nsEntry.getValue();
                if (!optionalNamespaceDecls.containsKey(prefix)) {
                    receiver.namespace(prefix, uri);
                }
            }
            //cancels current xmlns if relevant
            if ("".equals(elemPrefix) && !namespaceURI.equals(receiver.getDefaultNamespace())) {
                receiver.namespace("", namespaceURI);
                //nsSupport.declarePrefix("", namespaceURI);
            }
            optionalNamespaceDecls.clear();
            // output attributes
            for (int i = 0; i < attribs.getLength(); i++) {
                if (!attribs.getQName(i).startsWith("xmlns")) {
                    receiver.attribute(
                            attribs.getQName(i),
                            attribs.getValue(i));
                }
            }
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.exist.util.serializer.Receiver#startElement(org.exist.dom.QName)
     */
    public void startElement(QName qname, Attributes attribs) throws SAXException {
        try {
            namespaceDecls.clear();
            //nsSupport.pushContext();
            receiver.startElement(qname);
            String prefix = qname.getPrefix();
            String namespaceURI = qname.getNamespaceURI();
            if (prefix == null) {
                prefix = "";
            }
            if (namespaceURI == null) {
                namespaceURI = "";
            }

            // check attributes for required namespace declarations
            QName attrQName;
            String uri;
            if (attribs != null) {
                for (int i = 0; i < attribs.getLength(); i++) {
                }

            }

            Map.Entry nsEntry;
            String optPrefix;
            if (attribs != null) {
                // output attributes
                for (int i = 0; i < attribs.getLength(); i++) {
                    receiver.attribute(
                            attribs.getQName(i),
                            attribs.getValue(i));

                }
            }
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qname)
            throws SAXException {
        try {
            //nsSupport.popContext();
            receiver.endElement(qname);
            //receiver.setDefaultNamespace(nsSupport.getURI(""));
            receiver.setDefaultNamespace("");
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.exist.util.serializer.Receiver#endElement(org.exist.dom.QName)
     */
    public void endElement(QName qname) throws SAXException {
        try {
            //nsSupport.popContext();
            receiver.endElement(qname);
            receiver.setDefaultNamespace("");
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.exist.util.serializer.Receiver#attribute(org.exist.dom.QName, java.lang.String)
     */
    public void attribute(QName qname, String value) throws SAXException {
        // ignore namespace declaration attributes
        if ((qname.getPrefix() != null && qname.getPrefix().equals("xmlns"))
                || qname.getLocalName().equals("xmlns")) {
            return;
        }
        try {
            receiver.attribute(qname, value);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int len) throws SAXException {
        try {
            receiver.characters(ch, start, len);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    public void characters(CharSequence seq) throws SAXException {
        try {
            receiver.characters(seq);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int len)
            throws SAXException {
        try {
            receiver.characters(ch, start, len);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        try {
            receiver.processingInstruction(target, data);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    public void cdataSection(char[] ch, int start, int len) throws SAXException {
        try {
            receiver.cdataSection(ch, start, len);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity(String arg0) throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#startDTD(java.lang.String, java.lang.String, java.lang.String)
     */
    public void startDTD(String arg0, String arg1, String arg2)
            throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#endDTD()
     */
    public void endDTD() throws SAXException {
    }

    public void documentType(String name, String publicId, String systemId)
            throws SAXException {
        try {
            receiver.documentType(name, publicId, systemId);
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    public void highlightText(CharSequence seq) {
        // not supported with this receiver
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#startEntity(java.lang.String)
     */
    public void startEntity(String arg0) throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#endEntity(java.lang.String)
     */
    public void endEntity(String arg0) throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#startCDATA()
     */
    public void startCDATA() throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#endCDATA()
     */
    public void endCDATA() throws SAXException {
    }

    /* (non-Javadoc)
     * @see org.xml.sax.ext.LexicalHandler#comment(char[], int, int)
     */
    public void comment(char[] ch, int start, int len) throws SAXException {
        try {
            receiver.comment(new XMLString(ch, start, len));
        } catch (TransformerException e) {
            throw new SAXException(e.getMessage(), e);
        }
    }

    /*
    public void setCurrentNode(Node node) {
        // just ignore.
    }

    public Document getDocument() {
        //just ignore.
        return null;
    }
     */
}
