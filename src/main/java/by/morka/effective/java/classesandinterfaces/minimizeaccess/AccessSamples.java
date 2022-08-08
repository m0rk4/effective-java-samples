package by.morka.effective.java.classesandinterfaces.minimizeaccess;

import java.util.List;

/**
 * Minimize access to class internals.
 * public static final is allowed only (with not-mutable objects ofc)
 */
public class AccessSamples {

    private AccessSamples() {
        throw new AssertionError();
    }

    // BAD!
    public static final Thing[] THINGS = {};

    // BETTER
    private static final Thing[] PRIVATE_THINGS = {};
    public static final List<Thing> THING_LIST = List.of(PRIVATE_THINGS);

    // ONE MORE ALTERNATIVE
    private static final Thing[] PRIVATE_THINGS_SECOND = {};

    public static final Thing[] things() {
        return PRIVATE_THINGS_SECOND.clone();
    }

    class Thing {
    }
}
