package by.morka.effective.java.objectscreationdestruction.dependencyinjection;

import java.util.function.Supplier;

public class SpellChecker {

    private final Dictionary dictionary;

    /**
     * Same applies for static factory methods, builder pattern
     */
    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        return dictionary.exists(word);
    }
}
