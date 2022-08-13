package by.morka.effective.java.enumsandannotations.enumsetinsteadofbitfields;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

// EnumSet - a modern replacement for bit fields
public class Text {
    public enum Style {BOLD, ITALIC, UNDERLINE, STRIKETHROUGH}

    // Any Set could be passed in, but EnumSet is clearly best
    public void applyStyles(Set<Style> styles) {
        System.out.printf("Applying styles %s to text%n",
                Objects.requireNonNull(styles));
    }

    // Sample use
    public static void main(String[] args) {
        Text text = new Text();
        /*
        EnumSet uses same bit fields under the hood, but its API is safe and well formed!
         */
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
    }
}
