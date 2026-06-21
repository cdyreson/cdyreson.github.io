package xmorph.edu.usu.algebra;

import java.util.*;

/**
 * A Data node stores the name of the XML file to use.
 * 
 * @author Curtis
 */
public class Data extends Operator {
    String fileName;

    public Data(String s) {
        fileName = s;
    }
}


