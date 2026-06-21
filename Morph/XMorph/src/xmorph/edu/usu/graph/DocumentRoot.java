package xmorph.edu.usu.graph;

/**
 * The document root represents the root node in the document, just
 * above the first XML element in the document.
 * @author Curtis
 */
public class DocumentRoot extends Node {

    public DocumentRoot() {
        super(NodeType.DOCUMENTROOT, new Id(), "", "");
    }

    public DocumentRoot(Id id) {
        super(NodeType.DOCUMENTROOT, id, "", "");
    }
}
