package cs7641.assignment2.problems;

import cs7641.ann.MultilayerPerceptron;
import cs7641.assignment2.OptimizationProblem;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class ANNOptimizationProblem extends OptimizationProblem<List<Double>> {

    private MultilayerPerceptron mmp;
    private Instances test;

    public ANNOptimizationProblem(MultilayerPerceptron mmp, Instances test) {
        this.mmp = mmp;
        this.test = test;
    }

    public String[] getColumns() {
        return new String[]{"numWeights"};
    }

    public String[] getData() {
        return new String[] {String.valueOf(mmp.numWeights())};
    }

    public List<Double> getStartConfiguration() {
        List<Double> start = new ArrayList(mmp.numWeights());
        double[] weights = mmp.getWeights();
        for (int i = 0; i < weights.length; i++)
            start.add(weights[i]);

        return start;
    }

    public List<Double> getRandomConfiguration() {
        // TODO
        return null;
    }

    public Double getTarget() {
        return null;
    }

    public Double fitnessOfImpl(List<Double> d) {
        double[] weights = new double[d.size()];
        for (int i = 0; i < d.size(); i++)
            weights[i] = d.get(i);
        mmp.setWeights(weights);
        try {
            Evaluation eval = new Evaluation(test);
            eval.evaluateModel(mmp, test);
            return eval.pctCorrect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
