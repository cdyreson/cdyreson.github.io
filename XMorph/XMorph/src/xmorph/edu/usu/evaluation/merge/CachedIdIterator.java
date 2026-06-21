/*
 * An CachedListIterator is like an Iterator but extends it with special methods.
 * The CachedListIterator caches values in a List and can reset the iteration to
 * the start of the list, or release the cached nodes.
 */
package xmorph.edu.usu.evaluation.merge;

import java.util.*;
import xmorph.edu.usu.graph.Id;

/**
 *
 * @author Curt
 */
public class CachedIdIterator extends MergedListIterator implements Iterator<Id> {

    List<Id> cached;
    int current;

    public CachedIdIterator() {
        super();
        cached = new ArrayList();
        current = 0;
    }


    /* Check to see if the cached list contains values,
     * or the underlying feed still needs values.
     */
    @Override
    public boolean hasNext() {
        if (cached.isEmpty() || current >= cached.size()) {
            return super.hasNext();
        }
        return current < cached.size();
    }

    public void freeCache() {
        cached = new ArrayList();
        current = 0;
    }
    
    public void resetIterator() {
        current = 0;
    }
    
    @Override
    public Id next() {
        /* If empty then need to populate */
        if (cached.isEmpty() || current >= cached.size()) {
            if (super.hasNext()) {
                Id id = super.next();
                //System.out.println("adding " + current + " " + id.toString());
                cached.add(id);
                //current++;
            }
        }
        if (current < cached.size()) {
            return cached.get(current++);
        }
        return null; // Should we get here?
    }

    @Override
    public void remove() {
    }
}
