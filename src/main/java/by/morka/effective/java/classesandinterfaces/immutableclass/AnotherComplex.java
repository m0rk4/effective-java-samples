package by.morka.effective.java.classesandinterfaces.immutableclass;

import java.math.BigInteger;

/*
It is final actually because it has no public constructors
Moreover its static factory can return subclasses (PACKAGE PRIVATE ONES, which is great, the client won't know)
 */
public class AnotherComplex {
    /*
        1. Biginteger is not final, thus can be extended, which is dangerous
        2. If your safety depends on Biginteger received from not trustable client, use such algo
     */
    public static BigInteger safeInstance(BigInteger bigInteger) {
        if (bigInteger.getClass() == BigInteger.class) {
            // safe, real biginteger
            return bigInteger;
        }
        // subclass, we need protected copy
        return new BigInteger(bigInteger.toByteArray());
    }

    private final double re;
    private final double im;

    public static final AnotherComplex ZERO = new AnotherComplex(0, 0);
    public static final AnotherComplex ONE = new AnotherComplex(1, 0);
    public static final AnotherComplex I = new AnotherComplex(0, 1);

    // Private
    private AnotherComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    public AnotherComplex plus(AnotherComplex c) {
        return new AnotherComplex(re + c.re, im + c.im);
    }

    // Static factory, used in conjunction with private constructor
    // Caching, etc. to minimize instantiation
    public static AnotherComplex valueOf(double re, double im) {
        return new AnotherComplex(re, im);
    }

    public AnotherComplex minus(AnotherComplex c) {
        return new AnotherComplex(re - c.re, im - c.im);
    }

    public AnotherComplex times(AnotherComplex c) {
        return new AnotherComplex(re * c.re - im * c.im,
                re * c.im + im * c.re);
    }

    public AnotherComplex dividedBy(AnotherComplex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new AnotherComplex((re * c.re + im * c.im) / tmp,
                (im * c.re - re * c.im) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AnotherComplex))
            return false;
        AnotherComplex c = (AnotherComplex) o;

        return Double.compare(c.re, re) == 0
                && Double.compare(c.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}
