package by.morka.effective.java.concurrency.avoidexcessivesynch;

@FunctionalInterface
interface SetObserver<E> {
    void added(ObservableSet<E> set, E element);
}

