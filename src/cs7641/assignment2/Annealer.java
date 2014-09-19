package cs7641.assignment2;

import javafx.util.Pair;

import java.util.List;

public class Annealer<T> extends RandomOptimizer<T> {
    private NeighborFunction<T> neighborFunction;
    private double temp;
    private final double cooling;
    private Pair<T, Double> current;

    public Annealer(OptimizationProblem<T> problem, NeighborFunction<T> neighborFunction, double temp, double cooling) {
        super(problem);

        this.neighborFunction = neighborFunction;
        this.temp = temp;
        this.cooling = cooling;
    }

    @Override
    public Pair<T, Double> iterate() {
        if (current == null) {
            T start = problem.getStartConfiguration();
            double fitness = problem.fitnessOf(start);
            current = new Pair(start, fitness);
            best = current;
        }

        List<T> neighbors = neighborFunction.getNeighbors(current.getKey());

        for (T neighbor : neighbors) {
            double fitness = problem.fitnessOf(neighbor);

            if (fitness > current.getValue() || Math.random() < Math.exp((fitness - current.getValue()) / temp)) {
                current = new Pair(neighbor, fitness);
                break;
            }
        }

        temp *= cooling;

        //if (current.getValue() > best.getValue())
        //    System.out.println(iteration + ": " + current.getValue());

        return (current.getValue() > best.getValue()) ? current : best;
    }
}
