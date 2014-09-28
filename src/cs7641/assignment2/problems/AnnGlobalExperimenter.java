package cs7641.assignment2.problems;

import cs7641.assignment2.Evolver;
import cs7641.assignment2.NeighborFunctions;
import cs7641.assignment2.RandomOptimizer;

import java.util.ArrayList;
import java.util.List;

public class AnnGlobalExperimenter extends AnnLocalExperimenter {
    public static void main(String[] args) throws Exception {
        AnnGlobalExperimenter exp = new AnnGlobalExperimenter();
        exp.run("ann-ga-final", Integer.MAX_VALUE, 10);
    }

    @Override
    public List<RandomOptimizer<List<Double>>> getOptimizers() {
        List<RandomOptimizer<List<Double>>> optimizers = new ArrayList();

        /*
        optimizers.add(new Evolver(100, 25, new NeighborFunctions.ListSingleCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(200, 50, new NeighborFunctions.ListSingleCrossover(), new NeighborFunctions.SingleWeightMutator(), 20));
        optimizers.add(new Evolver(300, 75, new NeighborFunctions.ListSingleCrossover(), new NeighborFunctions.SingleWeightMutator(), 30));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListSingleCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));

        optimizers.add(new Evolver(100, 25, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(200, 50, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 20));
        optimizers.add(new Evolver(300, 75, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 30));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));
        optimizers.add(new Evolver(500, 125, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));
        optimizers.add(new Evolver(1000, 250, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 100));

        optimizers.add(new Evolver(400,  10, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));
        optimizers.add(new Evolver(400,  50, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));
        optimizers.add(new Evolver(400, 150, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));
        optimizers.add(new Evolver(400, 200, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 40));

        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 100));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 150));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 200));
        */

        optimizers.add(new Evolver(1000, 250, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 100));

        return optimizers;
    }
}
