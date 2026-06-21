package cube.persistent;

import cube.database.*;
import java.io.*;

class Main {
  public static void main(String argv[]) {
    Database db = new Database("../../dbs");
    PersistentGraph graph = new PersistentGraph(db, "edges");
    graph.addEdge(new Id(1), new Id(2));
    graph.addEdge(new Id(2), new Id(3));
    graph.addEdge(new Id(1), new Id(4));
    IdSet r = graph.reachableSet(new Id(1));
    System.out.println(r.numberOfElements());
    r = graph.reachableSet(new Id(2));
    System.out.println(r.numberOfElements());
    r = graph.reachableSet(new Id(3));
    System.out.println(r.numberOfElements());
    r = graph.reachableSet(new Id(4));
    System.out.println(r.numberOfElements());
    }
}
