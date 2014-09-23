package cs7641.assignment2;

import cs7641.util.Pair;

import java.util.Iterator;

public class Annealer<T> extends RandomOptimizer<T> {
    private NeighborFunction<T> neighborFunction;
    protected double temp;
    private final double cooling;
    private Pair<T, Double> current;
    protected int lastSubPar;
    private final double startTemp;

    public Annealer(NeighborFunction<T> neighborFunction, double temp, double cooling) {
        this.neighborFunction = neighborFunction;
        this.temp = this.startTemp = temp;
        this.cooling = cooling;
    }

    public void resetImpl() {
        this.temp = startTemp;
        current = null;
        lastSubPar = 0;
    }

    public String toString() {
        return "Annealer(temp=" + startTemp + ",cooling=" + cooling + ",neighbor=" + neighborFunction.getClass().getSimpleName() + ")";
    }

    @Override
    public Pair<T, Double> iterate() {
        if (current == null) {
            T start = problem.getStartConfiguration();
            double fitness = problem.fitnessOf(start);
            current = new Pair(start, fitness);
        }

        Iterator<T> neighbors = neighborFunction.getNeighbors(current.getKey());

        while (neighbors.hasNext()) {
            T neighbor = neighbors.next();
            double fitness = problem.fitnessOf(neighbor);

            if (fitness > current.getValue() || Math.random() < Math.exp((fitness - current.getValue()) / temp)) {
                current = new Pair(neighbor, fitness);
                if (fitness <= current.getValue())
                    lastSubPar = iteration;
                break;
            }
        }

        temp *= cooling;

        return (current);
    }
}
