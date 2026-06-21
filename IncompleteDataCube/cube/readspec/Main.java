import java.io.*;
import cube.globals.*;
import cube.configure.*;
import cube.database.*;
import cube.tools.*;

/**
* Parse the specification files and create the tables and flex file
* needed to parse the actual records.
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
class Main {
  public static int whichDimension;
  public static String fileName;
  public static Globals global;
  public static LexFileWriter lexFile;
  public static CupFileWriter cupFile;
  public static void main(String argv[])
    {
      global = new Globals();
      cupFile = new CupFileWriter();
      // Read the specs
      for (int i = 0; i < Constants.dimensions; i++) {
        whichDimension = i;
        fileName = Constants.cubeInputPath + Constants.dimensionNames[i];
        lexFile = new LexFileWriter(Constants.cubeHome + "parser/" + 
                                    Constants.dimensionNames[i] + ".flex");
        lexFile.header();
        parser parse_obj = new parser();
        try {
	  parse_obj.parse();
          } catch (java.lang.Exception ex) {
              //System.out.println("End of file?");
          }
        lexFile.tail();
        }

      // Ok now read the filters
      whichDimension = 0;
      fileName = Constants.cubeInputPath + Constants.filterFileName;
      lexFile = new LexFileWriter(Constants.cubeHome + "parser/" + 
                                    Constants.filterFileName + ".flex");
      parser parse_obj = new parser();
      try {
	parse_obj.parse();
        } catch (java.lang.Exception ex) {
            System.out.println("End of file?" + ex);
        }
      lexFile.tail();
      global.save();
      Convert.save();
      System.out.println("alldone!");
    }
}
