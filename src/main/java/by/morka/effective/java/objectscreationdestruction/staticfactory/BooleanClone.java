package by.morka.effective.java.objectscreationdestruction.staticfactory;

public class BooleanClone {
    private static final BooleanClone TRUE = new BooleanClone(true);

    private static final BooleanClone FALSE = new BooleanClone(false);

    private final boolean value;

    /**
     * Instance-controlled class
     * Singleton
     * Not Instantiatable.
     * (Flyweight pattern?)
     */
    private BooleanClone(boolean b) {
        this.value = b;
    }

    /**
     * 1. Can be named nicely.
     * 2. Doesn't have to return new values all the time comparing to the constructor (Flyweight pattern?).
     */
    public static BooleanClone valueOf(boolean b) {
        return b ? TRUE : FALSE;
    }

    public boolean getValue() {
        return this.value;
    }
}
