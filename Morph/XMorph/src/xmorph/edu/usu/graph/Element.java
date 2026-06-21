package xmorph.edu.usu.graph;

import java.io.Serializable;
//import xmorph.edu.usu.evaluation.merge.Graph;
import java.util.*;

/**
 * Implements an Element node in the Morph data store.
 * @author Curtis
 */
public class Element extends Node implements Serializable {

    public Element(String name, Id id) {
        super(NodeType.ELEMENT, id, name, "");
    }

    public Element() {
        super();
    }

    @Override
    public Element cloneMe() {
        Element n = new Element();
        n.id = this.id;
        //n.groupId = this.groupId;
        n.groupNode = this.groupNode;
        n.type = this.type;
        n.name = this.name;
        n.value = this.value;
        n.hide = hide;
        n.newChildren = this.newChildren;
        n.grouped = this.grouped;
        n.attribute = this.attribute;
        n.attributeNames = this.attributeNames;
        n.clone = currentCloneValue++;
        return n;
    }

    public void addText(String s) {
        value += s;
    }

    /* Construct a String value for grouping purposes.
     * The value represents the Grouped value of the element.
     * For now, we'll assume it is the value of the attributes
     * and text value.
     */
    public String groupValue() {
        return value;
    }

    @Override
    public String imageXMLPrettyPrint(String indent, boolean attributePossible) {
        String childrenString = "",
                attributesString = "",
                endElementString = "",
                startElementString = "";

        if (!hide) {
            startElementString = indent + "<" + name;
            attributesString = ">" + value + "";
        }

        //SortedSet<Node> children = graph.getChildren(this);
        if (this.newChildren != null) {
            if (!hide && newChildren.size() > 0) {
                childrenString += "\n";
            }
            for (Node child : newChildren) {
                // Me being hidden changes the indent
                if (hide) {
                    childrenString += child.imageXMLPrettyPrint(indent, attributePossible);
                } else {
                    // Is this child an attribute, then maybe add to attribute string
                    if (child.isAttribute()) {
                        if (!child.hide) {
                            if (attributeNames.contains(child.getName())) {
                                childrenString += child.imageXMLPrettyPrint(indent + "    ", attributePossible);
                            } else {
                                attributesString = child.imageAsAttribute() + attributesString;
                                attributeNames.add(child.getName());
                            }
                        }
                    } else {
                        childrenString += child.imageXMLPrettyPrint(indent + "    ", attributePossible);
                    }
                }
            }
        }
        if (!hide) {
            if (newChildren != null && newChildren.size() > 0) {
                endElementString += indent;
            }
            endElementString += "</" + name + ">" + "\n";
        }
        return startElementString + attributesString + childrenString + endElementString;
    }

    @Override
    public String imageXMLPrettyPrint(String indent, boolean attributePossible, xmorph.edu.usu.evaluation.merge.Graph graph) {
        String childrenString = "",
                attributesString = "",
                endElementString = "",
                startElementString = "";

        if (!hide) {
            startElementString = indent + "<" + name;
            attributesString = ">" + value + "";
        }

        SortedSet<Node> children = graph.getChildren(this);
        if (children != null) {
            if (!hide && children.size() > 0) {
                childrenString += "\n";
            }
            for (Node child : children) {
                // Me being hidden changes the indent
                if (hide) {
                    childrenString += child.imageXMLPrettyPrint(indent, attributePossible, graph);
                } else {
                    // Is this child an attribute, then maybe add to attribute string
                    if (child.isAttribute()) {
                        if (!child.hide) {
                            if (attributeNames.contains(child.getName())) {
                                childrenString += child.imageXMLPrettyPrint(indent + "    ", attributePossible, graph);
                            } else {
                                attributesString = child.imageAsAttribute() + attributesString;
                                attributeNames.add(child.getName());
                            }
                        }
                    } else {
                        //System.out.println("childis " + child.name);
                        childrenString += child.imageXMLPrettyPrint(indent + "    ", attributePossible, graph);
                    }
                }
            }
        }
        if (!hide) {
            if (children != null && children.size() > 0) {
                endElementString += indent;
            }
            endElementString += "</" + name + ">" + "\n";
        }
        return startElementString + attributesString + childrenString + endElementString;
    }

    @Override
    public String imageXMLPrettyPrint(String indent, boolean attributePossible, xmorph.edu.usu.evaluation.shapelyidmerge.Graph graph) {
        String childrenString = "",
                attributesString = "",
                endElementString = "",
                startElementString = "";

        if (!hide) {
            startElementString = indent + "<" + name;
            attributesString = ">" + value + "";
        }

        SortedSet<Node> children = graph.getChildren(this);
        if (children != null) {
            if (!hide && children.size() > 0) {
                childrenString += "\n";
            }
            for (Node child : children) {
                // Me being hidden changes the indent
                if (hide) {
                    childrenString += child.imageXMLPrettyPrint(indent, attributePossible, graph);
                } else {
                    // Is this child an attribute, then maybe add to attribute string
                    if (child.isAttribute()) {
                        if (!child.hide) {
                            if (attributeNames.contains(child.getName())) {
                                childrenString += child.imageXMLPrettyPrint(indent + "    ", attributePossible, graph);
                            } else {
                                attributesString = child.imageAsAttribute() + attributesString;
                                attributeNames.add(child.getName());
                            }
                        }
                    } else {
                        //System.out.println("childis " + child.name);
                        childrenString += child.imageXMLPrettyPrint(indent + "    ", attributePossible, graph);
                    }
                }
            }
        }
        if (!hide) {
            if (children != null && children.size() > 0) {
                endElementString += indent;
            }
            endElementString += "</" + name + ">" + "\n";
        }
        return startElementString + attributesString + childrenString + endElementString;
    }

    @Override
    public String imageXML() {
        String s = "";
        if (!hide) {
            s = "<" + name + ">" + value;
        }
        if (this.newChildren != null) {
            for (int i = 0; i < newChildren.size(); i++) {
                s += newChildren.get(i).imageXML();
            }
        }
        if (!hide) {
            s += "</" + name + ">";
        }
        return s;
    }
}
