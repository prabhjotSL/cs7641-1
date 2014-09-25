package cs7641.assignment2.problems;

import cs7641.assignment2.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class FourPeaksExperimenter extends Experiment<BitSet> {
    public static void main(String[] args) throws Exception {
        FourPeaksExperimenter exp = new FourPeaksExperimenter();
        exp.run("four-peaks", 100, Integer.MAX_VALUE);
    }

    @Override
    public List<OptimizationProblem<BitSet>> getProblems() {
        List problems = new ArrayList();

        problems.add(new FourPeaks(10, 1));
        problems.add(new FourPeaks(20, 2));
        problems.add(new FourPeaks(30, 3));
        problems.add(new FourPeaks(40, 4));
        problems.add(new FourPeaks(50, 5));
        problems.add(new FourPeaks(60, 6));
        problems.add(new FourPeaks(70, 7));
        problems.add(new FourPeaks(80, 8));

        return problems;
    }

    @Override
    public List<RandomOptimizer<BitSet>> getOptimizers() {
        List optimizers = new ArrayList();

        optimizers.add(new HillClimber(new NeighborFunctions.SingleBitFlipper()));

        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.1));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.2));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.3));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.4));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.5));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.6));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.7));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.8));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.9));
        optimizers.add(new Annealer(new NeighborFunctions.SingleBitFlipper(), 1000, 0.99));

        optimizers.add(new Evolver(100, 50, new NeighborFunctions.UniformCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(100, 50, new NeighborFunctions.SingleCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(200, 50, new NeighborFunctions.UniformCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(200, 50, new NeighborFunctions.SingleCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(200, 100, new NeighborFunctions.SingleCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(200, 100, new NeighborFunctions.UniformCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.UniformCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.SingleCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(400, 200, new NeighborFunctions.UniformCrossover(), new NeighborFunctions.SingleBitMutator(), 10));
        optimizers.add(new Evolver(400, 200, new NeighborFunctions.SingleCrossover(), new NeighborFunctions.SingleBitMutator(), 10));

        optimizers.add(new Mimicker(100, 2));
        optimizers.add(new Mimicker(100, 3));
        optimizers.add(new Mimicker(100, 4));
        optimizers.add(new Mimicker(100, 5));
        optimizers.add(new Mimicker(200, 2));
        optimizers.add(new Mimicker(200, 3));
        optimizers.add(new Mimicker(200, 4));
        optimizers.add(new Mimicker(200, 5));
        optimizers.add(new Mimicker(200, 2));
        optimizers.add(new Mimicker(200, 3));
        optimizers.add(new Mimicker(200, 4));
        optimizers.add(new Mimicker(200, 5));
        optimizers.add(new Mimicker(200, 10));

        return optimizers;
    }
}
