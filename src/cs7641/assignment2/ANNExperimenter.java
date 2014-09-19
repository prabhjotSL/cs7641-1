package cs7641.assignment2;

import cs7641.ann.MultilayerPerceptron;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class ANNExperimenter {
    public static void main(String[] args) throws Exception {
        Instances data = new Instances(new FileReader(new File("datasets/adult_modified.arff")));
        data.setClassIndex(data.numAttributes() - 1);
        int idx = (int)(data.numInstances() * 0.9);
        Instances train = new Instances(data, 0, idx);
        Instances test = new Instances(data, idx, data.numInstances() - idx);

        /*
        MultilayerPerceptron mmp = new MultilayerPerceptron();
        mmp.setHiddenLayers("1");
        mmp.setMomentum(0.2);
        mmp.setLearningRate(0.3);
        mmp.buildClassifier(train, true);

        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(mmp, test);

        System.out.println(eval.pctCorrect());
        */

        for (int i = 0; i < 10; i++) {
        MultilayerPerceptron mmp2 = new MultilayerPerceptron();
        mmp2.setHiddenLayers("1");
        mmp2.buildClassifier(train, false);

        ANNOptimizationProblem op = new ANNOptimizationProblem(mmp2, test);
        NeighborFunction func = new NeighborFunctions.SingleRandomWeightUpdater();

        HillClimber<List<Double>> hc = new HillClimber<List<Double>>(op,func);
        //Annealer<List<Double>> hc = new Annealer<List<Double>>(op, func, 1E11, .95);
        hc.train(10000);
        }

        /*
        double[] weights1 = mmp.getWeights();
        double[] weights2 = mmp2.getWeights();

        for (int i = 0; i < weights1.length; i++)
            if (weights1[i] != weights2[i])
                System.out.println(i + " " + weights1[i] + " " + weights2[i]);
        */
    }
}
