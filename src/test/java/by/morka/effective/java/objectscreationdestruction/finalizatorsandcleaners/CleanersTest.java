package by.morka.effective.java.objectscreationdestruction.finalizatorsandcleaners;

import org.junit.jupiter.api.Test;

/**
 * 1. AVOID cleaners and finalizers
 */
public class CleanersTest {

    @Test
    public void testWithTryWithResources() {
        try (Room myRoom = new Room(8)) {
            System.out.println("Working...");
        }
    }

    @Test
    public void testWithCleaner() {
        Room myRoom = new Room(123);
        System.out.println("Working...");
        // even System.gc() doesn't do cleanup on my machine :(
        System.gc();
    }
}
