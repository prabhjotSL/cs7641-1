package cs7641.assignment2;


import cs7641.util.Pair;

public abstract class RandomOptimizer<T> {
    protected OptimizationProblem<T> problem;
    private Pair<T, Double> best;
    protected int iteration = 0;
    private int bestNumCalcs = 0;

    public void setProblem(OptimizationProblem<T> problem) {
        this.problem = problem;

        reset();
    }

    public void reset() {
        best = null;
        iteration = 0;
        bestNumCalcs = 0;

        resetImpl();
    }

    public int getBestNumCalcs() {
        return bestNumCalcs;
    }

    public abstract void resetImpl();

    public abstract Pair<T, Double> iterate();

    public void train(int maxStale) {
        int stale = 0;
        Double staleScore = null;

        while (true) {
            iteration++;

            boolean canQuit = true;

            if (this instanceof Annealer) {
                Annealer ann = (Annealer)this;
                if (iteration - ann.lastSubPar < 100)
                    canQuit = false;
            }

            if (canQuit && stale == maxStale)
                break;

            Pair<T, Double> current = iterate();

            // Indicates the optimizer has exhausted its search
            if (current == null)
                break;

            if (best != null && current.getValue() <= best.getValue()) {
                if (staleScore == null)
                    staleScore = current.getValue();

                if (current.getValue().equals(staleScore)) {
                    stale++;
                } else {
                    staleScore = current.getValue();
                    stale = 0;
                }

                continue;
            }

            stale = 0;
            staleScore = null;
            best = current;
            bestNumCalcs = problem.numFitnessCalculations();

            if (problem.getTarget() != null && best.getValue() >= problem.getTarget()) {
                break;
            }
        }
    }

    public Pair<T, Double> getBest() {
        return best;
    }
}
