package by.morka.effective.java.classesandinterfaces.interfacesoverabstractclasses;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

// Concrete implementation built atop skeletal implementation
public class IntArrays {
    static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        /*
        Nice application of skeleton implementations!
         */
        return new AbstractList<>() {
            @Override
            public Integer get(int i) {
                return a[i];  // Autoboxing, performance suffers :(
            }

            @Override
            public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // Auto-unboxing
                return oldVal;  // Autoboxing
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }
}
