package by.morka.effective.java.objectscreationdestruction.staticfactory;

import java.util.Properties;

/**
 * SERVICE PROVIDER
 */
public interface MyDriver {
    MyConnection connect(String url, Properties properties);
}
