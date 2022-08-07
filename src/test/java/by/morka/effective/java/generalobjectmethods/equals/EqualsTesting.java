package by.morka.effective.java.generalobjectmethods.equals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EqualsTesting {

    @Test
    public void testWithInstanceOf() {
        Sample s = new Sample(1, 2);
        SubSample ss = new SubSample(1,1,2);

        // but in fact they are different
        assertEquals(ss, s);
        assertEquals(s, ss);
    }

    @Test
    public void testWithGetClass() {
        AnotherSample s = new AnotherSample("dada");
        SubAnotherSample ss = new SubAnotherSample("dada", 123);

        assertNotEquals(ss, s);
        assertNotEquals(s, ss);
    }
}
