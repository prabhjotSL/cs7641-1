package cs7641.assignment1;

import weka.classifiers.Classifier;

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
