import java.util.BitSet;
import java.util.List;
import java.util.stream.Stream;

public class Universe {
    private final List<Galaxy> galaxies;
    private final int width;
    private final int height;

    public Universe(List<Galaxy> galaxies, int width, int height) {
        this.galaxies = galaxies;
        this.width = width;
        this.height = height;
    }

    public Universe expand(int expansionFactor) {
        BitSet emptyColumns = initBitset(width);
        galaxies.stream().map(Galaxy::getPosition).mapToInt(Position::x).forEach(emptyColumns::clear);
        BitSet emptyRows = initBitset(height);
        galaxies.stream().map(Galaxy::getPosition).mapToInt(Position::y).forEach(emptyRows::clear);
        List<Galaxy> expandedGalaxies = galaxies.stream().map(galaxy -> galaxy.expand(emptyColumns, emptyRows, expansionFactor)).toList();
        final int k = expansionFactor - 1;
        return new Universe(expandedGalaxies, width + emptyColumns.cardinality() * k, height + emptyRows.cardinality() * k);
    }

    private BitSet initBitset(int size) {
        BitSet bitset = new BitSet(size);
        bitset.set(0, size);
        return bitset;
    }

    public Stream<Galaxy.Pair> getGalaxyPairs() {
        return galaxies.stream().flatMap(a -> galaxies.stream().filter(b -> b.getId() > a.getId()).map(b -> new Galaxy.Pair(a, b)));
    }
}
