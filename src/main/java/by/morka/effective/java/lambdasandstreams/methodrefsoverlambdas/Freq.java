package by.morka.effective.java.lambdasandstreams.methodrefsoverlambdas;

import java.util.Map;
import java.util.TreeMap;

/*
RULE IS SIMPLE: Use method references where it is more readable and shorter.
 */
public class Freq {
    public static void main(String[] args) {
        Map<String, Integer> frequencyTable = new TreeMap<>();

        for (String s : args)
            frequencyTable.merge(s, 1, (count, incr) -> count + incr); // Lambda
        System.out.println(frequencyTable);

        frequencyTable.clear();
        for (String s : args)
            frequencyTable.merge(s, 1, Integer::sum); // Method reference
        System.out.println(frequencyTable);

    }
}
