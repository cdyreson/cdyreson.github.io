/*
 * This is the critical class. What we want to do is create one list that we
 * can traverse in sorted order from the merging of iterators of several
 * lists.
 */
package xmorph.edu.usu.evaluation.merge;

import java.util.*;
import xmorph.edu.usu.graph.Id;

/**
 *
 * @author Curt
 */
public class MergedListIterator implements Iterator<Id> {

    SortedMap<Id, Iterator<Id>> feeds;
    List<Iterator<Id>> feedList;
    boolean initialized = false;

    public MergedListIterator() {
        feeds = new TreeMap();
        feedList = new ArrayList();
        initialized = false;
    }

    public void addFeed(Iterator<Id> feed) {
        //System.out.println("Adding feed");
        feedList.add(feed);
        //if (feed.hasNext()) {
        //   feeds.put(feed.next(), feed);
        //}
    }

    public void addFeed(CachedIterator feed) {
        //System.out.println("Adding feed");
        feedList.add((Iterator<Id>) feed);
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
        for (Iterator<Id> iter : feedList) {
            if (iter.hasNext()) {
                feeds.put(iter.next(), iter);
            }
        }
    }

    @Override
    public Id next() {
        //System.out.println("MergedListIterator next");
        if (!feeds.isEmpty()) {
            Id firstKey = feeds.firstKey();
            Iterator<Id> feed = feeds.get(firstKey);
            if (feed.hasNext()) {
                Id id = feed.next();
                feeds.put(id, feed);
            }
            feeds.remove(firstKey);
            return firstKey;
        }
        System.err.println("bad feed in MergedListIterator");
        return null;  // Should never get here!
    }

    @Override
    public void remove() {
    }
}
