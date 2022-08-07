package by.morka.effective.java.objectscreationdestruction.staticfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * API FOR PROVIDER REGISTRATIONS
 */
public class MyDriverManager {

    private static final List<MyDriver> REGISTERED_DRIVERS = new ArrayList<>();

    public void registerDriver(MyDriver driver) {
        REGISTERED_DRIVERS.add(driver);
    }

    /**
     * 1. The class
     * of the returned object does not have to exist during the development
     * of the class containing the method
     */
    public MyConnection getConnection(String url, Properties properties) {
        // some logic
        return REGISTERED_DRIVERS.get(0).connect(url, properties);
    }
}
