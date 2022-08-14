# effective-java-samples
_Effective Java v3 / Programming Language Guide By Joshua Bloch / Samples with notes_

_Represents a diary in the format of git commits + README quick reference_

# CONTENTS:
- [2. Creating and destroying objects](#2-creating-and-destroying-objects)
    - [1. Use static factory methods](#1-use-static-factory-methods)
    - [2. Use builder pattern when telescoping constructors faced](#2-use-builder-pattern-when-telescoping-constructors-faced)
    - [3. Enforce the singleton property with a private constructor or an enum type](#3-enforce-the-singleton-property-with-a-private-constructor-or-an-enum-type)
    - [4. Enforce noninstantiability with a private constructor](#4-enforce-noninstantiability-with-a-private-constructor)
    - [5. DI](#5-di)
    - [6. Avoid extra objects creation](#6-avoid-extra-objects-creation)
    - [7. Eliminate obsolete object references](#7-eliminate-obsolete-object-references)
    - [8. Try-with-resources](#8-try-with-resources)
    - [9. Avoid cleaners and finalizers](#9-avoid-cleaners-and-finalizers)
- [3. Methods common to all objects](#3-methods-common-to-all-objects)
    - [10. Equals](#10-equals)
    - [11. Hashcode](#11-hashcode)
    - [12. ToString](#12-tostring)
    - [13. Clone](#13-clone)
    - [14. Comparable](#14-comparable)
- [4. Classes and interfaces](#4-classes-and-interfaces)
    - [15. Minimize access](#15-minimize-access)
    - [16. Use accessor methods](#16-use-accessor-methods)
    - [17. Minimize mutability](#17-minimize-mutability)
    - [18. Composition over inheritance <3](#18-composition-over-inheritance-3)
    - [19. Design and document for inheritance (or else prohibit it :))](#19-design-and-document-for-inheritance-or-else-prohibit-it-)
    - [20. Interfaces over abstract classes](#20-interfaces-over-abstract-classes)
    - [21. Favor static member classes over nonstatic](#21-favor-static-member-classes-over-nonstatic)
- [5. Generics](#5-generics)  
    - [22. Lists over arrays](#22-lists-over-arrays)
    - [23. Prefer generic methods](#23-prefer-generic-methods)
    - [24. Type bound with wildcards](#24-type-bound-with-wildcards)
    - [25. Typesafe heterogeneous containers](#25-typesafe-heterogeneous-containers)
- [6. Enums and annotations](#6-enums-and-annotations)
    
# 2. Creating and destroying objects
## 1. Use static factory methods

*Pros*
* Can be named nicely;
* Doesn't have to return new values all the time comparing to the constructor (Flyweight pattern?);
    * Instance-controlled classes / singletons
* Using static factory methods we can return sub-types of type (even private ones as in `Collections.emptyList()`);
* Sub-class (`JumboEnumSet`, `RegularEnumSet`) can be returned based on specific logic;
* The class of the returned object does not have to exist during the development of the class containing the method.

*Cons*
* Can't be inherited with no public/protected constructors (but still composition over inheritance :) ?)
* Sometimes hard to differ from general purpose static methods (javadoc, naming strategy ?)

## 2. Use builder pattern when telescoping constructors faced
It is a good choice when designing classes whose constructors or static factories will have more than a few parameters (3 is the limit usually).

*Pros*
* Simulates named optional params as in Kotlin / Python / Scala;
* Applicable for class hierarchy (see `Pizza.java` commit).

## 3. Enforce the singleton property with a private constructor, or an enum type

```java
// Simpler, but not configurable
public static final ElvisFirst INSTANCE = new ElvisFirst();
```
```java
// More freedom, not necessary to return instance always, can be configured.
public static ElvisSecond getInstance() {
    return INSTANCE;
}
```
```java
// singleton out of the java box!
public enum ElvisThird {
    INSTANCE;

    public void doSomeJob() {

    }
}
```
*Cons*
* Hard to test for clients (ruins lives).

## 4. Enforce noninstantiability with a private constructor
Group static fields / methods.

```java
// protect from reflective access
private UtilityClass() {
        throw new AssertionError();
}
```

Application for:
* Utility classes?
* Constants holders
* Final classes with static factories

## 5. DI
Use constructor injection instead of tight coupling (resource instantiation directly in class).

```java
// Passing resource factory to constructor, builder, static factory method
Mosaic create(Supplier<? extends Tile> tileFactory) {
```

## 6. Avoid extra objects creation
```java
// Compile first then do the job (See implementation to identify possible extra creations -> try to avoid)
private static final Pattern ROMAN =
            Pattern.compile("Л(?=.)M*(C[MD]|D?C{0,3})"
                    + "(X[CL]IL?X{0,3})(I[XV]|V?I{0,3})$");
```

**Prefer primitives over wrapper ones where possible.**

## 7. Eliminate obsolete object references
Common situations:
* When class manages memory itself, the programmer has to take care of possible memory issues;
* Same for caches (invalidation is usually a hard task) ;
    * `WeakHashMap` when lifetime is tight to external references to the key, not the value.
    * (`LinkedHashMap.removeEldestEntry()`)
* Memory leaks in listeners and callbacks;
    * `WeakHashMap` ?

```java
public Object pop() {
        if (size == 0)
            throw new IllegalStateException("Empty");

        final Object object = objects[--size];
        // Old ref removal
        objects[size] = null;
        return object;
}
```

## 8. Try-with-resources
Rules:
* Avoid try-finally nesting hell;
* If your resources should be closed, use `Autocloseable` interface.

## 9. Avoid cleaners and finalizers
* Finalizers are unpredictable, often dangerous and generally not useful;
* Same for cleaners;
* **Never do anything time-critical in a finalizer**;
* **Never depend on a finalizer to update critical persistent state**;
* Uncaught exceptions inside a finalizer won't even print a warning.

# 3. Methods common to all objects
## 10. Equals

**Don't override if:**
* Each instance of the class is inherently unique. I.e._Thread_
* You don't care whether the class provides a "logical equality" test. I.e. _java.util.Random_
* A superclass has already overridden _equals_, and the superclass behavior is appropriate for this class I.e. _Set_
* The class is private or package-private, and you are certain  that its _equals_ method will never be invoked

**Override if:**

A class has a notion of _logical equality_ that differs from mere object identity, and a superclass has not already overridden _equals_ to implement the desired behavior.

**_Equals implements an "equivalence relation"_**

* Reflexive: *x.equals(x)==true*
* Symmetric: *x.equals(y)==y.equals(x)*
* Transitive: *x.equals(y)==y.equals(z)==z.equals(x)*
* Consistent: *x.equals(y)==x.equals(y)==x.equals(y)==...*
* Non-nullity: *x.equals(null)->false*

**_The Recipe_**

1. Use the == operator to check if the argument is a reference to this object (for performance)
2. Use the _instanceof_ operator to check if the argument has the correct type
3. Cast the argument to the correct type
4. For each "significant" field in the class, check if that field of the argument matches the corresponding field of this object
5. When you are finished writing your _equals_ method, ask yourself three questions: Is it Symmetric? Is it Transitive? Is it Consistent? (the other 2 usually take care of themselves)

```java
@Override
public boolean equals (Object o){
		if(o == this)
			return true;

		if (!(o instanceof PhoneNumber))
			return false;

		PhoneNumber pn = (PhoneNumber)o;
		return pn.lineNumber == lineNumber
			&& pn.prefix == prefix
			&& pn.areaCode == areaCode;
}
```

* Always override _hashCode_ when you override _equals_
* Don't try to be too clever (simplicity is your friend)
* Don't substitute another type for _Object_ in the _equals_ declaration

* Remember difference between `instanceof` and `getClass()` approach.
 
## 11. Hashcode 

**OVERRIDE EVERY TIME WHEN EQUALS() IS OVERRIDDEN**
* Consistent: x.hashCode() = result => over time if objects are not changed x.hashCode() = result as well
* If two objects are equal in the result equals(), then when calling the hashCode method,
                                   the same integer values should be obtained for each of them.
* If the equals(Object) method states that two objects are not equal to each other,
                                 it does not mean that the hashCode method will return different
                                     numbers for them. However, the programmer should understand that
                                     generating different numbers for unequal objects can improve the
                                     performance of hash tables.
                                     
More tips:
* Use `Objects.hash()` if performance doesn't matter
* Caching is also a solution!
     
## 12. Tostring
*Pros:*
* Easier read;
* Easier debug;

Specify format in javadoc!

## 13. Clone
```java
    /**
     * Primitive - super clone is fine
     */
    private final int x;

    /**
     * Immutable Reference - super clone is fine
     */
    private final String s;

    /**
     * Not Immutable Reference - super clone is not fine, perform one more for this field specifically
     */
    private Object o;

    /**
     * Not immutable final reference - after super clone we CAN'T reassign - BAD!!!!
     */
    private final Object aa = new Object();
```
**Has to be thread-safe!**

Prefer using copy-constructors / copy-factories.

## 14. Comparable
* Same principles as un equals contract;
* Document fact that (x.compareTo(y) = 0 == x.equals(y)) is true or false;
* For integral primitives use < and > operators;
* For floating-point fields use Float.compare or Double.compare.

# 4. Classes and Interfaces
## 15. Minimize access
* Follow encapsulation;
* Minimize access to class internals;
* `public static final` allowed only (with not-mutable objects ofc)
```java
    // BAD!
    public static final Thing[] THINGS = {};

    // BETTER
    private static final Thing[] PRIVATE_THINGS = {};
    public static final List<Thing> THING_LIST = List.of(PRIVATE_THINGS);

    // ONE MORE ALTERNATIVE
    private static final Thing[] PRIVATE_THINGS_SECOND = {};

    public static final Thing[] things() {
        return PRIVATE_THINGS_SECOND.clone();
    }
```
It is acceptable to make a private member of a public class package-private in order to test it.

## 16. Use accessor methods
* If a class is accessed outside its package, provide accessor methods.
* If a class is `package-private` or is a `private nested class`, it's ok to expose its data fields.
* In `public classes` it is a questionable option to expose immutable fields. 

## 17. Minimize mutability
* Class can be final because it has no public constructors;
* Moreover its static factory can return subclasses (PACKAGE PRIVATE ONES, which is great, the client won't know);

**Not-final classes are in danger (I am the danger, Skyler!)**
```java
    /*
        1. Biginteger is not final, thus can be extended, which is dangerous
        2. If your safety depends on Biginteger received from not trustable client, use such algo
     */
    public static BigInteger safeInstance(BigInteger bigInteger) {
        if (bigInteger.getClass() == BigInteger.class) {
            // safe, real biginteger
            return bigInteger;
        }
        // subclass, we need protected copy
        return new BigInteger(bigInteger.toByteArray());
    }
```

Tip to force class finalization:
```java
    // Static factory, used in conjunction with private constructor
    // Caching, etc. to minimize instantiation
    public static AnotherComplex valueOf(double re, double im) {
        return new AnotherComplex(re, im);
    }
```

Tip for performance (see `BigInteger` for instance)
```java
    // Expose frequently used instances to minimize instantiation
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);
```

## 18. Composition over inheritance <3
**Inheritance violates encapsulation**

Fragility causes
1. A subclass depends on the implementation details of its superclass. If the superclass change the subclass may break.
2. The superclass can acquire new methods in new releases that might not be added in the subclass.

**Composition**
Instead of extending, give your new class a private field that references an instance of the existing class.

Decorator Pattern (Wrapper) in action!
```java
// Reusable forwarding class, simply forwards methods, api is strong and independent from impl.
// Important fact is that it IMPLEMENTS, not extends
public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;
    public ForwardingSet(Set<E> s) { this.s = s; }

    public void clear()               { s.clear();            }
    public boolean contains(Object o) { return s.contains(o); }
    public boolean isEmpty()          { return s.isEmpty();   }
    public int size()                 { return s.size();      }
    public Iterator<E> iterator()     { return s.iterator();  }
    public boolean add(E e)           { return s.add(e);      }
    public boolean remove(Object o)   { return s.remove(o);   }
    public boolean containsAll(Collection<?> c)
    { return s.containsAll(c); }
    public boolean addAll(Collection<? extends E> c)
    { return s.addAll(c);      }
    public boolean removeAll(Collection<?> c)
    { return s.removeAll(c);   }
    public boolean retainAll(Collection<?> c)
    { return s.retainAll(c);   }
    public Object[] toArray()          { return s.toArray();  }
    public <T> T[] toArray(T[] a)      { return s.toArray(a); }
    @Override public boolean equals(Object o)
    { return s.equals(o);  }
    @Override public int hashCode()    { return s.hashCode(); }
    @Override public String toString() { return s.toString(); }
}
```
```java
// Decorator pattern in action!
// Wrapper class - uses composition in place of inheritance
// Now api is not broken
public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override public boolean add(E e) {
        addCount++;
        return super.add(e);
    }
    @Override public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
    public int getAddCount() {
        return addCount;
    }
}

```
## 19. Design and document for inheritance (or else prohibit it :))
* Good documentation should describe what component does, not how
    * But in case of inheritance it is impossible to omit implementation details (see `@implSpec`)
    * Also describe what overridable methods / constructors are being called
* DO NOT CALL OVERRIDABLE METHODS IN CONSTRUCTORS
    * SAME FOR `clone()` / `readObject()`
* Hooks might be useful
* Test your inheritance properly by writing subclasses

## 20. Interfaces over abstract classes
*Applications for abstract classes*
```java
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
```
```java
        /*
        Nice application of skeleton implementations!
         */
        return new AbstractList<>() {
            @Override
            public Integer get(int i) {
                return a[i];  // Autoboxing, performance suffers :(
            }

            @Override
            public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // Auto-unboxing
                return oldVal;  // Autoboxing
            }

            @Override
            public int size() {
                return a.length;
            }
        };
```

## 21. Favor static member classes over nonstatic
1. Inner static class
2. Inner non-static class
3. Anonymous (see `IntArrays.java`)
4. Local (Tuple record in java > 14?)

# 5. Generics
| **Term**                | **Example**                        |**Item**|
|-------------------------|------------------------------------|--------|
| Parametrized type       | `List<String>`                     | 23     |
| Actual type parameter   | `String`                           | 23     |
| Generic type            | `List<E>`                          | 23, 26 |
| Formal type parameter   | `E`                                | 23     |
| Unbounded wildcard type | `List<?>`                          | 23     |
| Raw type                | `List`                             | 23     |
| Bounded type parameter  | `<E extends Number>`               | 26     |
| Recursive type bound    | `<T extends Comparable<T>>`        | 27     |
| Bounded wildcard type   | `List<? extends Number>`           | 28     |
| Generic method          | `static <E> List<E> asList(E[] a)` | 27     |
| Type token              | `String.class`                     | 29     |

## 22. Lists over arrays
Arrays are _covariant_: if `Sub` is a subtype of `Super`, `Sub[]` is a subtype of `Super[]`  
Generics are _invariant_: for any two types `Type1` and `Type2`, `List<Type1>` in neither  sub or super type of `List<Type2>`

```java
	// Fails at runtime
	Object[] objectArray = new Long[1];
	objectArray[0] ="I don't fit in" // Throws ArrayStoreException

	// Won't compile
	List<Object> ol = new ArrayList<Long>();//Incompatible types
	ol.add("I don't fit in")
```

Arrays are _reified_: Arrays know and enforce their element types at runtime.
Generics are _erasure_: Enforce their type constrains only at compile time and discard (or _erase_) their element type information at runtime.

Therefore it is illegal to create an array of a generic type, a parameterized type or a type parameter.

`new List<E>[]`, `new List<String>[]`, `new E[]`  will result in _generic array creation_ errors.

## 23. Prefer generic methods
```java
    // Generic singleton factory pattern
    private static final UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }
```
```java
// Using a recursive type bound to express mutual comparability
public class RecursiveTypeBound {
    // Returns max value in a collection - uses recursive type bound
    public static <E extends Comparable<E>> E max(Collection<E> c) {
```

## 24. Type bound with wildcards
```java
    /**
     * Similar to Collections.max
     */
    public static <E extends Comparable<? super E>> E max(
            List<? extends E> list) {
```

**PECS: producer-extends, consumer-super**

```java
    // Wildcard type for parameter that serves as an E producer
    public void pushAll(Iterable<? extends E> src) {
        for (E e : src)
            push(e);
    }
```
```java
    // Wildcard type for parameter that serves as an E consumer
    public void popAll(Collection<? super E> dst) {
        while (!isEmpty())
            dst.add(pop());
    }
```
**Never use wildcards in return values.**

**Wildcard capture**
```java
    public static void swap(List<?> list, int i, int j) {
        swapHelper(list, i, j);
    }

    // Private helper method for wildcard capture
    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
```

## 25. Typesafe heterogeneous containers
```java
    public class Favorites{
		private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();

		public <T> void putFavorites(Class<T> type, T instance){
			if(type == null)
				throw new NullPointerException("Type is null");
			favorites.put(type, type.cast(instance));//runtime safety with a dynamic cast
		}

		public <T> getFavorite(Class<T> type){
			return type.cast(favorites.get(type));
		}
	}
```

# 6. Enums and Annotations
