package xmorph.edu.usu.graph;

import java.util.*;
//import xmorph.edu.usu.evaluation.merge.*;
//import xmorph.edu.usu.evaluation.shapelyidmerge.*;
import java.io.Serializable;

/**
 * A Node class implements a node in the model
 *
 * @author Curtis
 */
public class Node implements Serializable, Comparable, Cloneable {

    public static enum NodeType {

        ATTRIBUTE, ELEMENT, TEXT, DOCUMENTROOT
    }
    static int currentCloneValue = 0;
    Id id;
    Id groupId;
    Node groupNode;
    NodeType type;
    //TypeId typeId;
    transient String name;
    String value;
    transient boolean hide = false;
    public transient List<Node> newChildren;
    transient boolean grouped = false;
    boolean attribute = false;
    transient HashSet<String> attributeNames = new HashSet();
    int clone = 0;

    // We also need the Typeid!
    public Node(NodeType type, Id id, String name, String value) {
        this.type = type;
        //this.groupId = new Id(id.getDLN()); // Initially the group id is the same as the id, this may be changed later
        this.groupNode = this;  // Initially the grouped node is self (probably could be null)
        this.id = id;
        this.name = name;
        this.value = value;
        this.newChildren = new ArrayList();
        this.grouped = false;
        //System.out.println("Cloning " + id.toString() + name);
        clone = currentCloneValue++;
        if (type == NodeType.DOCUMENTROOT) {
            //System.out.println("is root");
            clone = 0;
        }
    }

    // This one is called only for clones!
    public Node() {
    }

    // This one is called only for groupNodes!
    public Node(Id id) {
        this(NodeType.ELEMENT, id, "", "");
    }

    public Node cloneMe() {
        if (type == NodeType.DOCUMENTROOT) {
            //System.out.println("asdf");
            return this;
        }
        Node n = this.cloneMe();
        n.clone = currentCloneValue++;
        return n;
    }

    public void setClone(int clone) {
        this.clone = clone;
    }

    public void hide() {
        hide = true;
    }

    /*
     public boolean equals(Node other) {
     return id.getId() == other.id.getId();
     }
     */
    public boolean isGrouped() {
        return grouped;
    }

    public boolean isAttribute() {
        return attribute;
    }

    /* Identify this "Element" as an element rather than an attribute, turn off the
     * attribute flag.
     */
    public void setNoAttribute() {
        attribute = false;
    }

    /* Identify this "Element" as an attribute, turn on the
     * attribute flag.
     */
    public void setAttributeType() {
        attribute = true;
    }

    public void setGrouped() {
        grouped = true;
    }

    public void setGroupId(Id id) {
        groupId = id;
    }

    public Id getGroupId() {
        return groupId;
    }

    public void setGroupNode(Node n) {
        setGrouped();
        groupNode = n;
    }

    public Node getGroupNode() {
        return groupNode;
    }

    /*
     public void setGroupId(Id g) {
     groupId = g;
     }
    
     public Id getGroupId() {
     return groupId;
     }
     */
    public String format() {
        return imageXML();
    }
    /* For hashing purposes, equals tests if the Ids have the same long value
     */

    @Override
    public int compareTo(Object obj) {
        /*
         if (obj.getClass() != Node.class) {
         System.out.println("aklsdfjklajsdf " + obj.getClass().toString());
         return -1;
         }
         */
        return compareTo((Node) obj);
    }

    public int compareTo(Node obj) {
        // clone values are equal
        if (grouped) {
            //return groupId.compareTo(obj.groupId);
            if (groupNode != this) {
                return groupNode.compareTo(obj.groupNode);
            }
        }

        if (clone > obj.clone) {
            return 1;
        } else if (clone < obj.clone) {
            return -1;
        }
        return id.compareTo(obj.id);

    }

    @Override
    public boolean equals(Object other) {
        //System.out.println("here");
        return equals((Node) other);
    }

    /* For hashing purposes, equals tests if the Ids have the same long value
     */
    public boolean equals(Node other) {
        if (grouped) {
            //System.out.println("id grouogped");
            //return groupId.equals(other.groupId) && typeId.equals(other.typeId) && clone == other.clone;
            //return groupId.equals(other.groupId) && clone == other.clone;
            //return groupId.equals(other.groupId);
            if (groupNode != this) {
                return groupNode.equals(other.groupNode);
            }
        }
        //System.out.println("not grouped" + id.toString() + " " + other.id.toString());
        //return id.equals(other.id) && typeId.equals(other.typeId) && clone == other.clone;
        return id.equals(other.id) && clone == other.clone;
    }

    /* For hashing purposes, generate a hash code
     */
    @Override
    public int hashCode() {
        //System.out.println("hashcode " + id.hashCode());
        if (grouped) {
            //return groupId.hashCode() + clone;
            //return groupId.hashCode();
            if (groupNode != this) {
                return groupNode.hashCode();
            }
        }
        return id.hashCode() + clone;
    }

    public Node addChild(Node n) {
        //System.out.println("Node.java Adding child");
        if (this.newChildren == null) {
            this.newChildren = new ArrayList();
        }
        this.newChildren.add(n);
        // An attribute can't have children
        attribute = false;
        // If the child is an attribute, check that its names isn't in the set
        if (n.isAttribute()) {
            if (attributeNames.contains(n.name)) {
                n.attribute = false;
            } else {
                attributeNames.add(n.name);
            }
        }
        return this;
    }

    public void setAttribute() {
        //System.out.println("set attribute");
        this.attribute = true;
    }

    public void clearAttribute() {
        this.attribute = false;
    }

    public String imageXML() {
        String s = "";
        for (Node n : newChildren) {
            s += n.imageXML();
        }
        return s;
    }

    public String imageAsAttribute() {
        return " " + name + "=\"" + value + "\"";
    }

    public String imageXMLPrettyPrint() {
        return this.imageXMLPrettyPrint("", true);
    }

    public String imageXMLPrettyPrint(String indent, boolean attributePossible, xmorph.edu.usu.evaluation.merge.Graph graph) {
        String s = "";
        //System.out.println("in herex " + id.toString());
        SortedSet<Node> children = graph.getChildren(this);
        for (Node n : children) {
            //System.out.println("ffox " + n.getClass() + " " + n.getId().toString());
            s = n.imageXMLPrettyPrint(indent + "   ", attributePossible, graph);
        }
        //System.out.println("s is " + s);
        return s;
    }

    public String imageXMLPrettyPrints(String indent, boolean attributePossible, xmorph.edu.usu.evaluation.merge.Graph graph) {
        String s = "";
        //System.out.println("in herex " + id.toString());
        SortedSet<Node> children = graph.getChildren(this);
        for (Node n : children) {
            //System.out.println("ffox " + n.getId().toString());
            s = n.imageXMLPrettyPrints(indent + "   ", attributePossible, graph);
        }
        //System.out.println("s is " + s);
        return s;
    }

    public String imageXMLPrettyPrint(String indent, boolean attributePossible, xmorph.edu.usu.evaluation.shapelyidmerge.Graph graph) {
        String s = "";
        //System.out.println("in here " + attributePossible + " " + id.toString());
        SortedSet<Node> children = graph.getChildren(this);
        for (Node n : children) {
            //System.out.println("ffo " + id.toString());
            s += n.imageXMLPrettyPrint(indent + "   ", attributePossible, graph);
        }
        return s;
    }

    public String imageXMLPrettyPrint(String indent, boolean attributePossible) {
        String s = "";
        for (Node n : newChildren) {
            //s += indent + n.imageXMLPrettyPrint(indent + "   ") + "\n";
            s += n.imageXMLPrettyPrint(indent + "   ", attributePossible);
        }
        return s;
    }

    public NodeType getType() {
        return this.type;
    }

    public Id getId() {
        return this.id;
    }

    // Use this only under very special circumstances!
    public void setId(Id id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String image() {
        return "name " + name + " value " + value + " type " + type;
    }
}
