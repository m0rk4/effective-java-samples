package by.morka.effective.java.concurrency.avoidexcessivesynch;

import by.morka.effective.java.classesandinterfaces.compositionoverinheritance.ForwardingSet;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class BetterObservableSet<E> extends ForwardingSet<E> {
    public BetterObservableSet(Set<E> set) {
        super(set);
    }

    // Thread-safe observable set with CopyOnWriteArrayList
    private final List<SetObserver<E>> observers =
            new CopyOnWriteArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        observers.add(observer);
    }

    public boolean removeObserver(SetObserver<E> observer) {
        return observers.remove(observer);
    }

    private void notifyElementAdded(E element) {
        // Types doesn't match but logic is right
//        for (SetObserver<E> observer : observers)
//            observer.added(this, element);
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c)
            result |= add(element);  // Calls notifyElementAdded
        return result;
    }
}
