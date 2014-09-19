package cs7641.assignment2;

import java.util.BitSet;
import java.util.Random;

public class FourPeaks implements OptimizationProblem<BitSet> {
    private int length;
    private int t;
    private int calcs = 0;

    public FourPeaks(int length) {
        this.length = length;
        this.t = length / 10;
    }

    @Override
    public int numFitnessCalculations() {
        return calcs;
    }

    @Override
    public Double getTarget() {
        return (double)(length + (length - (t + 1)));
    }

    @Override
    public Double fitnessOf(BitSet bitSet) {
        calcs++;

        int idx = 0;

        int heads = 0;
        while (idx < length && bitSet.get(idx)) {
            heads++;
            idx++;
        }

        idx = length - 1;

        int tails = 0;
        while (idx >= 0 && !bitSet.get(idx)) {
            tails++;
            idx--;
        }

        int bonus = 0;

        if (heads > t && tails > t)
            bonus = length;

        return (double)(Math.max(heads, tails) + bonus);
    }

    @Override
    public BitSet getStartConfiguration() {
        BitSet set = new BitSet(length + 1);

        Random r = new Random();

        for (int i = 0; i < length; i++)
            set.set(i, r.nextBoolean());

        set.set(length, true);

        return set;
    }

    @Override
    public BitSet getRandomConfiguration() {
        return getStartConfiguration();
    }
}
