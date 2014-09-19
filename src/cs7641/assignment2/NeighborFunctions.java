package cs7641.assignment2;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class NeighborFunctions {
    public static class SingleRandomWeightUpdater implements NeighborFunction<List<Double>> {
        @Override
        public List<List<Double>> getNeighbors(List<Double> weights) {
            List<List<Double>> neighbors = new ArrayList<List<Double>>();

            Random r = new Random(System.currentTimeMillis());

            int idx = (int) (Math.random() * weights.size());

            List<Double> copy = new ArrayList(weights);
            copy.set(idx, copy.get(idx) + Math.random() * 2 - 1);
            neighbors.add(copy);

            return neighbors;
        }
    }

    public static class SingleBitFlipper implements NeighborFunction<BitSet> {
        private Random r = new Random();
        @Override
        public List<BitSet> getNeighbors(BitSet bitSet) {
            BitSet copy = new BitSet(bitSet.length());

            for (int i = 0; i < bitSet.length(); i ++)
                copy.set(i, bitSet.get(i));

            int idx = r.nextInt(copy.length() - 1);
            copy.flip(idx);

            List<BitSet> ret = new ArrayList();
            ret.add(copy);

            return ret;
        }
    }
}
