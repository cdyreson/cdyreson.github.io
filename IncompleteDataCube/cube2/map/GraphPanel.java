import java.util.*;
import java.awt.*;
import java.applet.Applet;


class Point {
  public int x;
  public int y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
    }
  }

class Box {
  Point ul;
  int width;
  int height;
  int depth;
  Color color;

  Box(Point ul, int width, int height, int depth, Color color) {
    this.ul = ul;
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.color = color;
    }

  public void draw(Graphics g) {
    g.setColor(color);
    g.fillRect(ul.x, ul.y, width, depth);
    g.drawRect(ul.x, ul.y, width, depth);
    }
}

class GraphPanel extends Panel implements Runnable {

  GraphPanel() {
    }

  public void run() {
    while (true) {
      relax();
      try {
        Thread.sleep(100);
        } catch (InterruptedException e) {
        break;
        }
      }
    }
 
   synchronized void relax() {
     repaint();
     }
 

    Thread relaxer;
  Color nodeColor = new Color(250, 220, 100);
  int red = 0;
  int green = 0;
  int blue = 0;
  int depth = 1;
    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;


  public void paintNode(Graphics g, FontMetrics fm) {
        Dimension d = size();
        if ((offscreen == null) || (d.width != offscreensize.width) || (d.height != offscreensize.height)) {
            offscreen = createImage(d.width, d.height);
            offscreensize = d;
            offgraphics = offscreen.getGraphics();
            offgraphics.setFont(getFont());
        }
 
        offgraphics.setColor(getBackground());

    if (blue >= 255) 
      if (green >= 255) red++;
      else green++;
    else blue++;
    nodeColor = new Color(red, green, blue);
    Box b = new Box(new Point(10, 10), 20, 10, depth++, nodeColor);
    b.draw(offgraphics);
 
        g.drawImage(offscreen, 0, 0, null);

    /*
    g.setColor(selectColor);
    int w = fm.stringWidth("hiya") + 10;
    int h = fm.getHeight() + 4;
    g.fillRect(x - w/2, y - h / 2, w, h);
    g.setColor(Color.black);
    g.drawRect(x - w/2, y - h / 2, w-1, h-1);
    g.drawString("hiya", x - (w-10)/2, (y - (h-4)/2) + fm.getAscent());
    */
    }
 
    public synchronized void update(Graphics g) {
      FontMetrics fm = g.getFontMetrics();
      paintNode(g, fm);
    }

    public synchronized boolean mouseDown(Event evt, int x, int y) {
      repaint();
      return false;
    }

    public synchronized boolean mouseDrag(Event evt, int x, int y) {
      repaint();
      return false;
    }

    public synchronized boolean mouseUp(Event evt, int x, int y) {
      repaint();
      return false;
    }

    public void start() {
        relaxer = new Thread(this);
        relaxer.start();
    }
    public void stop() {
        relaxer.stop();
    }


}
