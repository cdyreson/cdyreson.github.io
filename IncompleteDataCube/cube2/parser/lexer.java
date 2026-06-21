import java.io.*;

/**
* A simple lexical analyzer, all it does is read numbers
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
* @author Curtis Dyreson 
**/
public class lexer {
  private static scanner s;

  /**
  * Initialize the lexical analyzer to read from stdin
  **/
  public static void init() {
    s = new scanner(System.in);
    }

  /**
  * Get the next token, return -2 on end-of-file
  **/
  public static int next_token() {
    Integer tok;
    try {
      tok = s.next_token();
      return tok.intValue();
      } 
      catch (IOException e) {return -2;}
      catch (NullPointerException e) {/* end-of-file */ return -2;};
    }
}
