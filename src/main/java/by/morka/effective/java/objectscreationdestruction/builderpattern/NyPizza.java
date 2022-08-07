package by.morka.effective.java.objectscreationdestruction.builderpattern;

import java.util.Objects;

import static by.morka.effective.java.objectscreationdestruction.builderpattern.Pizza.Topping.ONION;
import static by.morka.effective.java.objectscreationdestruction.builderpattern.Pizza.Topping.SAUSAGE;

public class NyPizza extends Pizza {
    private final Size size;

    NyPizza pizza = new NyPizza.Builder(Size.SMALL)
            .addTopping(SAUSAGE).addTopping(ONION).build();

    private NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    public enum Size {SMALL, MEDIUM, LARGE}

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
