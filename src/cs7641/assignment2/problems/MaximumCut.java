package cs7641.assignment2.problems;

import cs7641.assignment2.OptimizationProblem;

import java.util.BitSet;
import java.util.Random;

public class MaximumCut extends OptimizationProblem<BitSet> {
    private Double[][] adjacency;

    public MaximumCut(Double[][] adjacency) {
        this.adjacency = adjacency;
    }

    public String[] getColumns() {
        return new String[]{"size"};
    }

    public String[] getData() {
        return new String[] {String.valueOf(adjacency.length)};
    }

    @Override
    public Double fitnessOfImpl(BitSet bitSet) {
        double fitness = 0.0;

        for (int i = 0; i < bitSet.length() - 1; i++) {
            boolean group1 = bitSet.get(i);
            for (int j = i + 1; j < adjacency.length; j++) {
                if (group1 != bitSet.get(j)) {// Must be opposite of i
                    Double val = adjacency[i][j];
                    if (val != null)
                        fitness += val;
                }
            }
        }

        return fitness;
    }

    @Override
    public BitSet getStartConfiguration() {

        BitSet set = new BitSet(adjacency.length + 1);

        Random r = new Random();

        for (int i = 0; i < adjacency.length; i++)
            set.set(i, r.nextBoolean());

        set.set(adjacency.length, true);

        return set;
    }

    @Override
    public BitSet getRandomConfiguration() {
        return getStartConfiguration();
    }

    @Override
    public Double getTarget() {
        return null;
    }
}
