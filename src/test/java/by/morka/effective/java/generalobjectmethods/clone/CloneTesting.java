package by.morka.effective.java.generalobjectmethods.clone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CloneTesting {

    @Test
    public void testCloneCloneable() throws CloneNotSupportedException {
        CloneSample cloneSample = new CloneSample(1, " 23");

        final CloneSample clone = (CloneSample) cloneSample.clone();

        assertNotSame(cloneSample, clone);
        assertEquals(cloneSample, clone);
    }

    @Test
    public  void testCloneNotCloneable() {
        assertThrows(CloneNotSupportedException.class, () -> {
            final NotCloneSample notCloneSample = new NotCloneSample();

            notCloneSample.clone();
        });
    }
}
