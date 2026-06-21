package xmorph.edu.usu.algebra;

/**
 * Test the algebra with a canned query
 * @author Curtis
 */

import xmorph.edu.usu.shape.ShapeNode;
import xmorph.edu.usu.database.memory.Database;

public class Main {

    public static void main(String argv[]) {
        try {
            //System.out.println(argv[0] + " is 1 " + argv[1] + " is two ");
            String fileName = argv[1];
            Database db = new Database();
            System.out.println("Opened " + fileName);

            // after parsing evalute it   
            /*
            algebra.Type pub = new algebra.Type("publisher");

            algebra.Type title = new algebra.Type("title");
            algebra.Group groupedPub = new algebra.Group(pub);

            algebra.Type book = new algebra.Type("book");
            algebra.Type author = new algebra.Type("author");
            algebra.Group groupedAuthor = new algebra.Group(author);


            algebra.LCAJoin pubAuthor = new algebra.LCAJoin(groupedAuthor, title);
            //algebra.LCAJoin top = new algebra.LCAJoin(groupedPub, pubAuthor);
            algebra.LCAJoin titleAuthor = new algebra.LCAJoin(title, author);
            //algebra.LCAJoin top = new algebra.LCAJoin(titleAuthor, pub);
            algebra.Exp top = new algebra.LCAJoin(pub, author);
             */
            xmorph.edu.usu.algebra.Type title = new xmorph.edu.usu.algebra.Type("title");
            xmorph.edu.usu.algebra.Type author = new xmorph.edu.usu.algebra.Type("author");
            xmorph.edu.usu.algebra.Type publisher = new xmorph.edu.usu.algebra.Type("publisher");
            xmorph.edu.usu.algebra.LCAJoin publisherAuthor = new xmorph.edu.usu.algebra.LCAJoin(author, publisher);
            xmorph.edu.usu.algebra.LCAJoin top = new xmorph.edu.usu.algebra.LCAJoin(publisherAuthor, title);
            xmorph.edu.usu.algebra.Query q = new Query(top);
            //, d.getMin(), d.getMax()
            ShapeNode shapeNode = q.evaluate(db, db.shapesTable.get(fileName));
            //algebra.LCAJoin top = new algebra.LCAJoin(title, publisherAuthor);
            //algebra.Exp top = new algebra.Type("title");

            //System.out.println("size is " + types.size());

            /*
            algebra.Type pub = new algebra.Type("publisher");
            algebra.Type title = new algebra.Type("title");
            algebra.LCAJoin top = new algebra.LCAJoin(title, pub);
             */
            
            /*
            List<TypeId> foo = top.typeOf();
            Iterator<TypeId> fooIterator = foo.iterator();
            while (fooIterator.hasNext()) {
                TypeId t = fooIterator.next();
                //System.out.println("Type to evaluate is " + t.image());

                //int x = 0;
                top.chooseType(t);
                top.setBoundary(d.getMin(), d.getMax());
                //Node n = top.evaluate();
                //while (n != null) {
                while (top.hasNextInBoundary()) {
                    String s = top.nextInBoundary().format();
                    //String s = n.format();
                    if (s != null) {
                        //x++;
                        System.out.println(s);
                    }
                    //n = top.evaluate();
                }

                //System.out.println(x + "");
            }
         */
        /*
        for (String s = Node.formatPartials(); s != null; s = Node.formatPartials()) {
        System.out.println(s);
        }
         */

        // Set up output stream
        //out = new OutputStreamWriter (System.out, "UTF8");

        // Parse the input
        //SAXParser saxParser = factory.newSAXParser();
        //saxParser.parse( new File(fileName), new XMLDataStore());

        } catch (Exception t) {
            t.printStackTrace();
        }
        System.exit(0);
    }
}
