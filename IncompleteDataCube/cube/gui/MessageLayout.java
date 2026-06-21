package cube.gui;

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
    Panel panel = new Panel();
    panel.setLayout(new BorderLayout());
    panel.add("Center", text);
 

    quitPanel = new Panel();
    quitPanel.setLayout(new GridLayout()); 
    quitButton = new Button("OK");
    setLayout(new GridBagLayout());
    //add("North", text);
    //constrain(this,text,0,1,1,1,GridBagConstraints.NONE,
    constrain(this,panel,0,0,1,1,GridBagConstraints.NONE,
              GridBagConstraints.NORTH,1.0,1.0,0,0,0,0);

    quitPanel.add("West", new Label(" "));
    quitPanel.add("Center", quitButton);
    quitPanel.add("East", new Label(" "));
    //add("South", quitPanel);
    constrain(this,quitPanel,0,1,3,1,GridBagConstraints.NONE,
              GridBagConstraints.SOUTH,0.0,1.0,10,10,5,5);

    validate();
  }

  /**
   * Constrain a grid
   **/
  private void constrain(Container container,Component component,
                        int grid_x,int grid_y,int grid_width,int grid_height,
                        int fill,int anchor,double weight_x,double weight_y,
                        int top,int left,int bottom,int right) {
 
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = grid_x; c.gridy=grid_y;
    c.gridwidth = grid_width; c.gridheight=grid_height;
    c.fill = fill; c.anchor=anchor;
    c.weightx = weight_x; c.weighty=weight_y;
 
    if (top+bottom+right+left > 0)
      c.insets = new Insets(top,left,bottom,right);
 
    ((GridBagLayout)container.getLayout()).setConstraints(component, c);
 
    container.add(component);
  }
 
 
  /**
   * Constrain a grid
   **/
  private void constrain(Container container,Component component,
                        int grid_x,int grid_y,int grid_width,int grid_height) {
      
    constrain(container,component,grid_x,grid_y,
              grid_width,grid_height,GridBagConstraints.NONE,
              GridBagConstraints.NORTHWEST,0.0,0.0,0,0,0,0) ;
  }
      
 
  /**
   * Constrain a grid
   **/
  private void constrain(Container container,Component component,
                        int grid_x,int grid_y,int grid_width,int grid_height,
                        int top,int left,int bottom,int right) {
      
    constrain(container,component,grid_x,grid_y,
              grid_width,grid_height,GridBagConstraints.NONE,
              GridBagConstraints.NORTHWEST,
              0.0,0.0,top,bottom,left,right) ;
  }
 

}
