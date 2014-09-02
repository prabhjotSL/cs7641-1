package cs7641.assignment1;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class BoostingIterationsWatcher {
    public static void main(String[] args) throws Exception {
        File adultFile = new File("datasets/adult_modified.arff");

        watch("adult-boost-its", adultFile, BoostingExperimenter.getBestAdult());

        File semeionFile = new File("datasets/semeion.arff");

        watch("semeion-boost-its", semeionFile, BoostingExperimenter.getBestSemeion());
    }

    public static void watch(String name, File file, AdaBoostM1 cls) throws Exception {
        Instances data = new Instances(new FileReader(file));
        data.setClassIndex(data.numAttributes() - 1);
        data.randomize(new Random(31));

        int idx = (int) (data.numInstances() * 0.66);
        Instances train = new Instances(data, 0, idx);
        Instances test = new Instances(data, idx, data.numInstances() - idx);

        FileWriter w = new FileWriter(new File(name + ".tsv"));

        for (int i = 1; i <= 100; i++) {
            Classifier copy = cls.makeCopy(cls);
            ((AdaBoostM1) copy).setNumIterations(i);
            copy.buildClassifier(train);
            Evaluation eTest = new Evaluation(train);
            Evaluation eTrain = new Evaluation(train);
            eTest.evaluateModel(copy, test);
            eTrain.evaluateModel(copy, train);
            w.write(i + String.format("\t%1.2f\t%2.2f", eTest.pctCorrect(), eTrain.pctCorrect()));
            w.write("\n");
            w.flush();
        }
        w.close();
    }
}
