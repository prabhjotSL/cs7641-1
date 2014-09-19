package cs7641.assignment2;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import javafx.util.Pair;
import opt.EvaluationFunction;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.Instance;

import java.util.BitSet;

public class Mimicker extends RandomOptimizer<BitSet> {
    private Distribution dist;
    private MIMIC mimic;

    public Mimicker(OptimizationProblem<BitSet> problem, int popSize, int keep) {
        super(problem);

        int[] ranges = new int[problem.getStartConfiguration().length() - 1];
        for (int i = 0; i < ranges.length; i++)
            ranges[i] = 2;

        dist = new DiscreteDependencyTree(.1, ranges);

        EvaluationFunction func = new EvaluationFunction() {
            @Override
            public double value(Instance d) {
                return Mimicker.this.problem.fitnessOf(instanceToBitSet(d));
            }
        };

        ProbabilisticOptimizationProblem p = new GenericProbabilisticOptimizationProblem(func, new DiscreteUniformDistribution(ranges), dist);

        mimic = new MIMIC(popSize, keep, p);
    }

    private BitSet instanceToBitSet(Instance d) {
        BitSet set = new BitSet(d.size() + 1);
        set.set(d.size(), true);

        for (int i = 0; i < d.getData().size(); i++)
            set.set(i, d.getData().get(i) > 0.5);

        return set;
    }

    @Override
    public Pair<BitSet, Double> iterate() {
        mimic.train();

        BitSet b = instanceToBitSet(mimic.getOptimal());

        return new Pair(b, problem.fitnessOf(b));
    }
}
