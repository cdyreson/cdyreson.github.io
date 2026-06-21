/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmorph.edu.usu.evaluation.xquery;

//import xmorph.edu.usu.graph.TypeId;
import xmorph.edu.usu.shape.ShapeNode;
//import xmorph.edu.usu.shape.*;
import java.util.*;
import xmorph.edu.usu.graph.*;
//import xmorph.edu.usu.algebra.*;
import xmorph.edu.usu.database.*;

/**
 *
 *
 * @author Kiran
 */
public class Xquery {

    ShapeNode s;
    List<ShapeNode> l;
    XQueryQuery[] xquery;
    Database db;
    int VarNumber = 1;

    public Xquery(Database d, ShapeNode s) {
        int queryCount = 0;
        Iterator<ShapeNode> i;
        this.db = d;
        this.s = s;

        xquery = new XQueryQuery[10];

        XQueryQuery q = evaluate(null, null, s);
        xquery[queryCount++] = q;


    }

    public XQueryQuery evaluate(String v, ShapeNode p, ShapeNode n) {
        XQueryQuery Xquery = new XQueryQuery();
        boolean childHidden = false;
        String presentVar = "", prevVar, relativePath, wherePred = "";
        ShapeNode relativePrevNode = p;
        presentVar = newVariable();
        prevVar = v;
        relativePath = generateXpath(relativePrevNode.getBaseTypeId(), n.getBaseTypeId());    // relative path to previous variable used

        Iterator<ShapeNode> childs = n.getChildren().iterator();
        if (childs != null) {

            while (childs.hasNext()) {
                ShapeNode temp = childs.next();
                XQueryQuery qTemp = evaluate(presentVar, n, temp);
                Xquery.extendQuery(qTemp);
                if (qTemp.hidden == true) {
                    childHidden = true;
                    if (Xquery.hiddenChildPath.contentEquals("")) {
                        Xquery.hiddenChildPath = qTemp.hiddenChildPath;
                    } else {
                        Xquery.hiddenChildPath = Xquery.hiddenChildPath + " and " + qTemp.hiddenChildPath;
                    }
                }
            }
        }


        if (n.isHidden()) {


            Xquery.hidden = true;
            if (!n.hasWhere()) {

                Xquery.extendHiddenPath(relativePath);
            } else {
                xmorph.edu.usu.algebra.Comparator comp = n.getWhere();
                Xquery.extendHiddenPathWithWhere(relativePath, comp.getStringValue());
            }

            return Xquery;
        }



        if (n.isGrouped()) {
            String tempName = getName(n.getBaseTypeId());

            if (n.hasWhere()) {
                xmorph.edu.usu.algebra.Comparator comp = n.getWhere();
                wherePred = "text() = \"" + comp.getStringValue() + "\"";
            }


            Xquery.extendGroupReturn(tempName, presentVar);
            if (!childHidden && !n.hasWhere()) // no hidden child
            {
                Xquery.extendGroupFor(presentVar, prevVar, relativePath);
            } else {
                String predicate = "";
                if (childHidden) {
                    predicate = extendPredicate(predicate, Xquery.hiddenChildPath);
                }
                if (n.hasWhere()) {
                    predicate = extendPredicate(predicate, wherePred);
                }
                Xquery.extendGroupForWithPredicate(presentVar, prevVar, relativePath, predicate);
            }

        } else //final case
        {

            String tempName = getName(n.getBaseTypeId());


            if (n.hasWhere()) {
                xmorph.edu.usu.algebra.Comparator comp = n.getWhere();
                wherePred = "text() = \"" + comp.getStringValue() + "\"";
            }

            if (!n.hasWhere() && !childHidden) {
                Xquery.extendFor(presentVar, prevVar, relativePath);
            } else {
                String predicate = "";
                if (childHidden) {
                    predicate = extendPredicate(predicate, Xquery.hiddenChildPath);
                }
                if (n.hasWhere()) {
                    predicate = extendPredicate(predicate, wherePred);
                }
                Xquery.extendForWithPredicate(presentVar, prevVar, relativePath, predicate);
            }

            Xquery.extendReturn(tempName, presentVar);

        }



        return Xquery;
    }

    public String generateXpath(TypeId from, TypeId to) {
        if (from != null) {
            return "Path from TypeId " + from + "to TypeId" + to;
        } else {
            return "Path from root to TypeId" + to;
        }
    }

    public String newVariable() {
        String nVariable = "variable";
        VarNumber = VarNumber + 1;
        nVariable = nVariable + VarNumber;
        return nVariable;
    }

    public String getName(TypeId t) {
        return t.getName();
        /*
        TableIterator t = (TableIterator) db.openIterator("abbreviatedTypes", String.class, List.class);
        while (t.hasNext()) {
            t.next();
        }
        return null;
        */
    }

    public String extendPredicate(String predicate, String extension) {
        if (predicate.contentEquals("")) {
            predicate = extension;
        } else {
            predicate = predicate + " and " + extension;
        }
        return predicate;
    }

    public XQueryQuery[] getXqueries() {
        return xquery;
    }
}
