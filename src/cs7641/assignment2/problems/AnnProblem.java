package cs7641.assignment2.problems;

import cs7641.ann.MultilayerPerceptron;
import cs7641.assignment2.OptimizationProblem;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

public class AnnProblem extends OptimizationProblem<List<Double>> {

    private MultilayerPerceptron mmp;
    private Instances sub;
    private Instances full;

    public AnnProblem(MultilayerPerceptron mmp, Instances sub, Instances full) {
        this.mmp  = mmp;
        this.sub  = sub;
        this.full = full;
    }

    public String[] getColumns() {
        return new String[]{"numWeights"};
    }

    public String[] getData() {
        return new String[] {String.valueOf(mmp.numWeights())};
    }

    public List<Double> getStartConfiguration() {
        List<Double> start = new ArrayList(mmp.numWeights());
        for (int i = 0; i < mmp.numWeights(); i++)
            start.add(Math.random() - 0.5);

        return start;
    }

    public List<Double> getRandomConfiguration() {
        List<Double> r = new ArrayList();
        for (int i = 0; i < mmp.numWeights(); i++)
            r.add(Math.random() * 20 - 10);
        return r;
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
            Evaluation eval = new Evaluation(sub);
            eval.evaluateModel(mmp, sub);
            return eval.pctCorrect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isSubsample() {
        return true;
    }

    @Override
    public double getFitnessOverFullData(List<Double> d) {
         double[] weights = new double[d.size()];
        for (int i = 0; i < d.size(); i++)
            weights[i] = d.get(i);
        mmp.setWeights(weights);
        try {
            Evaluation eval = new Evaluation(full);
            eval.evaluateModel(mmp, full);
            return eval.pctCorrect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
