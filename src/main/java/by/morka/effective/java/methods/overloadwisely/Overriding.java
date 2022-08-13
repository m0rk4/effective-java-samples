package by.morka.effective.java.methods.overloadwisely;

import java.util.List;

/**
 * Prefer not to export two overloads with same amount of params!!
 */
// Classification using method overriding
public class Overriding {
    public static void main(String[] args) {
        List<Wine> wineList = List.of(
                new Wine(), new SparklingWine(), new Champagne());

        // Will print 'wine' 'champagne' etc.
        // Because implementation of overridden methods is selected dynamically!
        for (Wine wine : wineList)
            System.out.println(wine.name());
    }
}
