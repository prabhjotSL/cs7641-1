package cs7641.assignment2;

import javafx.util.Pair;

public abstract class RandomOptimizer<T> {
    protected OptimizationProblem<T> problem;
    protected Pair<T, Double> best;
    protected int iteration = 0;

    public RandomOptimizer(OptimizationProblem<T> problem) {
        this.problem = problem;
    }

    public abstract Pair<T, Double> iterate();

    public void train(int iterations) {

        for (iteration = 0; iteration < iterations; iteration++) {
            best = iterate();

            if (problem.getTarget() != null && best.getValue() >= problem.getTarget())
                break;
        }

        System.out.println(best.getValue() + "(" + problem.numFitnessCalculations() + ")");
    }
}
