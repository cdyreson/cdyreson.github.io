package xmorph.edu.usu.parser;

/**
 * SAX parser to shred to Morph data store.
 *
 * @author Aswani and Curtis
 */
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.graph.Path;
import xmorph.edu.usu.graph.Id;
import xmorph.edu.usu.graph.Element;
import xmorph.edu.usu.graph.Node;
import xmorph.edu.usu.graph.DocumentRoot;
import xmorph.edu.usu.database.Database;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import java.util.*;
import java.util.regex.*;

public class XMLParser extends DefaultHandler {

    private static boolean dataWanted = true;
    private static Pattern p = Pattern.compile("\\A\\s*\\Z");
    public static boolean verbose = false;
    private Stack<Node> parent = new Stack();
    private Path path;
    private ShapeNode shapeRoot = new ShapeNode();
    private String fileName = "default";
    private Database db;
    private Map<Path, ShapeNode> pathToShapeNode = new HashMap();
    private Stack<Id> idStack = new Stack();
    private Map<Id, Id> siblings = new HashMap();
    private Map<String, TypeId> typeSiblings = new HashMap();
    private Stack<Set<TypeId>> manySetStack = new Stack();
    private Stack<Set<TypeId>> oneSetStack = new Stack();
    private TypeId rootType;
    private Map<TypeId, Map<TypeId, Long>> cardinalityTable = new HashMap();
    private Map<TypeId, Set<TypeId>> typePartialTable = new HashMap();
    private Map<TypeId, Set<TypeId>> typeTotalTable = new HashMap();
    private long documentNumber;
    private int typeNumber;

    /**
     * Default Constructor, does nothing since we need a Database to shred to.
     */
    public XMLParser() {
        // print error since needs a database
        System.err.println("XMLParser needs a Database object");
        System.exit(-1);
    }

    /**
     * Create an instance of the parser that shreds to the specified Database.
     */
    public XMLParser(Database db) {
        this.db = db;
    }

    /*
     * Fix the filename for the associated shredding information
     */
    public void setFileName(String s) {

        /*
        if (s == null || s.contentEquals("")) {
        s = "default";
        }
         */

        if (verbose) {
            System.out.println("Setting filename" + s);
        }
        this.fileName = "/" + s;
    }

    @Override
    /*
     * Begin processing the document
     */
    public void startDocument() {
                //System.out.println("START DOCUMENT ");
                //new Exception().printStackTrace();
        if (verbose) {
            System.out.println("Start document");
        }
        // Establish the document info
        documentNumber = db.metadataTable.get("documentNumber");
        documentNumber++;
        typeNumber = (int) db.metadataTable.get("typeNumber");
        typeNumber++;
        if (verbose) {
            System.out.println("Document number is " + documentNumber);
        }

        Id rootId = new Id(documentNumber);
        rootType = new TypeId(fileName, documentNumber);
        if (dataWanted) {
            idStack.push(rootId);
            DocumentRoot r = new DocumentRoot(rootId);
            parent.push(r);
        }

        path = new Path(fileName);
        if (verbose) {
            System.out.println("Setting path " + path.image());
        }
        db.pathsTable.put(path, rootType);

        // Set up the stack
        manySetStack.add(new HashSet());
        HashSet oneSet = new HashSet();
        //oneSet.add(rootType);
        oneSetStack.add(oneSet);

        // Initialize the cardinality table
        cardinalityTable.put(rootType, new HashMap());

        // Initialize the typePartial table
        //typePartialTable.put(rootType, new HashSet());

        // Initialize the typeTotal table
        //typeTotalTable.put(rootType, new HashSet());
    }

    @Override
    /*
     * Everything is done, finish off the document
     */
    public void endDocument() {
        //docInfo.setRange(new Id().getId());
        //DocumentsCollection.commit(docInfo);
        //shape.addRoot(shapeRoot);

        db.shapesTable.put(this.fileName, shapeRoot);
        //System.out.println("END DOCUMENT " + this.fileName);
        //db.shapesTable.put(documentNumber + "", shapeRoot);
        if (verbose) {
            System.out.println("End document " + documentNumber);
            System.out.println("Shape of data is ");
            System.out.println(shapeRoot.imageXML());
        }

        /*
         * Commit this document
         */
        db.documentsTable.put(fileName, rootType);
        if (verbose) {
            System.out.println("putting " + documentNumber);
        }
        db.metadataTable.put("documentNumber", documentNumber);
        db.metadataTable.put("typeNumber", typeNumber);

        // Run through cardinality table and update the database
        computeTheCardinality(rootType);
        updateCardinalityTable(rootType);
        updateTypePartialityTable(rootType);
        // Braindead but commit by closing and reopening db
        db.closeDatabase();
        db.openDatabase();
        //db.commitDatabase();        // commit the database
    }

    @Override
    /*
     * Recognized an Element, parse the element adding it to the set of Types,
     * extending the closest set, etc.
     */
    public void startElement(String uri, String name, String qName, Attributes atts) {
        if (verbose) {
            if ("".equals(uri)) {
                System.out.println("Start element: name " + name);
            } else {
                System.out.println("Start element: {" + uri + "}" + name);
            }
        }

        // Create the element
        Id parentId;
        Id myId = new Id(); // Doesn't need an object ...
        Element e;
        if (dataWanted) {
            parentId = idStack.peek();
            myId = null;
            if (siblings.containsKey(parentId)) {
                //System.out.println("sibling");
                Id tempId = siblings.get(parentId);
                myId = tempId.nextSibling();

            } else {
                //System.out.println("child");
                myId = parentId.newChild();
            }
            siblings.put(parentId, myId);
            //System.out.println("ID is " + myId.toString() + name + parentId.toString());
            e = new Element(name, myId);
            idStack.push(myId);

            // Record it as a parent
            parent.push(e);
        }
        // Extend current path
        path.extend(name);

        // Update the type list if we have one
        // type is path
        TypeId t = db.pathsTable.get(path);

        if (t == null) {
            // Set up the type, either it is a child or a sibling
            path.remove();
            if (typeSiblings.containsKey(path.image())) {
                t = typeSiblings.get(path.image()).nextSibling(name);
            } else {
                t = db.pathsTable.get(path).newChild(name);
            }

            typeSiblings.put(path.image(), t);
            path.extend(name);
            // Make path persistent
            db.pathsTable.put(path, t);
            db.typesTable.put(t, path);
            db.typesLookupTable.put(t, typeNumber);
            db.typesIntegerToTypeIdTable.put(typeNumber, t);
            typeNumber++;
            db.lcaTable.put(t, t);
            String pathName = name;
            for (int i = path.size() - 2; i > 0; i--) {
                db.abbreviatedTypesTable.put(pathName, t);
                pathName = path.peekAt(i) + "." + pathName;
            }
            if (verbose) {
                System.out.println("Parser adding new element type " + (typeNumber - 1) + " " + name + " " + t.toString() + " " + path.image());
            }
            // Update shape
            ShapeNode childShapeNode = new ShapeNode(t, t);
            pathToShapeNode.put(path, childShapeNode);
            path.remove();
            
            // If it has a parent, then it is not a root
            if (pathToShapeNode.containsKey(path)) {
                ShapeNode parentShapeNode = pathToShapeNode.get(path);
                parentShapeNode.add(childShapeNode);
            } else {
                shapeRoot.add(childShapeNode);
            }
            path.extend(name);
        }

        // Update the type id in the DLN for this node
        if (dataWanted) {
            myId.getDLN().setTypeID(db.typesLookupTable.get(t));
        }

        // Compute the cardianlity
        if (!manySetStack.peek().contains(t)) {
            if (!oneSetStack.peek().contains(t)) {
                if (verbose) {
                    System.out.println("Adding " + t.getName() + " to one ");
                }
                Set s = oneSetStack.peek();
                s.add(t);
            } else {
                if (verbose) {
                    System.out.println("Adding " + t.getName() + " to many ");
                }
                Set s = oneSetStack.peek();
                s.remove(t);
                s = manySetStack.peek();
                s.add(t);
            }
        }

        manySetStack.push(new HashSet());
        oneSetStack.push(new HashSet());

        // Process attributes
        for (int i = 0; i < atts.getLength(); i++) {
            if (verbose) {
                System.out.println("Attributes: " + atts.getLocalName(i) + "= " + atts.getValue(i));
            }
            // Create a new attribute
            //Attribute a = new Attribute(atts.getLocalName(i), atts.getValue(i));
            //if (dataWanted) {
            //    e = (Element) parent.peek();
            //}
            //e.addAttribute(atts.getLocalName(i), atts.getValue(i));

            // Extend current path
            path.extend(atts.getLocalName(i));

            // Update the type list if we have one
            // type is path
            TypeId attributeTypeId = db.pathsTable.get(path);

            if (attributeTypeId == null) {
                // Make path persistent
                // Set up the type, either it is a child or a sibling
                path.remove();
                if (typeSiblings.containsKey(path.image())) {
                    attributeTypeId = typeSiblings.get(path.image()).nextSibling(atts.getLocalName(i));
                } else {
                    attributeTypeId = db.pathsTable.get(path).newChild(atts.getLocalName(i));
                }
                typeSiblings.put(path.image(), attributeTypeId);
                path.extend(atts.getLocalName(i));
                // Make path persistent
                db.pathsTable.put(path, attributeTypeId);
                db.typesTable.put(attributeTypeId, path);
                db.typesLookupTable.put(attributeTypeId, typeNumber);
                db.typesIntegerToTypeIdTable.put(typeNumber, attributeTypeId);
                typeNumber++;
                db.lcaTable.put(attributeTypeId, attributeTypeId);

                String pathName = atts.getLocalName(i);
                for (int j = path.size() - 2; j > 0; j--) {
                    db.abbreviatedTypesTable.put(pathName, attributeTypeId);
                    pathName = path.peekAt(j) + "." + pathName;
                }
                if (verbose) {
                    System.out.println("Parser adding new attribute type " + (typeNumber - 1) + " " + attributeTypeId.toString() + " " + path.image());
                }
                // Update shape
                ShapeNode childShapeNode = new ShapeNode(attributeTypeId, attributeTypeId);
                pathToShapeNode.put(path, childShapeNode);
                if (path.size() >= 0) {
                    path.remove();
                    ShapeNode parentShapeNode = pathToShapeNode.get(path);
                    if (verbose) {
                        System.out.println("Parser adding to shape " + path.toString() + " to " + attributeTypeId.toString());
                    }
                    parentShapeNode.add(childShapeNode);
                    path.extend(name);
                }
            }

            if (dataWanted) {
                // Create an Id for this attribute
                Id attId;
                //System.out.println("Attribute id " + myId.toString());
                if (siblings.containsKey(myId)) {
                    //System.out.println("Sibling attribute");
                    Id tempId = siblings.get(myId);
                    attId = tempId.nextSibling();
                } else {
                    //System.out.println("child attribure");
                    attId = myId.newChild();
                }
                //System.out.println("Aid " + attId.toString());
                siblings.put(myId, attId);
                Element attribute = new Element(atts.getLocalName(i), attId);
                attribute.setAttributeType();
                attribute.addText(atts.getValue(i));


                // Update the type id in the DLN for this node
                attId.getDLN().setTypeID(db.typesLookupTable.get(attributeTypeId));
                //System.out.println("Setting attribute typeID " + attributeTypeId + " " + db.typesLookupTable.get(attributeTypeId)); 

                db.nodesTable.put(attribute);

                // Add attribte to the proper group
                Id groupId = db.buildGroupsTableCollection.get(attributeTypeId, attribute);
                //attribute.setGroupId(groupId);

                db.typedNodeTableCollection.put(attributeTypeId, attribute.getId());
            }
            // Pop the attribute
            path.remove();

        }

    }

    private void computeTypePartiality(TypeId t) {
        // Let's check to see if we've seen this type before
        if (!typeTotalTable.containsKey(t)) {
            // Initialize with all of the types we've seen
            Set s = new HashSet();
            for (TypeId toTypeId : manySetStack.peek()) {
                s.add(toTypeId);
            }
            for (TypeId toTypeId : oneSetStack.peek()) {
                s.add(toTypeId);
            }
            typeTotalTable.put(t, s);
            typePartialTable.put(t, new HashSet());
            return;
        }

        // We've seen this type before, check to see if any types are new
        // or missing

        Set<TypeId> totalSet = typeTotalTable.get(t);
        Set<TypeId> reducedTotalSet = new HashSet();
        Set<TypeId> partialSet = typePartialTable.get(t);
        for (TypeId toTypeId : manySetStack.peek()) {
            // Skip if already partial
            if (partialSet.contains(toTypeId)) {
                // do nothing, it is already partial
            } else {
                if (totalSet.contains(toTypeId)) {
                    // keep it in the totalSet
                    reducedTotalSet.add(toTypeId);
                    totalSet.remove(toTypeId);
                } else {
                    // Haven't seen it before
                    partialSet.add(toTypeId);
                }
            }

        }

        // Repeat the above loop for the one stack
        for (TypeId toTypeId : oneSetStack.peek()) {
            // Skip if already partial
            if (partialSet.contains(toTypeId)) {
                // do nothing, it is already partial
            } else {
                if (totalSet.contains(toTypeId)) {
                    // keep it in the totalSet
                    reducedTotalSet.add(toTypeId);
                    totalSet.remove(toTypeId);
                } else {
                    // Haven't seen it before
                    partialSet.add(toTypeId);
                }
            }
        }

        // Whatever is left over in the total set is partial
        for (TypeId ts : totalSet) {
            //System.out.println("Checking total " + ts.getName());
            if (partialSet.contains(ts)) {
                // do nothing, it is already partial
            } else {
                partialSet.add(ts);
            }
        }
        // Update the type total
        typeTotalTable.put(t, reducedTotalSet);
    }

    private void computeTheCardinality(TypeId t) {
        // Compute type partiality
        computeTypePartiality(t);

        // Compute the cardinality
        for (TypeId toTypeId : manySetStack.pop()) {
            // Create the type mapping if it does not exist
            if (!cardinalityTable.containsKey(t)) {
                cardinalityTable.put(t, new HashMap());
            }
            Map<TypeId, Long> tab = cardinalityTable.get(t);
            if (!tab.containsKey(toTypeId) || tab.get(toTypeId).intValue() < 2) {
                tab.put(toTypeId, new Long(2));
            }
        }

        for (TypeId toTypeId : oneSetStack.pop()) {
            // Create the type mapping if it does not exist
            if (!cardinalityTable.containsKey(t)) {
                cardinalityTable.put(t, new HashMap());
            }
            Map<TypeId, Long> tab = cardinalityTable.get(t);
            if (!tab.containsKey(toTypeId)) {
                tab.put(toTypeId, new Long(1));
            }
        }
    }

    @Override
    /*
     * Finish off an element, reducing the Path and stack of Node(s) along the
     * current Path.
     */
    public void endElement(String uri, String name, String qName) {
        TypeId t = db.pathsTable.get(path);
        Element e;
        Id id;
        if (dataWanted) {
            e = (Element) parent.pop();
            id = idStack.pop();
            if (siblings.containsKey(id)) {
                siblings.remove(id);
            }

            // Make the element persistent, updating the Node information
            db.nodesTable.put(e);

            // Add element to the proper group
            Id groupId = db.buildGroupsTableCollection.get(t, e);
            Node groupNode = db.nodesTable.get(groupId);
            db.groupedNodesTable.put(e.getId(),groupNode);
            //System.out.println("FFF " + groupId);
            //e.setGroupNode(new Node(groupId));

            // Add node to the list of nodes of this type
            db.typedNodeTableCollection.put(t, e.getId());
            //GroupedTypedNodesCollections.put(t, e);
            //GroupedNodesCollections.put(t, e);
        }
        // Compute the cardinality
        computeTheCardinality(t);

        path.remove();

        if (verbose) {
            if ("".equals(uri)) {
                System.out.println("End element: " + name);
            } else {
                System.out.println("End element:   {" + uri + "}" + name);
            }
        }
    }

    @Override
    /*
     * Characters could be added as text nodes, or we could just create one
     * string. I decided to just create one string.
     */
    public void characters(char ch[], int start, int length) {
        if (dataWanted) {
            String str = new String(ch, start, length);

            // Create the text node
            //Id parentId = idStack.peek();
            //Id myId = parentId.newChild();
            //new Text(str, myId);
            //System.out.print("\"\n");
            // Trim whitespace
            Matcher m = p.matcher(str);
            if (m.matches()) {
                str = "";
            }

            str = str.trim();

            Element e = (Element) parent.peek();
            if (str.length() > 0) {
                e.addText(str);
            }
            if (verbose && !str.isEmpty()) {
                System.out.println(" Text: " + str);
            }
        }
    }

    /*
     * Recursively descend the type partiality table
     */
    private void updateTypePartialityTable(TypeId pId) {
        for (TypeId parentTypeId : typePartialTable.keySet()) {
            Set<TypeId> pTypes = typePartialTable.get(parentTypeId);
            for (TypeId childTypeId : pTypes) {
                if (verbose) {
                    System.out.println("Type partial from " + parentTypeId.getDLN() + " to " + childTypeId.getDLN() + ".");
                }
                db.typePartialityTable.put(parentTypeId, childTypeId);
            }
        }
    }

    /*
     * Recursively descend the cardinality table
     */
    private void updateCardinalityTable(TypeId parentTypeId) {
        Map<TypeId, Long> tab = cardinalityTable.get(parentTypeId);
        for (TypeId childTypeId : tab.keySet()) {
            if (verbose) {
                System.out.println("Relationship from " + parentTypeId.getName() + " to " + childTypeId.getName() + " is " + tab.get(childTypeId).intValue() + ".");
            }
            db.cardinalityTable.put(parentTypeId, childTypeId, tab.get(childTypeId));
            if (cardinalityTable.containsKey(childTypeId)) {
                updateCardinalityTable(childTypeId);
            }
        }
    }
}
