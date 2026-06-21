package cube.database;

import java.io.*;
import cube.*;

/**
*  The Database class encapsulates a database.
*  The database consists of tables of persistent associative arrays,
*  so it is a really very simple.
*  In our implementation the database is a directory, and the tables
*  within it are Dbm files. 
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
* @see cube.database.Dbm
* @see cube.database.Table
* @see cube.database.Tuple
* @author Curtis Dyreson and Jason Pountney
**/
public class Database {
  /**
  * The directory for the database.
  **/
  private String name;

  /**
  * This constructor just stores the name of the database as 
  * the directory.  It does not open any files,
  * nor check to make sure the directory exists.
  * @param name - The name of the database (a directory name).
  **/
  public Database(String name) {
    this.name = name;
    }

  /**
  * Creates a database table.
  * A table is a database relation.
  * @param name  The name of the table (Dbm file).
  **/
  public Table createTable(String t) {
    String tablename = name + "/" + t;
    Table table = new Table(name + "/" + t);
    return table;
    }

  /**
  * Creates a new database table.
  * A table is a database relation.
  * @param name  The name of the table (Dbm file).
  */
  public Table createNewTable(String t) {
    String tablename = name + "/" + t;
    Table table = new Table(name + "/" + t);
    table.clear();
    return table;
    }
}
