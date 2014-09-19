package cs7641.assignment2;

import cs7641.ann.MultilayerPerceptron;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class ANNOptimizationProblem implements OptimizationProblem<List<Double>> {

    private MultilayerPerceptron mmp;
    private Instances test;
    private int calcs = 0;

    public ANNOptimizationProblem(MultilayerPerceptron mmp, Instances test) {
        this.mmp = mmp;
        this.test = test;
    }

    @Override
    public int numFitnessCalculations() {
        return calcs;
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

    public Double fitnessOf(List<Double> d) {
        calcs++;

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
