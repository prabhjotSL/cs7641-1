package cs7641.assignment2.problems;

import cs7641.assignment2.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SingleNumberExperimenter extends Experiment<BitSet> {
    public static void main(String[] args) throws Exception {
        SingleNumberExperimenter exp = new SingleNumberExperimenter();
        exp.run("single-number", 100, Integer.MAX_VALUE);
    }

    @Override
    public List<OptimizationProblem<BitSet>> getProblems() {
        List problems = new ArrayList();

        problems.add(new SingleNumber(1000, 51));
        problems.add(new SingleNumber(50, 19));
        problems.add(new SingleNumber(100, 31));

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
