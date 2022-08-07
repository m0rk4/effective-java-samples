package by.morka.effective.java.objectscreationdestruction.outdatedrefs;

import java.util.Arrays;

/**
 * 1. When class manages memory itself, the programmer has to take care of possible memory issues
 * 2. Same for caches, invalidation usually hard task
 * 3. Same for collection callbacks (weak refs might help?)
 */
public class StackClone {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private Object[] objects;

    private int size = 0;

    public StackClone() {
        this.objects = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object item) {
        ensureCapacity();
        this.objects[size++] = item;
    }

    public Object pop() {
        if (size == 0)
            throw new IllegalStateException("Empty");

        final Object object = objects[--size];
        // Old ref removal
        objects[size] = null;
        return object;
    }

    private void ensureCapacity() {
        if (size == objects.length) {
            objects = Arrays.copyOf(objects, objects.length * 2 + 1);
        }
    }
}
