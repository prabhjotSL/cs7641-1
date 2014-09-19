package cs7641.assignment1;

import cs7641.ann.MultilayerPerceptron;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class ANNIterationsWatcher {
    public static void main(String[] args) throws Exception {
        File adultFile = new File("datasets/adult_modified.arff");

        watch("adult-ann-its", adultFile, ANNExperimenter.getBestAdult());

        File semeionFile = new File("datasets/semeion.arff");

        watch("semeion-ann-its", semeionFile, ANNExperimenter.getBestSemeion());
    }

    public static void watch(String name, File file, final MultilayerPerceptron cls) throws Exception {
        Instances data = new Instances(new FileReader(file));
        data.setClassIndex(data.numAttributes() - 1);
        data.randomize(new Random(31));

        int idx = (int) (data.numInstances() * 0.66);
        final Instances train = new Instances(data, 0, idx);
        final Instances test = new Instances(data, idx, data.numInstances() - idx);

        final FileWriter w = new FileWriter(new File(name + ".tsv"));

        cls.setTrainingTime(1500);
        cls.setEpochCallback(new MultilayerPerceptron.EpochCallback() {
            @Override
            public boolean epochFinished(int epoch) {
                try {
                    Evaluation eTest = new Evaluation(train);
                    Evaluation eTrain = new Evaluation(train);
                    eTest.evaluateModel(cls, test);
                    eTrain.evaluateModel(cls, train);
                    w.write(epoch + String.format("\t%1.2f\t%2.2f", eTest.pctCorrect(), eTrain.pctCorrect()));
                    w.write("\n");
                    w.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return epoch > 1500;
            }
        });

        cls.buildClassifier(train);

        w.close();
    }
}
