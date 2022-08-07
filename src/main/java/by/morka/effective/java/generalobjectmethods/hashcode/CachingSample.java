package by.morka.effective.java.generalobjectmethods.hashcode;

import java.util.Arrays;

public class CachingSample {

    private final String[] s;

    public CachingSample(String[] s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CachingSample)) return false;
        CachingSample that = (CachingSample) o;
        return Arrays.equals(s, that.s);
    }

    int hashcode = 0;

    @Override
    public int hashCode() {
        /*
        If perfomance matters.
        For immutable objects only
         */
        if (hashcode == 0) {
            hashcode = Arrays.hashCode(s);
        }
        return hashcode;
    }
}
