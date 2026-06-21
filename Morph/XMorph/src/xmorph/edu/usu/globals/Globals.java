/*
 * Global variables, a hack but to facilitate communication between
 * XMorph and eXist
 */
package xmorph.edu.usu.globals;

import java.util.*;
import xmorph.edu.usu.api.XMC;
//import xmorph.edu.usu.shape.ShapeNode;
//import xmorph.edu.usu.evaluation.virtual.VDLNMap;
//import xmorph.graph.TypeId;
//import org.exist.dom.StoredNode;
//import org.exist.storage.serializers.StoredNodeWithChildCount;
//import xmorph.edu.usu.graph.Id;
//import org.exist.numbering.NodeId;
import xmorph.edu.usu.shape.ShapeNode;

/**
 * Global variables
 * @author Curtis Dyreson
 */
public class Globals {

    public static XMC xmc = new XMC(xmorph.edu.usu.database.berkeleyDB.Database.getDbHome());
    //public static Map<Integer, VDLNMap> vDLNMap;
    //public static Map<Integer, List<NodeId>> nodeListMap;
    //public static Map<Integer, List<NodeId>> textNodeListMap;
    //public static Map<NodeId, StoredNodeWithChildCount> storedNodeMap;
    // Shapes is the list of shapes created by a guard
    public static List<ShapeNode> shapes;
    public static List<ShapeNode> dataShapes;
    // Wanted type set is a set of types
    public static Set<Integer> wantedTypeSet;
    public static Stack<Boolean> hasGuard;
    public static Stack<String> guards;
    //public static Map<Integer,ShapeNode> shapeMap = new HashMap();

    public static void reset() {
        //vDLNMap = new HashMap();
        //nodeListMap = new HashMap();
        //textNodeListMap = new HashMap();
        //storedNodeMap = new HashMap();
        wantedTypeSet = new HashSet();
        //shapeMap = new HashMap();
        hasGuard = new Stack();
        hasGuard.add(Boolean.FALSE);
        dataShapes = new ArrayList();
        guards = new Stack();
    }

    public static void setXMC(XMC x) {
        xmc = x;
    }
}
