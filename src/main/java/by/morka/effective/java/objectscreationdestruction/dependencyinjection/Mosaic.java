package by.morka.effective.java.objectscreationdestruction.dependencyinjection;

import java.util.List;
import java.util.function.Supplier;

public class Mosaic {
    private Mosaic(List<Tile> tiles) {}

    /**
     * Passing resource factory to constructor, builder, static factory method
     */
    Mosaic create(Supplier<? extends Tile> tileFactory) {
        final Tile tile = tileFactory.get();
        final List<Tile> tiles = List.of(tileFactory.get(), tileFactory.get());
        return new Mosaic(tiles);
    }
}
