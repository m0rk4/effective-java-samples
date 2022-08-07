package by.morka.effective.java.generalobjectmethods.hashcode;

import java.util.Objects;

public class HashSample {

    private final int x;

    public HashSample(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashSample)) return false;
        HashSample that = (HashSample) o;
        return x == that.x;
    }

    /*
     * OVERRIDE EVERY TIME WHEN EQUALS() IS OVERRIDDEN
     * 1. Consistent: x.hashCode() = result => over time if objects are not changed x.hashCode() = result as well
     * 2. If two objects are equal in the result equals(), then when calling the hashCode method,
     *                               the same integer values should be obtained for each of them.
     * 3. If the equals (Ob j ect) method states that two objects are not equal to each other,
     *                               it does not mean that the hashCode method will return different
                                     numbers for them. However, the programmer should understand that
                                     generating different numbers for unequal objects can improve the
                                     performance of hash tables.
     */
    @Override
    public int hashCode() {
        /*
        If perfomance is not necessary -> hash() can be used
         */

//        return Objects.hash(x);
        return 31 * Integer.hashCode(x);
    }
}
