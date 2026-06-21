import java.io.*;
import java_cup.runtime.*;
import cube.tools.*;
import cube.configure.*;

/**
* A simple lexical anaylzer class used by the read-specification parser.
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
  * Create the lexical anaylzer, read input from a specific file
  * @param fileName - Name of the file to read from
  **/
  public static void init(String fileName) {
    //s = new scanner(System.in);
    try { 
      s = new scanner(new FileInputStream(fileName));
      } catch (IOException ex) { 
        Internal.Error("unable to open " + fileName);
      }
    }

  /**
  * Get the next token from the file.  On end of file, return the 
  * end of file symbol.
  **/
  public static token next_token() {
    token tok;
    try {
      tok = s.next_token();
      //System.out.println(tok);
      if (tok.sym == -1) return new token(sym.EOF);
      else return tok;
      }
    catch (IOException e) {return new token(sym.EOF);}
    catch (NullPointerException e) {/* end-of-file */ return new token(sym.EOF);};
    }
}
