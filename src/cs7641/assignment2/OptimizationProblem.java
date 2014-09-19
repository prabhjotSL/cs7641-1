package cs7641.assignment2;

public interface OptimizationProblem<T> {
    public Double fitnessOf(T t);
    public T getStartConfiguration();
    public T getRandomConfiguration();
    public int numFitnessCalculations();
    public Double getTarget();
}
