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
        exp.run("ann-local-search", Integer.MAX_VALUE, 500);
    }

    @Override
    public List<OptimizationProblem<List<Double>>> getProblems() {
        Instances data;

        try {
            data = new Instances(new FileReader(new File("datasets/adult_modified.arff")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        data.setClassIndex(data.numAttributes() - 1);

        MultilayerPerceptron mmp = new MultilayerPerceptron();
        mmp.setHiddenLayers("1");

        try {
            mmp.buildClassifier(data, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<OptimizationProblem<List<Double>>> problems = new ArrayList();

        problems.add(new AnnProblem(mmp, data));

        return problems;
    }

    @Override
    public List<RandomOptimizer<List<Double>>> getOptimizers() {
        List<RandomOptimizer<List<Double>>> optimizers = new ArrayList();

        optimizers.add(new HillClimber<List<Double>>(new NeighborFunctions.ContinuousSpaceNeighbor()));

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
        //optimizers.add(new Annealer(new NeighborFunctions.ContinuousSpaceNeighbor(), 1000, 0.99));

        return optimizers;
    }
}
