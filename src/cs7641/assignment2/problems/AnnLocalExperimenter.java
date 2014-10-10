package cs7641.assignment2.problems;

import cs7641.ann.MultilayerPerceptron;
import cs7641.assignment2.*;
import weka.core.Instances;
import weka.filters.supervised.instance.Resample;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AnnLocalExperimenter extends Experiment<List<Double>> {
    public static void main(String[] args) throws Exception {
        AnnLocalExperimenter exp = new AnnLocalExperimenter();
        exp.run("ann-local", Integer.MAX_VALUE, 500);
    }

    @Override
    public List<OptimizationProblem<List<Double>>> getProblems() {
        Instances full;
        Instances sub;

        try {
            full = new Instances(new FileReader(new File("datasets/adult_modified.arff")));
            sub  = new Instances(new FileReader(new File("datasets/adult_1k.arff")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        full.setClassIndex(full.numAttributes() - 1);
        sub.setClassIndex(sub.numAttributes() - 1);

        MultilayerPerceptron mmp = new MultilayerPerceptron();
        mmp.setHiddenLayers("1");

        try {
            mmp.buildClassifier(full, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<OptimizationProblem<List<Double>>> problems = new ArrayList();

        problems.add(new AnnProblem(mmp, sub, full));

        return problems;
    }

    @Override
    public List<RandomOptimizer<List<Double>>> getOptimizers() {
        List<RandomOptimizer<List<Double>>> optimizers = new ArrayList();

        optimizers.add(new HillClimber<List<Double>>(new NeighborFunctions.ContinuousSpaceNeighbor()));
        //optimizers.add(new HillClimber<List<Double>>(new NeighborFunctions.SingleRandomWeightUpdater()));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.95));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.99));
        //optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.95));

        /*

        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.05));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.1));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.2));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.3));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.4));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.5));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.6));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.7));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.8));
        optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.9));
        */

        /*
        optimizers.add(new HillClimber<List<Double>>(new NeighborFunctions.SingleRandomWeightUpdater()));

        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.05));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.1));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.2));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.3));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.4));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.5));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.6));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.7));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.8));
        optimizers.add(new Annealer(new NeighborFunctions.SingleRandomWeightUpdater(), 1000, 0.9));
        */

        return optimizers;
    }
}
