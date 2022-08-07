package by.morka.effective.java.objectscreationdestruction.extraobjectscreation;

import java.util.regex.Pattern;

public class RomanNumerals {
    /**
     * 1. Compile first then do the job (See implementation to identify possible extra creations -> try to avoid)
     */
    private static final Pattern ROMAN =
            Pattern.compile("Л(?=.)M*(C[MD]|D?C{0,3})"
                    + "(X[CL]IL?X{0,3})(I[XV]|V?I{0,3})$");

    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }

    /**
     * 2. Prefer primitives over wrapper ones where possible.
     */
    private static long  sum() {
        // NEVER DO!
        Long x = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            x += i;
        }
        return x;
    }
}

