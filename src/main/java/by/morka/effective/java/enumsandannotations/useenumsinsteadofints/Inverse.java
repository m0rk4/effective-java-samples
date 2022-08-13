package by.morka.effective.java.enumsandannotations.useenumsinsteadofints;

// Switch on an enum to simulate a missing method
public class Inverse {
    /**
     * Switch is applicable when:
     * 1. Enum is NOT UNDER OUR CONTROL
     * 2. ENUM is UNDER OUR CONTROL but the method ISN'T USEFUL ENOUGH TO BE IN ENUM
     */
    public static Operation inverse(Operation op) {
        return switch (op) {
            case PLUS -> Operation.MINUS;
            case MINUS -> Operation.PLUS;
            case TIMES -> Operation.DIVIDE;
            case DIVIDE -> Operation.TIMES;
        };
    }

    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        for (Operation op : Operation.values()) {
            Operation invOp = inverse(op);
            System.out.printf("%f %s %f %s %f = %f%n",
                    x, op, y, invOp, y, invOp.apply(op.apply(x, y), y));
        }
    }
}
