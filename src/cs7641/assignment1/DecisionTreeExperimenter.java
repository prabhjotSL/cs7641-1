package cs7641.assignment1;

import cs7641.Experiment;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeExperimenter extends Experiment {
    public static void main(String[] args) throws Exception {
        DecisionTreeExperimenter exp = new DecisionTreeExperimenter();
        exp.run(10);
    }

    public List<Classifier> getClassifiers() {
        List<Classifier> trees = new ArrayList();

        for (int i = 1; i <= 25; i++) {
            J48 unpruned = new J48();
            unpruned.setUnpruned(true);
            unpruned.setMinNumObj(i);
            trees.add(unpruned);
        }

        J48 reducedError = new J48();
        reducedError.setReducedErrorPruning(true);
        trees.add(reducedError);

        for (int conf = 5; conf <= 50; conf += 5) {
            for (int min = 1; min < 25; min++) {
                J48 tree = new J48();
                tree.setConfidenceFactor(conf/ (float)100);
                tree.setMinNumObj(min);
                trees.add(tree);
            }
        }

        return trees;
    }

    public String getName() {
        return "dt-params";
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Unpruned", "ReducedError", "PruneConfidence", "MinInstancesPerLeaf"};
    }

    @Override
    public Object[] getData(Classifier cls) {
        J48 tree = (J48)cls;
        return new Object[]{tree.getUnpruned(), tree.getReducedErrorPruning(), tree.getConfidenceFactor(), tree.getMinNumObj()};
    }
}
