package cs7641;

import com.sun.deploy.util.StringUtils;
import javafx.util.Pair;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Experiment {
    private int iterationsSinceBest = 0;
    private Evaluation bestTrain;
    private Evaluation bestTest;
    private int bestTestTime;
    private int bestTrainTime;

    public abstract List<Classifier> getClassifiers();

    public abstract String[] getHeaders();

    public abstract Object[] getData(Classifier cls);

    public abstract String getName();

    public void run(int folds) throws Exception {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("ARFF", "arff");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("/home/kmcintyre/cs7641/datasets"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            System.exit(0);

        Instances data = new Instances(new FileReader(chooser.getSelectedFile()));
        int seed = 31;

        int its = 1;//folds;

        data.setClassIndex(data.numAttributes() - 1);
        data.randomize(new Random(seed));

        File input = chooser.getSelectedFile();
        String baseName = input.getName().split("\\.(?=[^\\.]+$)")[0];
        File output = new File(input.getParent() + "/" + baseName + "-" + getName() + ".tsv");
        FileWriter writer = new FileWriter(output);

        writer.write(StringUtils.join(Arrays.asList(getHeaders()), "\t") + "\t");
        writer.write(StringUtils.join(Arrays.asList(new String[]{"trainPctCorrect", "testPctCorrect", "trainTime", "testTime"}), "\t"));
        writer.write("\n");

        for (int c = 0; c < getClassifiers().size(); c++) {
            Classifier cls = getClassifiers().get(c);
            System.out.println("Testing classifier " + c + " of " + getClassifiers().size());

            double trainTime = 0;
            double testTime = 0;

            final List<Evaluation> trainEvals = new ArrayList<Evaluation>();
            final List<Evaluation> testEvals = new ArrayList<Evaluation>();

            for (int f = 0; f < its; f++) {
                final Instances train = data.trainCV(folds, f);
                final Instances test = data.testCV(folds, f);

                bestTrain = null;
                bestTest = null;
                iterationsSinceBest = 0;

                if (cls instanceof ModifiedMultilayerPerceptron) {
                    final ModifiedMultilayerPerceptron mmp = (ModifiedMultilayerPerceptron) cls;
                    mmp.setEpochCallback(new ModifiedMultilayerPerceptron.EpochCallback() {
                        @Override
                        public boolean epochFinished(int epoch) {
                            try {
                                Evaluation stop = new Evaluation(train);
                                long start = System.currentTimeMillis();
                                stop.evaluateModel(mmp, test);
                                long end = System.currentTimeMillis();
                                if (bestTest == null || stop.pctCorrect() > bestTest.pctCorrect()) {
                                    bestTest = stop;
                                    bestTestTime += (end - start) / (double) 1000;
                                    Evaluation foo = new Evaluation(train);
                                    start = System.currentTimeMillis();
                                    foo.evaluateModel(mmp, test);
                                    end = System.currentTimeMillis();
                                    bestTrainTime += (end - start) / (double) 1000;
                                    bestTrain = foo;
                                    return false;
                                }

                                if (iterationsSinceBest > 200) {
                                    return true;
                                }

                                iterationsSinceBest++;
                            } catch (Exception e) {

                            }
                            return false;
                        }
                    });
                }

                long start = System.currentTimeMillis();
                cls.buildClassifier(train);
                long end = System.currentTimeMillis();
                trainTime += (end - start) / (double) 1000;

                if (cls instanceof ModifiedMultilayerPerceptron) {
                    trainEvals.add(bestTrain);
                    testEvals.add(bestTest);
                    testTime += bestTestTime;
                    trainTime += bestTrainTime;
                } else {
                    Evaluation testE = new Evaluation(train);
                    start = System.currentTimeMillis();
                    testE.evaluateModel(cls, test);
                    end = System.currentTimeMillis();
                    testTime += (end - start) / (double) 1000;

                    Evaluation trainE = new Evaluation(train);
                    trainE.evaluateModel(cls, train);

                    testEvals.add(testE);
                    trainEvals.add(trainE);
                }
            }

            double trainCorrectSum = 0.0;
            double testCorrectSum = 0.0;

            for (int i = 0; i < its; i++) {
                testCorrectSum += testEvals.get(i).pctCorrect();
                trainCorrectSum += trainEvals.get(i).pctCorrect();
            }

            List<String> d = new ArrayList<String>();
            for (int i = 0; i < getHeaders().length; i++)
                d.add(getData(cls)[i].toString());

            String msg = StringUtils.join(d, "\t") + String.format("\t%1.2f\t%2.2f\t%3.2f\t%3.2f", trainCorrectSum / (double) its, testCorrectSum / (double) its, trainTime / (double) its, testTime / (double) its);
            System.out.println(msg);
            writer.write(msg);
            writer.write("\n");
        }

        writer.close();
    }
}
