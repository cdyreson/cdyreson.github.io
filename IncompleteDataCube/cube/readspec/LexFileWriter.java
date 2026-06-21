import java.io.*;
import java.util.*;
import cube.database.*;
import cube.tools.*;

/**
* A class to write the Lexical Analyzer (flex) file to parse the input
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
class LexFileWriter {
  private Writer fout;
  private String fileName;

  /**
  * Open the file to contain the flex source
  **/
  public LexFileWriter(String s) {
    fileName = s;
    try {
      fout = new FileWriter(s);
      } catch (IOException e) {
        Internal.Error("Opening file " + s); 
      }
    }

  /**
  * Emit a rule to recognize a token
  **/
  public void emitTokenRule(String regex, String unit) {
    try {
      fout.write(regex + " { printf(\"" + (new Id(unit)).image() + " \"); } REJECT\n");
      } catch (IOException e) {
        Internal.Error("Writing file " + fileName); 
      }
    }

  /**
  * Generate a canned header for the flex file
  **/
  public void header() {
    try {
      fout.write("%{\n");
      fout.write("/* flex source for lexical analyzer, automatically generated */\n");
      fout.write("%}\n");
      fout.write("%%\n");
      } catch (IOException e) {
        Internal.Error("Writing file " + fileName); 
      }
    }

  /**
  * Generate a canned tail for the flex file
  **/
  public void tail() {
    try {
      fout.write("\\n {printf(\"0\\n\");}\n");
      fout.write(". {}\n");
      fout.write("%%\n");
      fout.write("yywrap()\n");
      fout.write("{\n");
      fout.write("return(1);\n");
      fout.write("}\n");
      fout.write("#undef input()\n");
      fout.write("#define input() mlex_input()\n");
      fout.write("#undef unput()\n");
      fout.write("#define unput(c) mlex_unput(c)\n");
      fout.close();
      } catch (IOException e) {
        Internal.Error("Closing file " + fileName); 
      }
    }
}  

