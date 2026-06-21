import java.util.*;
import java.awt.*;
import java.applet.Applet;

public class Intro extends Applet {
    GraphPanel panel;

    public void init() {
	setLayout(new BorderLayout());

	panel = new GraphPanel();
	add("Center", panel);
	Panel p = new Panel();
	add("South", p);
	p.add(new Button("Scramble"));
	p.add(new Button("Shake"));
	p.add(new Checkbox("Stress"));
	p.add(new Checkbox("Random"));

	Dimension d = size();
    }

    public void update(Graphics g) {
      panel.update(g);
    }
    public void start() {
      panel.start();
    }
    public void stop() {
      panel.stop();
    }
    public boolean action(Event evt, Object arg) {
      return true;
    }
}
