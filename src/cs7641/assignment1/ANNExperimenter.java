package cs7641.assignment1;

import cs7641.Experiment;
import cs7641.ModifiedMultilayerPerceptron;
import weka.classifiers.Classifier;

import java.util.ArrayList;
import java.util.List;

public class ANNExperimenter extends Experiment {
    public static void main(String[] args) throws Exception {
        ANNExperimenter e = new ANNExperimenter();
        e.run(4, 4);
    }

    public static ModifiedMultilayerPerceptron getBestAdult() {
        ModifiedMultilayerPerceptron best = new ModifiedMultilayerPerceptron();
        best.setLearningRate(0.3);
        best.setMomentum(0.3);
        best.setHiddenLayers("32");
        return best;
    }

    public static ModifiedMultilayerPerceptron getBestSemeion() {
        ModifiedMultilayerPerceptron best = new ModifiedMultilayerPerceptron();
        best.setLearningRate(0.3);
        best.setMomentum(0.2);
        best.setHiddenLayers("64");
        return best;
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Classifier> anns = new ArrayList();

        for (int m = 2; m <= 5; m++) {
            for (int r = 3; r <= 4; r++) {
                for (int n = 1; n <= 6; n++) {
                    ModifiedMultilayerPerceptron ann = new ModifiedMultilayerPerceptron();
                    ann.setMomentum(m / (double)10);
                    ann.setLearningRate(r / (double)10);
                    ann.setHiddenLayers(String.valueOf(Math.pow(2, n)));
                    anns.add(ann);
                }

                ModifiedMultilayerPerceptron ann = new ModifiedMultilayerPerceptron();
                ann.setMomentum(m / (double)10);
                ann.setLearningRate(r / (double)10);
                ann.setHiddenLayers("a"); // try the default
                anns.add(ann);
            }
        }

        return anns;
    }

    @Override
    public String[] getHeaders() {
        return new String[] {"Momentum", "Rate", "Nodes", "Iterations"};
    }

    @Override
    public Object[] getData(Classifier cls) {
        ModifiedMultilayerPerceptron mp = (ModifiedMultilayerPerceptron)cls;
        return new Object[]{mp.getMomentum(), mp.getLearningRate(), mp.getHiddenLayers(), mp.getEpoch()};
    }

    @Override
    public String getName() {
        return "ann-params";
    }
}
