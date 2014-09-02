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
        e.run(4, 4);
    }

    public static  SMO getBestAdult() {
        SMO best = new SMO();
        PolyKernel pk = new PolyKernel();
        pk.setExponent(2);
        best.setKernel(pk);
        return best;
    }

    public static SMO getBestSemeion() {
        SMO best = new SMO();
        PolyKernel pk = new PolyKernel();
        pk.setExponent(2);
        best.setKernel(pk);
        return best;
    }

    @Override
    public List<Classifier> getClassifiers() {
        List<Kernel> kernels = new ArrayList();

        kernels.add(new RBFKernel());
        kernels.add(new NormalizedPolyKernel());

        for (int i = 0; i < 4; i++) {
            PolyKernel pk = new PolyKernel();
            pk.setExponent(i);
            kernels.add(pk);
        }

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
        return new String[] {"Kernel", "Exponent"};
    }

    @Override
    public Object[] getData(Classifier cls) {
        SMO svm = (SMO)cls;
        double pow = ( svm.getKernel() instanceof PolyKernel) ? ((PolyKernel)svm.getKernel()).getExponent() : 0;

        return new Object[] {((SMO)cls).getKernel().getClass().getSimpleName(), pow};
    }

    @Override
    public String getName() {
        return "svm-kernels";
    }
}
