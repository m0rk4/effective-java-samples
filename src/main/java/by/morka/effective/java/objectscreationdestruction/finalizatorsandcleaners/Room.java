package by.morka.effective.java.objectscreationdestruction.finalizatorsandcleaners;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();

    // Ресурс, требующий очистки. He должен ссылаться на Room?
    private static class State implements Runnable {
        int numJunkPiles; // Количество мусора в комнате

        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        // Вызывается методом close или cleaner
        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    // Состояние комнаты, используется совместно с cleanable
    private final State state;
    // Очистка комнаты, когда она готова для сборщика мусора
    private final Cleaner.Cleanable cleanable;

    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() {
        cleanable.clean();
    }
}
