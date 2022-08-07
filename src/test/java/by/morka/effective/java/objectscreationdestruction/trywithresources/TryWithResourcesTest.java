package by.morka.effective.java.objectscreationdestruction.trywithresources;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TryWithResourcesTest {

    @Test
    public void test() {
        /*
        Avoid try-finally nesting hell.
        If your resources should be closed, use Autocloseable interface
         */
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{1, 2});
             InputStreamReader in = new InputStreamReader(byteArrayInputStream);
             BufferedReader reader = new BufferedReader(in)) {
            assertEquals(1, reader.read());
            assertEquals(2, reader.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
