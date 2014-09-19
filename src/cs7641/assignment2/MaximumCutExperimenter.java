package cs7641.assignment2;

import java.text.DecimalFormat;
import java.util.BitSet;
import java.util.Random;

public class MaximumCutExperimenter {
    public static void main(String[] args) {
        Random r = new Random(31);

        int nodes = 20;

        Double[][] adjacency = new Double[nodes][nodes];

        for (int i = 0; i < nodes; i++) {
            for (int j = i + 1; j < nodes; j++) {
                if (r.nextBoolean()) {
                    adjacency[i][j] = adjacency[j][i] = r.nextBoolean() ? (double)r.nextInt(10) : (double)r.nextInt(3);
                } else {
                    adjacency[i][j] = adjacency[j][i] = null;
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                System.out.print((adjacency[i][j] == null ? "null" : df.format(adjacency[i][j])) + ", ");
            }
            System.out.println();
        }

        MaximumCut mc0 = new MaximumCut(adjacency);
        Mimicker m = new Mimicker(mc0, 200, 5);
        m.train(1000);

        MaximumCut mc = new MaximumCut(adjacency);

        for (int i = 0; i < 10; i++) {
            HillClimber<BitSet> hc = new HillClimber(mc, new NeighborFunctions.SingleBitFlipper());
            hc.train(12000);
        }

        final NeighborFunctions.SingleBitFlipper flipper = new NeighborFunctions.SingleBitFlipper();
        MaximumCut mc2 = new MaximumCut(adjacency);
        Evolver.Mutator<BitSet> mutator = new Evolver.Mutator<BitSet>() {
            @Override
            public BitSet mutate(BitSet bitSet) {
                return flipper.getNeighbors(bitSet).get(0);
            }
        };
        final Random r2 = new Random();
        Evolver.Breeder<BitSet> breeder = new Evolver.Breeder<BitSet>() {
            @Override
            public BitSet breed(BitSet m, BitSet d) {
                BitSet child = new BitSet();
                for (int i = 0; i < m.length() - 1; i++)
                    child.set(i, r2.nextBoolean() ? m.get(i) : d.get(i));
                child.set(m.length() - 1, true);
                return child;
            }
        };
        Evolver<BitSet> ev = new Evolver<BitSet>(mc2, 100, 50, breeder, mutator, 10);
        ev.train(10000);
    }
}
