package xmorph.edu.usu.algebra;

import xmorph.edu.usu.reporting.TypeInformation;
import xmorph.edu.usu.reporting.LossInformation;
import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.graph.Path;
import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.Database;
import java.util.*;

public class LCAJoin extends Operator {

    public Operator parent;
    Map<TypeId, HashSet<TypeId>> parentMap;

    public LCAJoin(Operator left, Operator right) {
        parent = left;
        child = right;
        parentMap = new HashMap();
    }

    @Override
    /* The types of the operator can be restricted in the
     * second phase of type analysis. This limits the types
     * for the operator to the passed types. For an LCAJoin,
     * if a parent isn't in the restricted types, then omit it.
     */
    public void restrictType(HashSet<TypeId> goodTypes) {
        //System.out.println("Did we restrict type");
        //Map<TypeId, HashSet<TypeId>> parentMapCopy = new HashMap();
        HashSet<TypeId> childTypes = new HashSet();
        for (TypeId t : goodTypes) {
            // First do the children
            HashSet<TypeId> children = parentMap.get(t);
            childTypes.addAll(children);
            //parentMapCopy.put(t, children);
        }
        parent.restrictType(goodTypes);
        child.restrictType(childTypes);
    }

    @Override
    /* Evaluate produces a ShapeNode that records the evaluation
     * of the LCAJoin. Each ShapeNode in the query shape basically 
     * represents an LCAJoin.
     * @param db - The database we are working with
     * @param dataShape - The shape of the data, this is ignored
     *                    by this operation.
     */
    public ShapeNode evaluate(Database db, ShapeNode dataShape) {
        ShapeNode childNode = child.evaluate(db, dataShape);
        ShapeNode parentNode = parent.evaluate(db, dataShape);
        
        // A drop node will return a null so check if we have non-nulls
        if (childNode == null) return parentNode;
        if (parentNode == null) return childNode;

        //System.out.println("LCAJoin.java evaluating ");
        for (ShapeNode aParent : parentNode.getChildren()) {
            //System.out.println("have parent ");
            if (parentMap.containsKey(aParent.getBaseTypeId())) {
               //System.out.println("what is parent map "  + aParent.getBaseTypeId().getName() + aParent.getBaseTypeId().toString());
 
                HashSet<TypeId> childSet = parentMap.get(aParent.getBaseTypeId());
                for (ShapeNode aChild : childNode.getChildren()) {
                    //System.out.println(" have base type " + aChild.getBaseTypeId().getName() + aChild.getBaseTypeId().toString());
                    /* Add an edge in the shape */

                    if (childSet.contains(aChild.getBaseTypeId())) {
                        //System.out.println(" in child ");
                        //System.out.println("LCA start " + aChild.getBaseTypeId().toString() + " " + aParent.getBaseTypeId().toString()); 
                        TypeId lca = new TypeId("", aParent.getBaseTypeId().lca(aChild.getBaseTypeId()));
                        //System.out.println("LCA for " + aChild.getBaseTypeId().toString() + " " + aParent.getBaseTypeId().toString() + " " + lca.getDLN().toString());                      
                        //System.out.println("Closest join " + aChild.getBaseTypeId().toString() + " " +
                        //  aParent.getBaseTypeId().toString() + " " +
                        //  lca.toString());
                        if (TypeInformation.captureTypeInformation) {
                            TypeInformation.appendLn(
                              "Closest join between type "
                              + db.typesTable.get(aParent.getBaseTypeId()).image()
                              + " and "
                              + db.typesTable.get(aChild.getBaseTypeId()).image()
                              + ".");
                        }
                        if (LossInformation.captureErrorInformation) {

                            Boolean nonAdditive = db.cardinalityTable.isNonAdditive(
                              aParent.getBaseTypeId(),
                              aChild.getBaseTypeId(),
                              lca);
                            Boolean inclusive = db.typePartialityTable.isInclusive(
                              aParent.getBaseTypeId(),
                              aChild.getBaseTypeId(),
                              lca);
                            String s = " yikes!";
                            if (nonAdditive) {
                                if (inclusive) {
                                    s = " is reversible (non-additive and inclusive).";
                                } else {
                                    s = " is non-additive but not inclusive.";
                                }
                            } else {
                                  if (inclusive) {
                                    s = " is additive but inclusive.";
                                } else {
                                    s = " is additive and not inclusive.";
                                }
                            }
                            LossInformation.appendLn(
                              "Closest join between type "
                              + db.typesTable.get(aParent.getBaseTypeId()).image()
                              + " and "
                              + db.typesTable.get(aChild.getBaseTypeId()).image()
                              + s);
                            LossInformation.nonAdditive = nonAdditive && LossInformation.nonAdditive;
                            LossInformation.inclusive = inclusive && LossInformation.inclusive;
                        }
                        aChild.setLCA(lca);
                        aParent.addChild(aChild);
                    }
                }
            }
        }
        return parentNode;
    }

    @Override
    /* Do a type analysis. Figure out the type of the nodes produced by this
     * operation. The types for this node are determined by the set of types
     * for the children. Each type has to be close to some other type in a
     * given document to determine what the types are. The type distance is
     * used to determine this relationship. Only types that have a minimal type
     * distance are related.
     * @param db - The database we are using to evaluate this query.
     * @returns - The types.
     */
    public HashSet<TypeId> typeOf(Database db) {
        HashSet<TypeId> parentTypes = parent.typeOf(db);
        HashSet<TypeId> childTypes = child.typeOf(db);
        types = new HashSet();

        // Find the minimal distance between all pairs of types
        for (TypeId parentTypeId : parentTypes) {
            int minimalDistance = Integer.MAX_VALUE;
            Path parentPath = db.typesTable.get(parentTypeId);
            parentMap.put(parentTypeId, new HashSet());
            if (TypeInformation.captureTypeInformation) {
                TypeInformation.appendLn("Checking type " + parentPath.image());
            }
            for (TypeId childTypeId : childTypes) {
                Path childPath = db.typesTable.get(childTypeId);
                //int dist = parentPath.distanceToLCA(db, childPath);
                int dist = parentTypeId.distanceToLCA(childTypeId);
                //System.out.println(minimalDistance + " Distance is " + dist + " " + childTypeId.toString() + " " + parentTypeId.toString());
                if (TypeInformation.captureTypeInformation) {
                    TypeInformation.appendLn(
                      "Type distance from "
                      + parentPath.image()
                      + " to "
                      + childPath.image()
                      + " is "
                      + dist
                      + ".");
                }
                if (dist < Integer.MAX_VALUE && dist <= minimalDistance) {
                    if (dist < minimalDistance) {
                        //System.out.println("resetting types ");                      
                        parentMap.put(parentTypeId, new HashSet());
                        minimalDistance = dist;
                    }
                    // We have a pair of nodes that are connected
                    if (!parentMap.containsKey(parentTypeId)) {
                        parentMap.put(parentTypeId, new HashSet());
                    }
                    //System.out.println("adding type id " + parentTypeId.getDLN().toString() + " " + childTypeId.getDLN().toString());
                    parentMap.get(parentTypeId).add(childTypeId);

                    // Add this type to the types only if we actually have a child
                    types.add(parentTypeId);
                }
            }
        }
        return types;
    }

    @Override
    public void translate(Dictionary d) {
        child.translate(d);
        parent.translate(d);
    }
}
