package by.morka.effective.java.objectscreationdestruction.builderpattern;

import static by.morka.effective.java.objectscreationdestruction.builderpattern.Pizza.Topping.HAM;

public class Calzone extends Pizza {
    private final boolean sauceInside;

    Calzone calzone = new Calzone.Builder()
            .addTopping(HAM).sauceInside().build();

    private Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        public Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
