package xmorph.edu.usu.database.tables;

import xmorph.edu.usu.graph.Text;
import xmorph.edu.usu.graph.Id;
import java.util.*;

/**
 * Maps an Id to the corresponding Text node.
 * @author Curtis
 */
public class TextTable {

    private Map<Id, Text> tab;

    public TextTable(Map<Id, Text> tab) {
        this.tab = tab;
    }

    public void put(Text t) {
        tab.put(t.getId(), t);
    }

    public Text get(Id t) {
        return tab.get(t);
    }
}
