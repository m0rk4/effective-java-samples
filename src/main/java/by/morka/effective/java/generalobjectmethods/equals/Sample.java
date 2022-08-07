package by.morka.effective.java.generalobjectmethods.equals;

public class Sample {

    private final int x;

    private final int y;

    public Sample(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
    1. Reflexive: x.equals(x) = true
    2. Symmetric: x.equals(y) = true <=> y.equals(x) = true
    3. Transitive: x.equals(y) = true + y.equals(z) = true => x.equals(z) = true
    4. Consistent: x.equals(y) = result => over time if objects are not changed x.equals(y) = result as well
    5. For any non-null reference value x, x.equals(null) should return false
     */
    @Override
    public final boolean equals(Object obj) {
        /*
         * Such implementation covers cases with subclasses,
         * but take into account that new fields within subclasses
         * are not gonna be taken into account.
         *
         * Make it final because there is no way to override it without breaking any of 5 rules above.
         */
        if (this == obj)
            return true;

        if (!(obj instanceof Sample))
            return false;

        Sample o = (Sample) obj;
        return x == o.x && y == o.y;
    }
}
