package cs7641.assignment2.problems;

import cs7641.assignment2.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class MaximumCutExperimenter extends Experiment<BitSet> {

    public static void main(String[] args) throws Exception {
        MaximumCutExperimenter exp = new MaximumCutExperimenter();
        exp.run("max-cut", Integer.MAX_VALUE, 100);
    }

    @Override
    public List<OptimizationProblem<BitSet>> getProblems() {
        List problems = new ArrayList();

        Random r = new Random(31);

        for (int m = 2; m < 3; m++) {
            int nodes = m * 40;
            int magnitude = 20;

            Double[][] adjacency = new Double[nodes][nodes];

            // ~50% connected
            for (int i = 0; i < nodes; i++) {
                for (int j = i + 1; j < nodes; j++) {
                    if (r.nextBoolean()) {
                        adjacency[i][j] = adjacency[j][i] = (double) r.nextInt(magnitude) + 1;
                    } else {
                        adjacency[i][j] = adjacency[j][i] = null;
                    }
                }
            }
            problems.add(new MaximumCut(adjacency, "50%/uniform"));

            Double[][] adjacency2 = new Double[nodes][nodes];

            // fully connected
            for (int i = 0; i < nodes; i++) {
                for (int j = i + 1; j < nodes; j++) {
                    adjacency2[i][j] = adjacency2[j][i] = (double) r.nextInt(magnitude) + 1;
                }
            }

            problems.add(new MaximumCut(adjacency2, "full/uniform"));

            Double[][] adjacency3 = new Double[nodes][nodes];

            // somewhat connected
            for (int i = 0; i < nodes; i++) {
                int connections = r.nextInt(nodes - i);
                for (int j = 0; j < connections; j++) {
                    int idx = i + r.nextInt(nodes - i);
                    adjacency3[i][idx] = adjacency3[idx][i] = (double) r.nextInt(magnitude) + 1;
                }
            }

            problems.add(new MaximumCut(adjacency3, "somewhat"));
        }

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
