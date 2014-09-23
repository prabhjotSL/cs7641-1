package cs7641.assignment2;

import com.sun.deploy.util.StringUtils;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public abstract class Experiment<T> {
    public abstract List<OptimizationProblem<T>> getProblems();
    public abstract List<RandomOptimizer<T>> getOptimizers();

    public void run(String name, int sought) throws Exception {
        FileWriter fw = new FileWriter(name + "-" + sought + ".tsv");

        boolean headerPrinted = false;

        for (OptimizationProblem<T> problem : getProblems()) {
            if (!headerPrinted) {
                String msg = StringUtils.join(Arrays.asList(problem.getColumns()), "\t");
                msg += "\t" + StringUtils.join(Arrays.asList(new String[]{"Calcs", "AltCalcs", "AvgRuntime", "SuccessRatio", "Optimizer"}), "\t");
                msg += "\n";

                fw.write(msg);
                System.out.print(msg);

                headerPrinted = true;
            }


            for (RandomOptimizer<T> optimizer : getOptimizers()) {

                int found = 0;
                int tried = 0;
                int calcs = 0;

                int altCalcs = 0;

                long start = System.currentTimeMillis();

                while (found < sought) {
                    problem.reset();
                    optimizer.setProblem(problem);
                    optimizer.reset();

                    optimizer.train(100);

                    calcs += problem.numFitnessCalculations();
                    altCalcs += optimizer.getBestNumCalcs();

                    tried += 1;

                    if (optimizer.getBest().getValue().equals(problem.getTarget())) {
                        found += 1;
                    } else if (optimizer.getBest().getValue() > problem.getTarget())
                        throw new RuntimeException("WTF");
                }

                Double avgSeconds  = ((System.currentTimeMillis() - start) / 1000) / (double)sought;
                Double avgCalcs    = calcs / (double)sought;
                Double altAvgCalcs = altCalcs / (double)sought;

                String msg = StringUtils.join(Arrays.asList(problem.getData()), "\t");
                msg += "\t" + StringUtils.join(Arrays.asList(new String[] {String.format("%1.0f", avgCalcs), String.format("%1.0f", altAvgCalcs), String.format("%1.1f", avgSeconds), String.format("%1.4f", found / (double)tried), optimizer.toString(), String.valueOf(found), String.valueOf(tried)}), "\t");
                msg += "\n";

                fw.write(msg);

                System.out.print(msg);

                fw.flush();
            }
        }
        fw.close();
    }
}
