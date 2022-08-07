package by.morka.effective.java.generalobjectmethods.equals;

import java.util.Objects;

public class AnotherSample {

    private final String s;

    public AnotherSample(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object obj) {
        /*
         * This case is also a valid one.
         * but with subtypes it won't work, because of the getClass().
         * Also breaks Liskov substitution principle in some cases, cause for sutypes it is always FALSE
         */
        if (this == obj)
            return true;

        if (obj == null || (obj.getClass() != getClass()))
            return false;

        AnotherSample o = (AnotherSample) obj;
        return Objects.equals(s, o.s);
    }
}
