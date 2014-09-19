package cs7641.assignment2;

import javafx.util.Pair;

import java.util.List;

public class HillClimber<T> extends RandomOptimizer<T> {
    private NeighborFunction<T> neighborFunction;

    public HillClimber(OptimizationProblem<T> problem, NeighborFunction<T> neighborFunction) {
        super(problem);

        this.neighborFunction = neighborFunction;
    }

    @Override
    public Pair<T, Double> iterate() {
        if (best == null) {
            T start = problem.getStartConfiguration();
            double fitness = problem.fitnessOf(start);
            best = new Pair(start, fitness);
        }

        List<T> neighbors = neighborFunction.getNeighbors(best.getKey());

        for (T neighbor : neighbors) {
            double fitness = problem.fitnessOf(neighbor);

            if (fitness > best.getValue()) {
               // System.out.println(iteration + ": " + fitness);
                return new Pair(neighbor, fitness);
            }
        }
        return best;
    }
}
