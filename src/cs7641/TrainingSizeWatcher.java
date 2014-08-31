package cs7641;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class TrainingSizeWatcher {
    private Classifier classifier;
    private Instances trainSet;
    private Instances testSet;

    public TrainingSizeWatcher(Classifier classifier, Instances trainSet, Instances testSet) {
        this.classifier = classifier;
        this.trainSet = trainSet;
        this.testSet = testSet;
    }

    public void run() throws Exception {

        for (int i = 1; i <= trainSet.numInstances(); i += 10) {
            Instances iTrain = new Instances(trainSet, 0, Math.min(i, trainSet.numInstances()));

            classifier.buildClassifier(iTrain);

            Evaluation eval = new Evaluation(iTrain);
            eval.evaluateModel(classifier, testSet);

            System.out.println(i + ": " + eval.rootMeanSquaredError());
        }
    }
}
