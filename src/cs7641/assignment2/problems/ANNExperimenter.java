package cs7641.assignment2.problems;

import cs7641.ann.MultilayerPerceptron;
import cs7641.assignment2.*;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ANNExperimenter extends Experiment<List<Double>> {
    public static void main(String[] args) throws Exception {
        ANNExperimenter exp = new ANNExperimenter();
        exp.run("ann", Integer.MAX_VALUE, 10, 10);
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
        Instances reduced = new Instances(data, 0, 1000);

        MultilayerPerceptron mmp = new MultilayerPerceptron();
        mmp.setHiddenLayers("1");

        try {
            mmp.buildClassifier(data, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MultilayerPerceptron mmp2 = new MultilayerPerceptron();
        mmp2.setHiddenLayers("2");

        try {
            mmp2.buildClassifier(data, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<OptimizationProblem<List<Double>>> problems = new ArrayList();

        problems.add(new ANNOptimizationProblem(mmp, reduced));
        problems.add(new ANNOptimizationProblem(mmp2, reduced));

        return problems;
    }

    @Override
    public List<RandomOptimizer<List<Double>>> getOptimizers() {
        List<RandomOptimizer<List<Double>>> optimizers = new ArrayList();

        NeighborFunction func = new NeighborFunctions.ContinuousSpaceNeighbor();
        HillClimber<List<Double>> hc = new HillClimber<List<Double>>(func);

        //optimizers.add(hc);

        /*(optimizers.add(new Evolver(100, 50, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(200, 50, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(200, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));
        optimizers.add(new Evolver(400, 200, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 10));

        optimizers.add(new Evolver(100, 50, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));
        optimizers.add(new Evolver(200, 50, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));
        optimizers.add(new Evolver(200, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));
        optimizers.add(new Evolver(400, 100, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));
        optimizers.add(new Evolver(400, 200, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 50));*/

        optimizers.add(new Evolver(2000, 250, new NeighborFunctions.ListUniformCrossover(), new NeighborFunctions.SingleWeightMutator(), 200));

        return optimizers;
    }
}
