package xmorph.edu.usu.graph;

import xmorph.edu.usu.database.Database;
import java.io.Serializable;
/**
 *
 * @author Curtis
 */
import java.util.*;

public class Path implements Serializable {

    private Stack<String> path;
    transient private Stack<String> image;
    String separator = "/";
    String separatorRegex = "\\/";
    //String separator = ".";
    //String separatorRegex = "\\.";
    //Database db;

    public Path() {
        path = new Stack<String>();
        image = new Stack<String>();
        //this.db = db;
    }

    /*
     * Construct a Path from a String representation of the path. We assume that
     * the path is represented as a sequence of names separated by a specific
     * separator, which is an instance variable in this class.
     */
    public Path(String s) {
        path = new Stack<String>();
        image = new Stack<String>();
        String[] a = s.split(separatorRegex);
        for (int i = 0; i < a.length; i++) {
            this.extend(a[i]);
        }
    }

    /*
     * Fetch the name of the last element in the Path.
     */
    public String peekAt(int i) {
        return path.elementAt(i);
    }

    @Override
    /*
     * Compare two Paths. Will throw a cast error if the obj is not a Path.
     */
    public boolean equals(Object obj) {
        return equals((Path) obj);
    }

    @Override
    /*
     * The hash code is of the String representation of the Path.
     */
    public int hashCode() {
        return image().hashCode();
    }

    /*
     * Compare two Paths. The other object must be a Path.
     */
    public boolean equals(Path other) {
        return this.image().equals(other.image());
    }

    /*
     * Add an element to the end of a Path.
     */
    public void extend(String str) {
        if (size() > 0) {
            String top = image.peek();
            image.push(top + separator + str);
        } else {
            image.push(str);
        }
        path.push(str);
    }

    /*
     * Continue the LCA computation.
     */
    private void lcaInner(Path me, Path other, int currentThis, int currentOther) {
        if (other.size() == currentOther) {
            return;
        }
        if (this.size() == currentThis) {
            return;
        }
        String topOther = other.path.get(currentOther);
        String topSelf = this.path.get(currentThis);
        //System.out.println("comapring " + topSelf + topOther);
        if (topSelf.equals(topOther)) {
            me.extend(topSelf);
            lcaInner(me, other, currentThis + 1, currentOther + 1);
        }
    }

    /*
     * Compute the distance to the LCA of this Path with some other Path. @param
     * db - The Database to use to compute the distance. @param other - The Path
     * for which the LCA is to be computed.
     *
     * @returns The distance as an integer.
     */
    public int distanceToLCA(Database db, Path other) {
        Path p = new Path();
        lcaInner(p, other, 0, 0);

        // Check to see if these two types are in the same file
        if (p.size() <= 0) {
            return Integer.MAX_VALUE;
        }
        TypeId t = db.pathsTable.get(p);

        if (t == null) {
            // Distance is too great
            return Integer.MAX_VALUE;
            //System.out.println("Path.java Internal error no type in lca for " + other.image() + " or " + image());
        }
        return (this.size() - p.size()) + (other.size() - p.size());
    }

    /*
     * Compute the LCA TypeId of this Path with some other Path. @param db - The
     * Database to use to compute the distance. @param other - The Path for
     * which the LCA is to be computed.
     *
     * @returns The TypeId of the LCA.
     */
    public TypeId lca(Database db, Path other) {
        Path p = new Path();
        lcaInner(p, other, 0, 0);
        //System.out.println("lca is " + p.image());
        TypeId t = db.pathsTable.get(p);
        if (t == null) {
            System.out.println("Path.java Internal error no type in lca for " + other.image() + " or " + p.image());
        }
        return t;
    }

    /*
     * Shorten the Path by removing the last element.
     */
    public String remove() {
        String str = "";
        if (size() > 0) {
            str = path.pop();
            image.pop();
        }
        return str;
    }

    /*
     * Compute the number of types in the Path
     *
     * @returns The count of the number of types.
     */
    public int size() {
        return path.size();
    }

    /*
     * The image of the path is the entire String?
     */
    public String image() {
        String str = "";
        if (size() > 0) {
            str = image.peek();
        }
        return str;
    }
}
