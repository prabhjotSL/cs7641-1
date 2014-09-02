package cs7641.assignment1;

import cs7641.Experiment;
import weka.classifiers.Classifier;

import java.util.ArrayList;
import java.util.List;

public class FinalAdultExperiment extends Experiment {
    public static void main(String[] args) throws Exception {
        FinalAdultExperiment e = new FinalAdultExperiment();
        e.run(10, 10);
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Classifier> classers = new ArrayList<Classifier>();

        classers.add(DecisionTreeExperimenter.getBestAdult());
        classers.add(ANNExperimenter.getBestAdult());
        classers.add(SVMExperimenter.getBestAdult());
        classers.add(KNNExperimenter.getBestAdult());
        classers.add(BoostingExperimenter.getBestAdult());

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
