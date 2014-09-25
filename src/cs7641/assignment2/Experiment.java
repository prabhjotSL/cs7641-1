package cs7641.assignment2;

import com.sun.deploy.util.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public abstract class Experiment<T> {
    public abstract List<OptimizationProblem<T>> getProblems();
    public abstract List<RandomOptimizer<T>> getOptimizers();

    public void run(String name, int sought, int tries) throws Exception {
        FileWriter fw = new FileWriter(name + "-" + sought + ".tsv");

        boolean headerPrinted = false;

        for (OptimizationProblem<T> problem : getProblems()) {
            boolean hasTarget = problem.getTarget() != null;

            if (!headerPrinted) {
                String msg = StringUtils.join(Arrays.asList(problem.getColumns()), "\t");
                msg += "\t" + StringUtils.join(Arrays.asList(new String[]{"Invocations", "FitnessCost", "AltFitnessCost", "MinFitness", "MeanFitness", "MaxFitness", "StdDevFitness", "AvgRuntime"}), "\t");
                if (hasTarget)
                    msg += "\t" + StringUtils.join(Arrays.asList(new String[]{"Target", "Hits", "HitsRatio", "FitCalcsPerOpt", "AltFitCalcsPerOpt", "RuntimePerOpt"}), "\t");

                msg += "\tOptimizer\n";

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

                SummaryStatistics stats = new SummaryStatistics();

                while (found < sought && tried < tries) {
                    problem.reset();
                    optimizer.setProblem(problem);
                    optimizer.reset();

                    optimizer.train(100);

                    calcs += problem.numFitnessCalculations();
                    altCalcs += optimizer.getBestNumCalcs();

                    stats.addValue(optimizer.getBest().getValue());

                    tried += 1;

                    if (hasTarget) {
                        if (optimizer.getBest().getValue().equals(problem.getTarget())) {
                            found += 1;
                        } else if (optimizer.getBest().getValue() < problem.getTarget()) {
                            //System.out.println("subopt: " + optimizer.getBest().getValue());
                        } else if (optimizer.getBest().getValue() > problem.getTarget())
                            throw new RuntimeException("WTF");
                    }
                }

                long end = System.currentTimeMillis();

                Double avgRuntime = ((end - start) / 1000) / (double)tried;

                Double fitnessCost = calcs / stats.getSum();
                Double altFitnessCost = altCalcs /stats.getSum();

                String msg = StringUtils.join(Arrays.asList(problem.getData()), "\t") + "\t";

                msg += String.format("%1d\t", tried);
                msg += String.format("%1f\t", fitnessCost);
                msg += String.format("%1f\t", altFitnessCost);
                msg += String.format("%1.1f\t", stats.getMin());
                msg += String.format("%1.1f\t", stats.getMean());
                msg += String.format("%1.1f\t", stats.getMax());
                msg += String.format("%1.1f\t", stats.getStandardDeviation());
                msg += String.format("%1.2f", avgRuntime);

                if (hasTarget) {
                    Double secsPerOpt     = ((end - start) / 1000) / (double)found;
                    Double calcsPerOpt    = calcs / (double)found;
                    Double altCalcsPerOpt = altCalcs / (double)found;

                    msg += "\t" + problem.getTarget();
                    msg += "\t" + found + "\t";
                    msg += String.format("%1.4f\t", found / (double)tried);
                    msg += String.format("%1.0f\t", calcsPerOpt);
                    msg += String.format("%1.0f\t", altCalcsPerOpt);
                    msg += String.format("%1.1f\t", secsPerOpt);
                }

                msg += "\t" + optimizer.toString();

                msg += "\n";

                fw.write(msg);

                System.out.print(msg);

                fw.flush();
            }
        }
        fw.close();
    }
}
