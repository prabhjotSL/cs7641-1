package cs7641.assignment2;

import java.util.List;

public interface NeighborFunction<T> {
    public List<T> getNeighbors(T t);

}
