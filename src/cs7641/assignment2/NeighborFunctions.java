package cs7641.assignment2;

import java.util.*;
import java.util.function.Consumer;

public class NeighborFunctions {
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

    public static class SingleBitFlipper implements NeighborFunction<BitSet> {

        @Override
        public Iterator<BitSet> getNeighbors(final BitSet bitSet) {
            final int[] indexes = new int[bitSet.length() - 1];
            for (int i = 0; i < indexes.length; i++)
                indexes[i] = i;

            Random r = new Random();
            // Shuffle it
            for (int i = 0; i < indexes.length; i++) {
                int idx = r.nextInt(i + 1);

                int tmp = indexes[idx];

                indexes[idx] = indexes[i];
                indexes[i] = tmp;
            }

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

                @Override
                public void remove() {
                    throw new RuntimeException("Not implemented");
                }

                @Override
                public void forEachRemaining(Consumer action) {
                    throw new RuntimeException("Not implemented");
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
