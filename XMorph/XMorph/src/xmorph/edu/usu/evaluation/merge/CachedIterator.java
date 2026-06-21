/*
 * An CachedIterator is like an Iterator but extends it with special methods.
 * The CachedIterator caches values in a List and can reset the iteration to
 * the start of the list, or release the cached nodes.
 */
package xmorph.edu.usu.evaluation.merge;

import java.util.*;
import xmorph.edu.usu.graph.Id;

/**
 *
 * @author Curt
 */
public class CachedIterator implements Iterator<Id> {

    Iterator<Id> iter;
    Iterator<Id> iterInterior;
    Id nextOne;
    Id parent;
    int level;
    Map<Id, List<Id>> idMap;

    public CachedIterator(Iterator<Id> iter) {
        this.iter = iter;
        nextOne = null;
        level = 0;
        idMap = new HashMap();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRoot() {
        //System.out.println("CachedIterator set root");
        this.parent = null;
        iterInterior = iter;
    }

    public void setParent(Id p) {
        //System.out.println("CACHEDITERATOR setParent start " + this + " " + p);
        if (level < p.getDLN().getTreeLevel()) {
            //System.out.println("CACHEDITERATOR setParent level less ");
            //this.parent = new Id(p.getDLN().ancestorAtLevel(level-1));
            this.parent = p;
            //parent = new Id(parent.getDLN().getAncestorId(parent.getDLN().getTreeLevel() - level));
            parent = new Id(parent.getDLN().getAncestorId(level));
            //System.out.println("CACHEDITERATOR  new Parent " + parent);
            /*
             do {
             parent = new Id(parent.getDLN().getParentId());  
             } while (level < parent.getDLN().getTreeLevel());
             */
        } else {
            //System.out.println("CACHEDITERATOR setParent level more ");
            this.parent = p;
        }
        //System.out.println("CachedIterator " + level + " " + p.getDLN() + " " + parent.getDLN());
        if (idMap.containsKey(parent)) {
            //System.out.println("CachedIterator contains key");
            iterInterior = idMap.get(parent).iterator();
        } else {
            Id id;
            if (nextOne != null) {
                //System.out.println("CachedIterator next One " + nextOne.getDLN());
                id = nextOne;
                nextOne = null;
            } else {
                if (iter.hasNext()) {
                    id = iter.next();
                    nextOne = id;
                    //System.out.println("CachedIterator has next " + id.getDLN());
                } else {
                    //System.out.println("CachedIterator no next ");
                    idMap.put(parent, new ArrayList());
                    return;
                }
            }
            //Id nextParent = nextOne.getAncestorAtLevel(level);
            //System.out.println("CachedIterator xxx " + id.getDLN() + " " + parent.getDLN() + " " + level);
            if (!id.getDLN().hasCommonLCA(parent.getDLN(), level)) {
                // Is the parent before this id?  If so, then can skip it.
                nextOne = null;
                // Advance id to get below parent
                while (id.getDLN().before(parent.getDLN(), true)) {

                    if (iter.hasNext()) {
                        //System.out.println("CachedIterator skipping " + id.getDLN() + " " + parent.getDLN());
                        id = iter.next();
                        //nextOne = id;
                    } else {
                        //System.out.println("CachedIterator adding " + id.getDLN() + " " + parent.getDLN());
                        idMap.put(parent, new ArrayList());
                        return;
                    }
                }

            }
            List<Id> idList = new ArrayList();
            while (id.getDLN().hasCommonLCA(parent.getDLN(), level)) {
                //System.out.println("CachedIterator add " + id.getDLN() + " " + parent.getDLN() + " " + level);
                idList.add(id);
                if (iter.hasNext()) {
                    id = iter.next();
                    nextOne = id;
                } else {
                    nextOne = null;
                    break;
                }
            }
            //System.out.println("CachedIterator no " + id.getDLN() + " " + parent.getDLN());
            if (nextOne == null) {
                nextOne = id;
            }
            idMap.put(parent, idList);
            iterInterior = idList.iterator();
        }
    }

    /*
     * Check to see if the cached list contains values, or the underlying feed
     * still needs values.
     */
    @Override
    public boolean hasNext() {
        if (iterInterior == null) {
            return false;
        }
        return iterInterior.hasNext();
    }

    public void freeCache() {
        //System.out.println("CachedIterator freeCache");
        idMap = new HashMap();
    }

    public void reset() {
        //System.out.println("CachedIterator reset ");
    }

    @Override
    public Id next() {
        if (iterInterior != null) {
            return iterInterior.next();
        }
        return null; // Never gets here?
    }

    @Override
    public void remove() {
    }
}
