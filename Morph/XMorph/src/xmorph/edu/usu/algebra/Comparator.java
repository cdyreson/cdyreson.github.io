/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmorph.edu.usu.algebra;

import xmorph.edu.usu.graph.Node;

/**
 *
 * @author Kiran
 */
public class Comparator extends Operator {

    String stringValue;
    boolean integer = false;
    int op, intValue;

    public Comparator(int op, String stringValue) {
        this.op = op;                       // if comparing with string
        this.stringValue = stringValue;
    }

    public Comparator(int op, int intValue) {
        this.op = op;                    // if comparing with an integer
        this.intValue = intValue;
        integer = true;          // setting integer flag
    }
    
    public String getStringValue() {
        return stringValue;
    }
    
    /* Evaluate the comparator on a Node */
    public boolean evaluate(Node n) {
        switch (op) {
            case Where.EQ:
                //System.out.println("here " + n.getValue().contentEquals(stringValue));
                return n.getValue().contentEquals(stringValue);
            default :
                return false;
        }
    }

}