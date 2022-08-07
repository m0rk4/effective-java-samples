package by.morka.effective.java.objectscreationdestruction.staticfactory;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyIterator;
import static java.util.Collections.emptyListIterator;

/**
 * COPIED FROM java.util.Collections.*
 */
public class CollectionsClone {

    @SuppressWarnings("rawtypes")
    public static final List EMPTY_LIST = new EmptyList<>();

    private CollectionsClone() {
    }

    /**
     * 1. Using static factory methods we can return sub-types of type (even private ones as there).
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> emptyList() {
        return (List<T>) EMPTY_LIST;
    }

    /**
     * @serial include
     */
    private static class EmptyList<E>
            extends AbstractList<E>
            implements RandomAccess, Serializable {
        @Serial
        private static final long serialVersionUID = 8842843931221139166L;

        public Iterator<E> iterator() {
            return emptyIterator();
        }

        public ListIterator<E> listIterator() {
            return emptyListIterator();
        }

        public int size() {
            return 0;
        }

        public boolean isEmpty() {
            return true;
        }

        public void clear() {
        }

        public boolean contains(Object obj) {
            return false;
        }

        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        public Object[] toArray() {
            return new Object[0];
        }

        public <T> T[] toArray(T[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        public E get(int index) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        public boolean equals(Object o) {
            return (o instanceof List) && ((List<?>) o).isEmpty();
        }

        public int hashCode() {
            return 1;
        }

        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            return false;
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
        }

        @Override
        public void sort(Comparator<? super E> c) {
        }

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.emptySpliterator();
        }

        // Preserves singleton property
        @java.io.Serial
        private Object readResolve() {
            return EMPTY_LIST;
        }
    }
}
