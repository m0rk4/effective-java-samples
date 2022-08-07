package by.morka.effective.java.generalobjectmethods.clone;

import java.util.Objects;

/**
 * USE COPY CONSTRUCTORS AND STATIC FACTORY METHODS!
 */
public class CloneSample implements Cloneable {

    /**
     * Primitive - super clone is fine
     */
    private final int x;

    /**
     * Immutable Reference - super clone is fine
     */
    private final String s;

    /**
     * Not Immutable Reference - super clone is not fine, perform one more for this field specifically
     */
    private Object o;

    /**
     * Not immutable reference - after super clone we CAN'T reassign - BAD!!!!
     */
    private final Object aa = new Object();

    public CloneSample(int x, String s) {
        this.x = x;
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloneSample that = (CloneSample) o;
        return x == that.x &&
                Objects.equals(s, that.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, s);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
