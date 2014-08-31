package cs7641.assignment1;

import cs7641.Experiment;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.Kernel;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RBFKernel;

import java.util.ArrayList;
import java.util.List;

public class SVMExperimenter extends Experiment {
    public static void main(String[] args) throws Exception {
        SVMExperimenter e = new SVMExperimenter();
        e.run(4);
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Kernel> kernels = new ArrayList();

        kernels.add(new PolyKernel());
        kernels.add(new RBFKernel());
        kernels.add(new NormalizedPolyKernel());

        List<Classifier> svms = new ArrayList();
        for (Kernel k : kernels) {
            SMO svm = new SMO();
            svm.setKernel(k);
            svms.add(svm);
        }

        return svms;
    }

    @Override
    public String[] getHeaders() {
        return new String[] {"Kernel"};
    }

    @Override
    public Object[] getData(Classifier cls) {
        return new Object[] {((SMO)cls).getKernel().getClass().getSimpleName()};
    }

    @Override
    public String getName() {
        return "svm-kernels";
    }
}
