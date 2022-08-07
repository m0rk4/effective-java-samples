package by.morka.effective.java.objectscreationdestruction.singleton;

public class ElvisSecond {

    private static final ElvisSecond INSTANCE =  new ElvisSecond();

    private ElvisSecond() {
        throw new AssertionError();
    }

    /**
     * 1. More freedom, not necessary to return instance always, can be configured.
     */
    public static ElvisSecond getInstance() {
        return INSTANCE;
    }

    public void doSomeJob() {
    }
}
