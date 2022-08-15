package by.morka.effective.java.concurrency.avoidexcessivesynch;

import by.morka.effective.java.classesandinterfaces.compositionoverinheritance.ForwardingSet;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObservableSet<E> extends ForwardingSet<E> {
    public ObservableSet(Set<E> s) {
        super(s);
    }

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAddedBad(E element) {
        synchronized (observers) {
            for (SetObserver<E> observer : observers)
                /*
                Calling client method - DANGER!
                 */
                observer.added(this, element);
        }
    }

    private void notifyElementAddedBetter(E element) {
        List<SetObserver<E>> snapshot;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }
        // client method is moved outside sync block
        for (SetObserver<E> observer : snapshot)
            observer.added(this, element);
    }

    @Override
    public boolean add(E e) {
        final boolean added = super.add(e);
        if (added)
            notifyElementAddedBad(e);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c)
            result |= add(element);
        return result;
    }
}

class ConcurrentModificationExceptionSample {
    public static void main(String[] args) {
        final ObservableSet<Integer> observableSet = new ObservableSet<>(new HashSet<>());
        observableSet.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);

                if (element == 23)
                    set.removeObserver(this);
            }
        });

        for (int i = 0; i < 100; i++)
            observableSet.add(i);
    }
}

class DeadlockSample {
    public static void main(String[] args) {
        final ObservableSet<Integer> observableSet = new ObservableSet<>(new HashSet<>());
        observableSet.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);

                if (element == 23) {
                    final ExecutorService executor = Executors.newSingleThreadExecutor();

                    try {
                        executor.submit(() -> set.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        executor.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++)
            observableSet.add(i);
    }
}
