package xmorph.edu.usu.graph;

import java.io.Serializable;


/**
 *
 * @author aswani
 */
public class Text extends Node implements Serializable {

    public Text(String value, Id id) {
        super(NodeType.TEXT, id, "", value);
    }
    
}
