package cube.tools;

import java.io.*;
import java.util.*;

/**
* A class used to handle internal errors.  
* <P>
* For more information on the cube see the cube
* <A HREF="cube.Overview.html">Overview</A>.
* <BR>
* Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
* Please be aware of the 
* <A HREF="cube.Licence.html">Licence</A>
* and
* <A HREF="cube.Version.html">Version</A>.
*
* @author Curtis Dyreson and Jason Pountney
**/
public class Internal {

  /**
  * Print an error message and then continue.  
  **/
  public static void Error(String s) {
    System.out.println("\nInternal Error:" + s);
    System.err.println("\nInternal Error:" + s);
    System.out.flush();
    System.err.flush();
    //System.exit(-1);
    }
}
