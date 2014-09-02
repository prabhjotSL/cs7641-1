package cs7641.assignment1;

import cs7641.Experiment;
import cs7641.ModifiedMultilayerPerceptron;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.J48;
import weka.core.SelectedTag;

import java.util.ArrayList;
import java.util.List;

public class FinalSemeionExperiment extends Experiment {
    public static void main(String[] args) throws Exception {
        FinalSemeionExperiment e = new FinalSemeionExperiment();
        e.run(10, 10);
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Classifier> classers = new ArrayList<Classifier>();

        classers.add(DecisionTreeExperimenter.getBestSemeion());
        classers.add(ANNExperimenter.getBestSemeion());
        classers.add(SVMExperimenter.getBestSemeion());
        classers.add(KNNExperimenter.getBestSemeion());
        classers.add(BoostingExperimenter.getBestSemeion());

        return classers;
    }

    @Override
    public String[] getHeaders() {
        return new String[] {"Classifier"};
    }

    @Override
    public Object[] getData(Classifier cls) {
        return new Object[] {cls.getClass().getSimpleName()};
    }

    @Override
    public String getName() {
        return "overall";
    }
}
