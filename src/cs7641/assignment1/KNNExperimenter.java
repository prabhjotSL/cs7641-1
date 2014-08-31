package cs7641.assignment1;

import cs7641.Experiment;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.SelectedTag;

import java.util.ArrayList;
import java.util.List;

public class KNNExperimenter extends Experiment {
    public static void main(String[] args) throws Exception {
        KNNExperimenter exp = new KNNExperimenter();
        exp.run(4);
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Classifier> knns = new ArrayList();

        for (int w = 0; w <= 2; w++) {
            for (int i = 1; i <= 10; i++) {
                IBk knn = new IBk(i);
                knn.setDistanceWeighting(new SelectedTag((int)Math.pow(2, w), IBk.TAGS_WEIGHTING));
                knns.add(knn);
            }
        }
        return knns;
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Weighting?", "K"};
    }

    @Override
    public Object[] getData(Classifier cls) {
        IBk knn = (IBk)cls;
        return new Object[]{knn.getDistanceWeighting(), knn.getKNN()};
    }

    @Override
    public String getName() {
        return "knn-params";
    }
}
