package cs7641.assignment2;

import java.util.*;
import java.util.function.Consumer;

public class NeighborFunctions {

    enum ContinuousStep {
        BIG_NEG,
        BIG_POS,
        MED_NEG,
        MED_POS,
        LIT_NEG,
        LIT_POS
    }
    public static class ContinuousSpaceNeighbor implements NeighborFunction<List<Double>> {
        private final Random r = new Random();

        @Override
        public Iterator<List<Double>> getNeighbors(final List<Double> doubles) {
            final int[] shuffled = shuffleIndexes(doubles.size());

            Iterator<List<Double>> it = new Iterator() {
                int index = 0;
                ContinuousStep step = ContinuousStep.BIG_NEG;

                @Override
                public boolean hasNext() {
                    return index < shuffled.length;
                }

                @Override
                public Object next() {
                    List<Double> neighbor = new ArrayList<Double>(doubles);

                    double oldVal = neighbor.get(shuffled[index]);
                    double newVal = oldVal;

                    switch (step) {
                        case BIG_NEG: newVal -= 5; break;
                        case BIG_POS: newVal += 5; break;
                        case MED_NEG: newVal -= 1; break;
                        case MED_POS: newVal += 1; break;
                        case LIT_NEG: newVal -= 0.1; break;
                        case LIT_POS: newVal += 0.1; break;
                    }

                    neighbor.set(shuffled[index], newVal);

                    ContinuousStep[] test = ContinuousStep.values();

                    int m = step.ordinal()  + 1 % ContinuousStep.values().length - 1;

                    step = ContinuousStep.values()[m];

                    if (step.ordinal() == 0)
                        index += 1;

                    return neighbor;
                }
            };

            return it;
        }
    }

    public static class SingleRandomWeightUpdater implements NeighborFunction<List<Double>> {
        @Override
        public Iterator<List<Double>> getNeighbors(List<Double> weights) {
            List<List<Double>> neighbors = new ArrayList<List<Double>>();

            Random r = new Random(System.currentTimeMillis());

            int idx = (int) (Math.random() * weights.size());

            List<Double> copy = new ArrayList(weights);
            copy.set(idx, copy.get(idx) + Math.random() * 2 - 1);
            neighbors.add(copy);

            return neighbors.iterator();
        }
    }

    private static int[] shuffleIndexes(int size) {
        int[] indexes = new int[size];
        for (int i = 0; i < size; i++)
            indexes[i] = i;

        Random r = new Random();

        // Shuffle it
        for (int i = 0; i < indexes.length; i++) {
            int idx = r.nextInt(i + 1);

            int tmp = indexes[idx];

            indexes[idx] = indexes[i];
            indexes[i] = tmp;
        }

        return indexes;
    }

    public static class SingleBitFlipper implements NeighborFunction<BitSet> {

        @Override
        public Iterator<BitSet> getNeighbors(final BitSet bitSet) {
            final int[] indexes = shuffleIndexes(bitSet.length() - 1);

            Iterator<BitSet> it = new Iterator() {
                private int count = 0;

                @Override
                public boolean hasNext() {
                    return count < indexes.length;
                }

                @Override
                public Object next() {
                    BitSet copy = new BitSet(bitSet.length());

                    for (int i = 0; i < bitSet.length(); i++)
                        copy.set(i, bitSet.get(i));

                    copy.flip(indexes[count++]);

                    return copy;
                }
            };

            return it;
        }
    }

    public static class SingleBitMutator implements Evolver.Mutator<BitSet> {
        final NeighborFunctions.SingleBitFlipper flipper = new NeighborFunctions.SingleBitFlipper();

        @Override
        public BitSet mutate(BitSet bitSet) {
            return flipper.getNeighbors(bitSet).next();
        }
    }

    public static class SingleWeightMutator implements Evolver.Mutator<List<Double>> {
        Random r = new Random();
        @Override
        public List<Double> mutate(List<Double> l) {
            List<Double> mutated = new ArrayList(l);
            mutated.set(r.nextInt(l.size()), r.nextDouble() * 2 - 1);
            return mutated;
        }
    }

    public static class UniformCrossover implements Evolver.Breeder<BitSet> {
        final Random r = new Random();

        @Override
        public BitSet breed(BitSet m, BitSet d) {
            BitSet child = new BitSet();
            for (int i = 0; i < m.length() - 1; i++)
                child.set(i, r.nextBoolean() ? m.get(i) : d.get(i));
            child.set(m.length() - 1, true);
            return child;
        }
    }

    public static class ListUniformCrossover implements Evolver.Breeder<List> {
        final Random r = new Random();

        @Override
        public List breed(List m, List d) {
            List child = new ArrayList();
            for (int i = 0; i < m.size(); i++)
                child.add(r.nextBoolean() ? m.get(i) : d.get(i));

            return child;
        }
    }

    public static class SingleCrossover implements Evolver.Breeder<BitSet> {
        final Random r = new Random();

        @Override
        public BitSet breed(BitSet m, BitSet d) {
            BitSet child = new BitSet();
            int crossPoint = r.nextInt(d.length() - 1);

            for (int i = 0; i < m.length() - 1; i++)
                child.set(i, i < crossPoint ? m.get(i) : d.get(i));
            child.set(m.length() - 1, true);
            return child;
        }
    }
}
