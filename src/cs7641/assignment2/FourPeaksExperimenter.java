package cs7641.assignment2;

import java.util.BitSet;
import java.util.Random;

public class FourPeaksExperimenter {
    public static void main(String[] args) {

        FourPeaks p0 = new FourPeaks(80);
        Mimicker m = new Mimicker(p0, 100, 10);
        m.train(1000);

        FourPeaks p = new FourPeaks(80);
        HillClimber<BitSet> hc = new HillClimber<BitSet>(p, new NeighborFunctions.SingleBitFlipper());
        hc.train(28800);

        FourPeaks p2 = new FourPeaks(80);
        Annealer<BitSet> an = new Annealer<BitSet>(p2, new NeighborFunctions.SingleBitFlipper(), 1E11, 0.95);
        an.train(28800);

        final Random r = new Random();
        final NeighborFunctions.SingleBitFlipper flipper = new NeighborFunctions.SingleBitFlipper();
        FourPeaks p3 = new FourPeaks(80);
        Evolver.Mutator<BitSet> mutator = new Evolver.Mutator<BitSet>() {
            @Override
            public BitSet mutate(BitSet bitSet) {
                return flipper.getNeighbors(bitSet).get(0);
            }
        };
        Evolver.Breeder<BitSet> breeder = new Evolver.Breeder<BitSet>() {
            @Override
            public BitSet breed(BitSet m, BitSet d) {
                BitSet child = new BitSet();
                for (int i = 0; i < m.length() - 1; i++)
                    child.set(i, r.nextBoolean() ? m.get(i) : d.get(i));
                child.set(m.length() - 1, true);
                return child;
            }
        };
        Evolver<BitSet> ev = new Evolver<BitSet>(p3, 100, 50, breeder, mutator, 10);
        ev.train(10000);
    }
}
