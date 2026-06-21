package cube.gui;

import java.awt.*;
import cube.globals.*;
import cube.configure.*;

 /**
 * This class is the handler for the message layout box.
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
public class MessageHandler extends MessageLayout {

  /**
   * Create a new handler
   **/
  public MessageHandler(String title, String message) {
    super(title, message);
  }

  /**
   * Handle an event
   **/
  public boolean handleEvent(Event event) {
    switch(event.id) {
      case Event.ACTION_EVENT:

        ///////////////////////////
        ///       button         //
        ///////////////////////////

        if (event.target == quitButton) {
          //System.out.println("OK");
          super.dispose();
          }
      break;

      case Event.WINDOW_DESTROY:
        System.out.println("Bye...");
        System.exit(0);

      case Event.WINDOW_ICONIFY:
      case Event.WINDOW_DEICONIFY:
      case Event.WINDOW_MOVED:
        return false;

      case Event.MOUSE_DOWN:
      case Event.MOUSE_UP:
      case Event.MOUSE_DRAG:
        return false;

      case Event.KEY_PRESS:
      case Event.KEY_ACTION:
      case Event.KEY_RELEASE:
      case Event.KEY_ACTION_RELEASE:
        return false;

      case Event.GOT_FOCUS:
      case Event.LOST_FOCUS:
      case Event.MOUSE_ENTER:
      case Event.MOUSE_EXIT:
      case Event.MOUSE_MOVE:
        return false;

      default:
        System.out.println("Missed one !!");
      break;
    }
    return true;
  }

}
