package cs7641.assignment2.problems;

import cs7641.assignment2.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SingleNumberExperimenter extends Experiment<BitSet> {
    public static void main(String[] args) throws Exception {
        SingleNumberExperimenter exp = new SingleNumberExperimenter();
        exp.run("single-number", 100);
    }

    @Override
    public List<OptimizationProblem<BitSet>> getProblems() {
        List problems = new ArrayList();

        problems.add(new SingleNumber(50, 19));
        problems.add(new SingleNumber(100, 31));

        return problems;
    }

    @Override
    public List<RandomOptimizer<BitSet>> getOptimizers() {
        List optimizers = new ArrayList();

        optimizers.add(new HillClimber(new NeighborFunctions.SingleBitFlipper()));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 0, 0.0));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000000, 0.9));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000000, 0.95));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000000, 0.995));
        optimizers.add(new Evolver(100, 50, new NeighborFunctions.UniformCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(100, 50, new NeighborFunctions.SingleCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Mimicker(200, 5));

        return optimizers;
    }
}
