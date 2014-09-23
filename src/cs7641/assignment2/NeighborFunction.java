package cs7641.assignment2;

import java.util.Iterator;

public interface NeighborFunction<T> {
    public Iterator<T> getNeighbors(T t);

}
