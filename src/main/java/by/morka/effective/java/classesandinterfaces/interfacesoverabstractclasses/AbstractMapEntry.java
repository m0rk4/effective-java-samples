package by.morka.effective.java.classesandinterfaces.interfacesoverabstractclasses;

import java.util.Map;
import java.util.Objects;

/*
 Skeletal implementation class is nice application for abstract classes
 1. define skeleton operations
 2. build functionality on skeleton operations
 3. inherit it and make work easier
 P.S at the same time you can implement interface
 */
public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {

    // Entries in a modifiable map must override this method
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    /*
     Implements the general contract of Map.Entry.equals
          We are not allowed to define default object methods impls in interfaces, so we do it here
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?, ?> e = (Map.Entry) o;
        return Objects.equals(e.getKey(), getKey())
                && Objects.equals(e.getValue(), getValue());
    }


    /*
     Implements the general contract of Map.Entry.hashCode
     We are not allowed to define default object methods impls in interfaces, so we do it here
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getKey())
                ^ Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }
}
