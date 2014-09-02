package cs7641.assignment1;

import cs7641.Experiment;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.J48;

import java.util.ArrayList;
import java.util.List;

public class BoostingExperimenter extends Experiment {
    private DecisionTreeExperimenter dte = new DecisionTreeExperimenter();

    public static AdaBoostM1 getBestAdult() {
        AdaBoostM1 best = new AdaBoostM1();
        J48 booster = new J48();
        booster.setUnpruned(true);
        booster.setMinNumObj(4);
        best.setNumIterations(40);
        best.setClassifier(booster);
        return best;
    }

    public static AdaBoostM1 getBestSemeion() {
        AdaBoostM1 best = new AdaBoostM1();
        J48 booster = new J48();
        booster.setUnpruned(true);
        booster.setMinNumObj(5);
        best.setNumIterations(86);
        best.setClassifier(booster);
        return best;
    }

    public static void main(String[] args) throws Exception {
        BoostingExperimenter e = new BoostingExperimenter();
        e.run(10, 10);
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Classifier> classers = dte.getClassifiers();

        List<Classifier> boosters = new ArrayList<Classifier>();

        for (Classifier cls : classers) {
            AdaBoostM1 b = new AdaBoostM1();
            b.setClassifier(cls);
            boosters.add(b);
        }

        return boosters;
    }

    @Override
    public String[] getHeaders() {
        return dte.getHeaders();
    }

    @Override
    public Object[] getData(Classifier cls) {
        return dte.getData(((AdaBoostM1)cls).getClassifier());
    }

    @Override
    public String getName() {
        return "boost-params";
    }
}
