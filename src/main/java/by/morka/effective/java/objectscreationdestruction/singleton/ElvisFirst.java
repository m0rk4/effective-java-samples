package by.morka.effective.java.objectscreationdestruction.singleton;

import java.io.Serial;
import java.io.Serializable;

public class ElvisFirst implements Serializable {
    /**
     * 1. Simpler, but not configurable
     */
    public static final ElvisFirst INSTANCE = new ElvisFirst();

    private ElvisFirst() {
        throw new AssertionError("Can't get there");
    }

    public void doSomeJob() {
    }

    @Serial
    private Object readResolve() {
        return INSTANCE;
    }
}
