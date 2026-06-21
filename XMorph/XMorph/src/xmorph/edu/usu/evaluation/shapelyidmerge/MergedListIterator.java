/*
 * This is the critical class. What we want to do is create one list that we
 * can traverse in sorted order from the merging of iterators of several
 * lists.
 */
package xmorph.edu.usu.evaluation.shapelyidmerge;

import java.util.*;
//import xmorph.edu.usu.graph.Id;

/**
 *
 * @author Curt
 */
public class MergedListIterator implements Iterator<ShapelyId> {

    SortedMap<ShapelyId, Iterator<ShapelyId>> feeds;
    List<Iterator<ShapelyId>> feedList;
    boolean initialized = false;

    public MergedListIterator() {
        feeds = new TreeMap();
        feedList = new ArrayList();
        initialized = false;
    }

    public void addFeed(Iterator<ShapelyId> feed) {
        //System.out.println("Adding feed");
        feedList.add(feed);
        //if (feed.hasNext()) {
        //   feeds.put(feed.next(), feed);
        //}
    }

    @Override
    public boolean hasNext() {
        //System.out.println("MergedListIterator hasnext");
        if (!initialized) {
            //reset();
            initialized = true;
        }
        return !feeds.isEmpty();
    }

    public void reset() {
        for (Iterator<ShapelyId> iter : feedList) {
            if (iter.hasNext()) {
                feeds.put(iter.next(), iter);
            }
        }
    }

    @Override
    public ShapelyId next() {
        //System.out.println("MergedListIterator next");
        if (!feeds.isEmpty()) {
            ShapelyId firstKey = feeds.firstKey();
            Iterator<ShapelyId> feed = feeds.get(firstKey);
            if (feed.hasNext()) {
                ShapelyId id = feed.next();
                feeds.put(id, feed);
            }
            feeds.remove(firstKey);
            return firstKey;
        }
        System.err.println("bad feed in MergedListShapelyIdIterator");
        return null;  // Should never get here!
    }

    @Override
    public void remove() {
    }
}
