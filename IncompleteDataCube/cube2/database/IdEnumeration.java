package cube.database;

import java.util.*;

/**
* The IdEnumeration class creates an Enumeration on an array of Ids.
* It creates an enumeration for an IdList.
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
* @see cube.database.IdList
* @author Curtis Dyreson
**/
public class IdEnumeration implements Enumeration {
  private Id[] ids = null;
  private int index = -1;

  /**
  * Construct an IdEnumeration from an array of Ids.
  * @param p - array of Ids
  **/
  public IdEnumeration(Id[] p) {
    ids = p;
    index = 0;
    }
 
  /**
  * Retrieve the next Id
  **/
  public Object nextElement() {
    //if (index >= ids.length) {throw error};
    return (Object) ids[index++];
    }
 
  /**
  * Does this enumeration have more elements?
  **/
  public boolean hasMoreElements() {
    return (index >= 0) && (index < ids.length);
    }
}
