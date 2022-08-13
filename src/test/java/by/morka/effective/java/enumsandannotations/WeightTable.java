package by.morka.effective.java.enumsandannotations;

import by.morka.effective.java.enumsandannotations.useenumsinsteadofints.Planet;
import org.junit.jupiter.api.Test;

public class WeightTable {

    @Test
    public void main() {
        double earthWeight = Double.parseDouble("76");
        double mass = earthWeight / Planet.EARTH.surfaceGravity();
        for (Planet p : Planet.values())
            System.out.printf("Weight on %s is %f%n",
                    p, p.surfaceWeight(mass));
    }
}
