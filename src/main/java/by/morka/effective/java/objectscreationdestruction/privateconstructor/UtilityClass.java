package by.morka.effective.java.objectscreationdestruction.privateconstructor;

public class UtilityClass {
    /**
     * Exception to protect from reflective access.
     */
    private UtilityClass() {
        throw new AssertionError();
    }

    public String job() {
        return "job";
    }
}
