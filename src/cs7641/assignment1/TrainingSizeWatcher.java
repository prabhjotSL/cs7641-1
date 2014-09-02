package cs7641.assignment1;

import com.sun.deploy.util.StringUtils;
import cs7641.Experiment;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrainingSizeWatcher {
    public static void main(String[] args) throws Exception {
        File adultFile = new File("datasets/adult_modified.arff");

        List<Classifier> adultClassifiers = new ArrayList<Classifier>();
        adultClassifiers.add(DecisionTreeExperimenter.getBestAdult());
        adultClassifiers.add(KNNExperimenter.getBestAdult());
        adultClassifiers.add(SVMExperimenter.getBestAdult());

//        watch("adult-train-size", adultFile, adultClassifiers);

        File semeionFile = new File("datasets/semeion.arff");

        List<Classifier> semeionClassifiers = new ArrayList<Classifier>();
        semeionClassifiers.add(DecisionTreeExperimenter.getBestSemeion());
        semeionClassifiers.add(KNNExperimenter.getBestSemeion());
        semeionClassifiers.add(SVMExperimenter.getBestSemeion());

        watch("semeion-train-size", semeionFile, semeionClassifiers);
    }

    public static void watch(String name, File file, List<Classifier> classifiers) throws Exception {
        Instances data = new Instances(new FileReader(file));
        data.setClassIndex(data.numAttributes() - 1);
        data.randomize(new Random(31));

        int idx = (int)(data.numInstances() * 0.66);
        Instances train = new Instances(data, 0, idx);
        Instances test  = new Instances(data, idx, data.numInstances() - idx);

        for (Classifier cls : classifiers) {
            System.out.println(cls);
            File out = new File(name + "-" + cls.getClass().getSimpleName() + ".tsv");
            FileWriter w = new FileWriter(out);
            for (int i = 10; i < idx; i += 10) {
                System.out.println(i);
                Instances subTrain = new Instances(train, 0, i);
                cls.buildClassifier(subTrain);
                Evaluation e = new Evaluation(subTrain);
                Evaluation e2 = new Evaluation(subTrain);
                e.evaluateModel(cls, test);
                e2.evaluateModel(cls, subTrain);
                w.write(i + String.format("\t%1.2f\t%2.2f", e.pctCorrect(), e2.pctCorrect()));
                w.write("\n");
                w.flush();

            }
            w.close();
        }
    }
}
