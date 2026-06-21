package cube.applet;

import java.awt.*;
import java.util.*;
import cube.globals.*;
import cube.configure.*;
//import cube.database.*;
//import cube.tools.*;
//import cube.persistent.*;

 /**
 * This class is the Layout box for a message.
 * <P>
 * For more information on the cube see the cube
 * <A HREF="cube.Overview.html">Overview</A>.
 * <BR>
 * Copyright &copy 1997 Curtis E. Dyreson. All rights reserved.
 * Please be aware of the
 * <A HREF="cube.Licence.html">Licence</A>
 * and
 * <A HREF="cube.Version.html">Version</A>.
 * @author Curtis Dyreson and Jason Pountney
 **/
public class MessageLayout extends Frame {
  protected Button quitButton;
  private Panel quitPanel;

  /**
   * Build a new message, with the given string and title
   **/
  public MessageLayout(String title, String message) {
    super(title);  //Set name of Frame;
    TextArea text = new TextArea(message,20,100);
 

    quitPanel = new Panel();
    quitPanel.setLayout(new FlowLayout()); 
    quitButton = new Button("OK");
    quitPanel.add(quitButton);
    //quitPanel.add("West", new Label(" "));
    //quitPanel.add("Center", quitButton);
    //quitPanel.add("East", new Label(" "));

    //Panel panel = new Panel();
    setLayout(new BorderLayout());
    add("Center", text);
    add("South", quitPanel);
    validate();
  }

}
