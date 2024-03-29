package cs7641.assignment2;

public abstract class OptimizationProblem<T> {
    private int numFitnessCalcs = 0;

    public Double fitnessOf(T t) {
        numFitnessCalcs++;
        return fitnessOfImpl(t);
    }

    public int numFitnessCalculations() {
        return numFitnessCalcs;
    }

    public void reset() {
        numFitnessCalcs = 0;
    }

    public abstract Double fitnessOfImpl(T t);
    public abstract T getStartConfiguration();
    public abstract T getRandomConfiguration();
    public abstract Double getTarget();

    public abstract String[] getColumns();
    public abstract String[] getData();

    public boolean isSubsample() {
        return false;
    }

    public double getFitnessOverFullData(T t) {
        return 0.0;
    }
}
