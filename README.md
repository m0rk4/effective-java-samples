# effective-java-samples
_Effective Java v3 / Programming Language Guide By Joshua Bloch / Samples with notes_

_Represents a diary in the format of git commits + README quick reference_

_Some items / chapters were omitted since they were familiar to me_

# Items:
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
    - [26. Use Enums instead of int constants](#26-use-enums-instead-of-int-constants)
    - [27. Use instance fields instead of ordinals](#27-use-instance-fields-instead-of-ordinals)
    - [28. Use EnumSet instead of bit fields](#28-use-enumset-instead-of-bit-fields)
    - [29. Use EnumMap instead of ordinal indexing](#29-use-enummap-instead-of-ordinal-indexing)
    - [30. Emulate enum extension by using interfaces](#30-emulate-enum-extension-by-using-interfaces)
    - [31. Use annotations instead over naming conventions (e.g. void testMethod -> @Test void testMethod)](#31-use-annotations-instead-over-naming-conventions-eg-void-testmethod---test-void-testmthod)
    - [32. Interface marker or annotation marker?](#32-interface-marker-or-annotation-marker)
- [7. Lambdas and streams](#7-lambdas-and-streams)
    - [33. Lambdas over anonymous classes](#33-lambdas-over-anonymous-classes)
    - [34. Prefer method references over lambdas (not always)](#34-prefer-method-references-over-lambdas-not-always)
- [8. Methods](#8-methods)
    - [35. Defensive copies](#35-defensive-copies)
    - [36. Overload methods wisely](#36-overload-methods-wisely)
    - [37. Varargs](#37-varargs)
- [9. Exceptions](#9-exceptions)
    - [38. Use exceptions only for exceptional conditions](#38-use-exceptions-only-for-exceptional-conditions)
    - [39. Use checked exceptions for recoverable conditions and runtime exceptions for programming errors](#39-use-checked-exceptions-for-recoverable-conditions-and-runtime-exceptions-for-programming-errors)
    - [40. Avoid unnecessary use of checked exceptions](#40-avoid-unnecessary-use-of-checked-exceptions)
    - [41. Favor the use of standard exceptions](#41-favor-the-use-of-standard-exceptions)
    - [42. Throw exceptions appropriate to the abstraction](#42-throw-exceptions-appropriate-to-the-abstraction)
    - [43. Document all exceptions thrown by each method](#43-document-all-exceptions-thrown-by-each-method)
    - [44. Include failure-capture information in detail messages](#44-include-failure-capture-information-in-detail-messages)
    - [45. Strive for failure atomicity](#45-strive-for-failure-atomicity)
    - [46. Don't ignore exceptions](#46-dont-ignore-exceptions)
- [10. Concurrency](#10-concurrency)
    - [47. Synchronize access to shared mutable data](#47-synchronize-access-to-shared-mutable-data)
    - [48. Avoid excessive synchronization](#48-avoid-excessive-synchronization)
    - [49. Prefer executors and tasks to threads](#49-prefer-executors-and-tasks-to-threads)
    - [50. Prefer concurrency utilities to wait() and notify()](#50-prefer-concurrency-utilities-to-wait-and-notify)
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
## 26. Use Enums instead of int constants
Try Avoiding switch (i.e. avoid 'all-actions-method')
```java
    /**
     * Switch is applicable when:
     * 1. Enum is NOT UNDER OUR CONTROL
     * 2. ENUM is UNDER OUR CONTROL but the method ISN'T USEFUL ENOUGH TO BE IN ENUM
     */
    public static Operation inverse(Operation op) {
        return switch (op) {
            case PLUS -> Operation.MINUS;
            case MINUS -> Operation.PLUS;
            case TIMES -> Operation.DIVIDE;
            case DIVIDE -> Operation.TIMES;
        };
    }
```

*Benefits*
```java
/**
 * 1. Final class
 * 2. Singleton
 * 3. Exports static final instance member
 */
public enum Planet {
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);

    private final double mass;
    private final double radius;
    private final double surfaceGravity;

    private static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        this.surfaceGravity = G * mass / (radius * radius);
    }

    public double mass() {
        return mass;
    }

    public double radius() {
        return radius;
    }

    public double surfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }
}
```

```java
// Enum type with constant-specific class bodies and data
public enum Operation {
    PLUS("+") {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("-") {
        public double apply(double x, double y) { return x - y; }
    },
    TIMES("*") {
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE("/") {
        public double apply(double x, double y) { return x / y; }
    };

    private final String symbol;

    Operation(String symbol) {
        // Can't access static member from constructor
        // stringToEnum;
        this.symbol = symbol;
    }

    @Override public String toString() { return symbol; }

    public abstract double apply(double x, double y);

    // Implementing a fromString method on an enum type
    private static final Map<String, Operation> stringToEnum =
            Stream.of(values()).collect(
                    toMap(Object::toString, e -> e));

    // Returns Operation for string, if any
    public static Optional<Operation> fromString(String symbol) {
        return Optional.ofNullable(stringToEnum.get(symbol));
    }
}
```

**Strategy-enum pattern**
```java
public enum PayrollDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);

    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    PayrollDay() {
        this(PayType.WEEKDAY);
    }

    public int pay(int minutesWorked, int payRate) {
        return this.payType.pay(minutesWorked, payRate);
    }

    /**
     * Strategy enum
     * Useful when some constants share same behaviour
     */
    private enum PayType {
        WEEKDAY {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;
            }
        };

        private static final int MINS_PER_SHIFT = 8 * 60;

        abstract int overtimePay(int minutesWorked, int payRate);

        public int pay(int minutesWorked, int payRate) {
            int basePay = minutesWorked * payRate;
            return basePay + overtimePay(minutesWorked, payRate);
        }
    }
}
```

## 27. Use instance fields instead of ordinals
```java
// Enum with integer data stored in an instance field
public enum Ensemble {
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
    SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
    NONET(9), DECTET(10), TRIPLE_QUARTET(12);

    private final int numberOfMusicians;

    Ensemble(int size) {
        this.numberOfMusicians = size;
    }

    public int numberOfMusicians() {
        return numberOfMusicians;
    }

    /**
     * BAD! never do so
     */
//    public int numberOfMusicians() { return ordinal() + 1; }
}
```

## 28. Use EnumSet instead of bit fields
```java
// EnumSet - a modern replacement for bit fields
public class Text {
    public enum Style {BOLD, ITALIC, UNDERLINE, STRIKETHROUGH}

    // Any Set could be passed in, but EnumSet is clearly best
    public void applyStyles(Set<Style> styles) {
        System.out.printf("Applying styles %s to text%n",
                Objects.requireNonNull(styles));
    }

    // Sample use
    public static void main(String[] args) {
        Text text = new Text();
        /*
        EnumSet uses same bit fields under the hood, but its API is safe and well formed!
         */
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
    }
}
```

## 29. Use EnumMap instead of ordinal indexing
```java
    public static void main(String[] args) {
        Plant[] garden = {
                new Plant("Basil", LifeCycle.ANNUAL),
                new Plant("Carroway", LifeCycle.BIENNIAL),
                new Plant("Dill", LifeCycle.ANNUAL),
                new Plant("Lavendar", LifeCycle.PERENNIAL),
                new Plant("Parsley", LifeCycle.BIENNIAL),
                new Plant("Rosemary", LifeCycle.PERENNIAL)
        };

        // Using ordinal() to index into an array - DON'T DO THIS!
        Set<Plant>[] plantsByLifeCycleArr =
                (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
        for (int i = 0; i < plantsByLifeCycleArr.length; i++)
            plantsByLifeCycleArr[i] = new HashSet<>();
        for (Plant p : garden)
            plantsByLifeCycleArr[p.lifeCycle.ordinal()].add(p);
        // Print the results
        for (int i = 0; i < plantsByLifeCycleArr.length; i++) {
            System.out.printf("%s: %s%n",
                    Plant.LifeCycle.values()[i], plantsByLifeCycleArr[i]);
        }

        // Using an EnumMap to associate data with an enum (Page 172)
        Map<LifeCycle, Set<Plant>> plantsByLifeCycle =
                new EnumMap<>(Plant.LifeCycle.class);
        for (Plant.LifeCycle lc : Plant.LifeCycle.values())
            plantsByLifeCycle.put(lc, new HashSet<>());
        for (Plant p : garden)
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        System.out.println(plantsByLifeCycle);

        // Naive stream-based approach - unlikely to produce an EnumMap!
        System.out.println(Arrays.stream(garden)
                .collect(groupingBy(p -> p.lifeCycle)));

        // Using a stream and an EnumMap to associate data with an enum
        System.out.println(Arrays.stream(garden)
                .collect(groupingBy(p -> p.lifeCycle,
                        () -> new EnumMap<>(LifeCycle.class), toSet())));
    }
```

## 30. Emulate enum extension by using interfaces
```java
	public interface Operation{
		double apply(double x, double y);
	}
	public enum BasicOperation implements Operation{
		PLUS("+"){
			public double apply(double x, double y) {return x + y}
		},
		MINUS("-"){...},TIMES("*"){...},DIVIDE("/"){...};

		private final String symbol;
		BasicOperation(String symbol){
			this.symbol = symbol;
		}
		@Override
		public String toString(){ return symbol; }
	}
```

## 31. Use annotations instead over naming conventions (e.g. void testMethod -> @Test void testMthod)
`@Retention` and `@Target` are _meta-annotations_  

__Retention RetentionPolicies__

| Enum    | Description                              |
|:--------|:-----------------------------------------|
| CLASS   | Retain only at compile time, not runtime |
| RUNTIME | Retain at compile and also runtime       |
| SOURCE  | Discard by the compiler                  |

__Target ElementTypes__

| Enum            | Valid on...                                        |
|:----------------|:---------------------------------------------------|
| ANNOTATION_TYPE | Annotation type declaration                        |
| CONSTRUCTOR     | constructors                                       |
| FIELD           | the field (includes also enum constants)           |
| LOCAL_VARIABLE  | local variables                                    |
| METHOD          | methods                                            |
| PACKAGE         | packages                                           |
| PARAMETER       | parameter declaration                              |
| TYPE            | class, interface, annotation and enums declaration |
| TYPE_PARAMETER  | type parameter declarations                        |
| TYPE_USE        | the use of a specific type                         |

** Process annotations using refection API **

## 32. Interface marker or annotation marker?
Marker interface in Java is interfaces with no field or methods or in simple word empty interface in java is called marker interface.

* Marker interfaces define a type that is implemented by instances of the marked class; marker annotations do not. (Catch errors in compile time).
* They can be targeted more precisely than marker annotations.
* It's possible to add more information to an annotation type after it is already in use.

# 7. Lambdas and streams
## 33. Lambdas over anonymous classes

```java
// Enum with function object fields & constant-specific behavior
public enum Operation {
    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}
```
```java
// Sorting with function objects
public class SortFourWays {
    public static void main(String[] args) {
        List<String> words = Arrays.asList(args);

        // Anonymous class instance as a function object - obsolete!
        Collections.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
        System.out.println(words);
        Collections.shuffle(words);

        // Lambda expression as function object (replaces anonymous class)
        Collections.sort(words,
                (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        System.out.println(words);
        Collections.shuffle(words);

        // Comparator construction method (with method reference) in place of lambda
        Collections.sort(words, comparingInt(String::length));
        System.out.println(words);
        Collections.shuffle(words);

        // Default method List.sort in conjunction with comparator construction method
        words.sort(comparingInt(String::length));
        System.out.println(words);
    }
}
```

## 34. Prefer method references over lambdas (not always)
**RULE IS SIMPLE: Use method references where it is more readable and shorter.**
```java
public class Freq {
    public static void main(String[] args) {
        Map<String, Integer> frequencyTable = new TreeMap<>();

        for (String s : args)
            frequencyTable.merge(s, 1, (count, incr) -> count + incr); // Lambda
        System.out.println(frequencyTable);

        frequencyTable.clear();
        for (String s : args)
            frequencyTable.merge(s, 1, Integer::sum); // Method reference
        System.out.println(frequencyTable);
    }
}
```

# 8. Methods
## 35. Defensive copies
**Broken immutable class**
```java
public final class Period {
    private final Date start;
    private final Date end;

    /**
     * @param  start the beginning of the period
     * @param  end the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(
                    start + " after " + end);
        this.start = start;
        this.end   = end;
    }

    public Date start() {
        return start;
    }
    public Date end() {
        return end;
    }

    public String toString() {
        return start + " - " + end;
    }

//    // Repaired constructor - makes defensive copies of parameters
//    public Period(Date start, Date end) {
//        this.start = new Date(start.getTime());
//        this.end   = new Date(end.getTime());
//
    // PROTECTION AGAINST time-of-check time-of-use attack
//        if (this.start.compareTo(this.end) > 0)
//            throw new IllegalArgumentException(
//                    this.start + " after " + this.end);
//    }
//
//    // Repaired accessors - make defensive copies of internal fields
//    public Date start() {
//        return new Date(start.getTime());
//    }
//
//    public Date end() {
//        return new Date(end.getTime());
//    }
}
```

*Summary*
* If class has mutable components, which it receives from client or returns them to client
    defensive copy is required
* If coping costs are too high AND class trusts clients
    defensive coping can be replaced with DOCUMENTATION reflecting their responsibility 

## 36. Overload methods wisely
```java
public class CollectionClassifier {
    public static String classify(Set<?> s) {
        return "Set";
    }

    public static String classify(List<?> lst) {
        return "List";
    }

    public static String classify(Collection<?> c) {
        return "Unknown Collection";
    }

    public static String classifyFixed(Collection<?> c) {
        return c instanceof Set  ? "Set" :
                c instanceof List ? "List" : "Unknown Collection";
    }

    public static void main(String[] args) {
        Collection<?>[] collections = {
                new HashSet<String>(),
                new ArrayList<BigInteger>(),
                new HashMap<String, String>().values()
        };

        // Will print collection because overloading choice is being made on COMPILATION STAGE!
        // On compilation it is Collection<?> as seen
        for (Collection<?> c : collections)
            System.out.println(classify(c));
    }
}
```

**Overriding**
```java
public class Wine {
    String name() {
        return "wine";
    }
}
class Champagne extends SparklingWine {
    @Override String name() { return "champagne"; }
}
class SparklingWine extends Wine {
    @Override
    String name() {
        return "sparkling wine";
    }
}
// Classification using method overriding
public class Overriding {
    public static void main(String[] args) {
        List<Wine> wineList = List.of(
                new Wine(), new SparklingWine(), new Champagne());

        // Will print 'wine' 'champagne' etc.
        // Because implementation of overridden methods is selected dynamically!
        for (Wine wine : wineList)
            System.out.println(wine.name());
    }
}
```

* Prefer not to export two overloads with same amount of params!!!

## 37. Varargs
*Example that describes tips*
```java
public class Varargs {
    // Simple use of varargs
    static int sum(int... args) {
        int sum = 0;
        for (int arg : args)
            sum += arg;
        return sum;
    }

//    // The WRONG way to use varargs to pass one or more arguments!
//    static int min(int... args) {
//        if (args.length == 0)
//            throw new IllegalArgumentException("Too few arguments");
//        int min = args[0];
//        for (int i = 1; i < args.length; i++)
//            if (args[i] < min)
//                min = args[i];
//        return min;
//    }

    /*
    NO NEED TO CHECK FOR SIZE == 0!
    METHOD SIGNATURE GUARANTEES that at least one param exists!
     */
    // The right way to use varargs to pass one or more arguments
    static int min(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg : remainingArgs)
            if (arg < min)
                min = arg;
        return min;
    }

    public static void main(String[] args) {
        System.out.println(sum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println(min(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        /*
        PAY ATTENTION THAT IT IS NOT VARARGS!
        It is simple overloading with N params (x) (x, x) etc.
         */
        List.of(1,23,4,5);
    }
}
```

# 9. EXCEPTIONS
## 38. Use exceptions only for exceptional conditions
Exceptions are for exceptional conditions.  
Never use or (expose in the API) exceptions for ordinary control flow.

## 39. Use checked exceptions for recoverable conditions and runtime exceptions for programming errors
Throwables:

* checked exceptions: for conditions from which the caller can reasonably be expected to recover
* unchecked exceptions: shouldn't, be caught. recovery is impossible and continued execution would do more harm than good.
	* runtime exceptions: to indicate programming errors. The great majority indicate precondition violations.
	* errors : are reserved for use by the JVM. (as a convention)

Unchecked throwables that you implement should **always** subclass _RuntimeException_.

## 40. Avoid unnecessary use of checked exceptions
Use checked exceptions only if these 2 conditions happen:

* The  exceptional condition cannot be prevented by proper use of the API
* The programmer using the API can take some useful action once confronted with the exception.

Refactor the checked exception into an unchecked exception to make the API more pleasant.

Invocation with checked exception
```java

	try {
		obj.action(args);
	} catch(TheCheckedException e) {
		// Handle exceptional condition
		...
	}
```

Prefer this instead:
```java
	if (obj.actionPermitted(args)) {
		obj.action(args);
	} else {
		// Handle exceptional condition
		...
	}
```
## 41. Favor the use of standard exceptions
| Exception                       |  Occasion for Use                                                              |
|---------------------------------|--------------------------------------------------------------------------------|
| IllegalArgumentException        |  Non-null parameter value is inappropriate                                     |
| IllegalStateException           |  Object state is inappropriate for method invocation                           |
| NullPointerException            |  Parameter value is null where prohibited                                      |
| IndexOutOfBoundsException       |  Index parameter value is out of range                                         |
| ConcurrentModificationException |  Concurrent modification of an object has been detected where it is prohibited |
| UnsupportedOperationException   |  Object does not support method                                                |

### Java 8 Exceptions           
| 					             |  					             | 					               |
|--------------------------------|-----------------------------------|---------------------------------|
| AclNotFoundException           | InvalidMidiDataException          | RefreshFailedException          |
| ActivationException            | InvalidPreferencesFormatException | RemarshalException              |
| AlreadyBoundException          | InvalidTargetObjectTypeException  | RuntimeException                |
| ApplicationException           | IOException                       | SAXException                    |
| AWTException                   | JAXBException                     | ScriptException                 |
| BackingStoreException          | JMException                       | ServerNotActiveException        |
| BadAttributeValueExpException  | KeySelectorException              | SOAPException                   |
| BadBinaryOpValueExpException   | LambdaConversionException         | SQLException                    |
| BadLocationException           | LastOwnerException                | TimeoutException                |
| BadStringOperationException    | LineUnavailableException          | TooManyListenersException       |
| BrokenBarrierException         | MarshalException                  | TransformerException            |
| CertificateException           | MidiUnavailableException          | TransformException              |
| CloneNotSupportedException     | MimeTypeParseException            | UnmodifiableClassException      |
| DataFormatException            | MimeTypeParseException            | UnsupportedAudioFileException   |
| DatatypeConfigurationException | NamingException                   | UnsupportedCallbackException    |
| DestroyFailedException         | NoninvertibleTransformException   | UnsupportedFlavorException      |
| ExecutionException             | NotBoundException                 | UnsupportedLookAndFeelException |
| ExpandVetoException            | NotOwnerException                 | URIReferenceException           |
| FontFormatException            | ParseException                    | URISyntaxException              |
| GeneralSecurityException       | ParserConfigurationException      | UserException                   |
| GSSException                   | PrinterException                  | XAException                     |
| IllegalClassFormatException    | PrintException                    | XMLParseException               |
| InterruptedException           | PrivilegedActionException         | XMLSignatureException           |
| IntrospectionException         | PropertyVetoException             | XMLStreamException              |
| InvalidApplicationException    | ReflectiveOperationException      | XPathException                  |

## 42. Throw exceptions appropriate to the abstraction
Higher layers should catch lower-level exceptions and, in their place, throw exceptions that can be explained in terms of the higher-level abstraction.
```java
	// Exception Translation
	try {
		// Use lower-level abstraction to do our bidding
		...
	} catch(LowerLevelException e) {
		throw new HigherLevelException(...);
	}
```
Do not overuse. The best way to deal with exceptions from lower layers is to avoid them, by ensuring that lower-level methods succeed.

**Exception chaining**
When the lower-level exception is utile for the debugger, pass the lower-level to the higher-level exception, with an accessor method (Throwable.getCause) to retrieve the lower-level exception.

```java
	class HigherLevelException extends Exception {
		HigherLevelException(Throwable cause) {
		super(cause);
		}
	}
```
## 43. Document all exceptions thrown by each method
Unchecked exceptions generally represent programming errors, and familiarizing programmers with all of the errors they can make helps them avoid making these errors.

Always declare checked exceptions individually, and document precisely the conditions under which each one is thrown using the Javadoc `@throws` tag.

Do not use the throws keyword to include unchecked exceptions in the method declaration.

## 44. Include failure-capture information in detail messages
It is critically important that the exception’s `toString` method return as much information as possible concerning
the cause of the failure.
To capture the failure, the detail message of an exception should contain the values of all parameters and fields that contributed to the exception.
One way to ensure that is to require this information in their constructors instead of a string detail message. Also provide accessors to this parameters could help useful to recover from the failure

```java
	public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {...}
```

## 45. Strive for failure atomicity
A failed method invocation should leave the object in the state that it was in prior to the invocation.
Options to achieve this:

* Design immutable objects
* Order the computation so that any part that may fail takes place before any part that modifies the object.
* Write recovery code (Undo operation)
* Perform the operation on a temporary copy of the object, and replace it once is completed.

## 46. Don't ignore exceptions
```java
	try {
	...
	} catch (SomeException e) {
	}
```

# 10. Concurrency
## 47. Synchronize access to shared mutable data
In Java reading or writing a variable is atomic unless type `long` or `double`, but for all atomic operations it does not guarantee that a value written by one thread will be visible to another.

Synchronization is required for reliable communication between threads as well as for mutual exclusion.

**Effectively immutable**: data object  modified by one thread to modify shared it with other threads, synchronizing only the act of sharing the object reference. Other threads can then read the object without further synchronization, so long as it isn't modified again.

**Safe publication**: Transferring such an object reference from one thread to others.


_In general:_ When multiple threads share mutable data, each thread that reads or writes the data must perform synchronization

_Best thing to do:_ **Not share mutable data.**

## 48. Avoid excessive synchronization
Inside a synchronized region, do not invoke a  method (_alien_) that is designed to be overridden, or one provided by a client in the form of a function object ([Item 21](#21-use-function-objects-to-represent-strategies)). Calling it from a synchronized region can cause exceptions,
deadlocks, or data corruption.
Move alien method invocations out of synchronized blocks. Taking a “snapshot” of the object that can then be safely traversed without a lock.

```java

	// Alien method moved outside of synchronized block - open calls
	private void notifyElementAdded(E element) {
		List<SetObserver<E>> snapshot = null;
		synchronized(observers) {
			snapshot = new ArrayList<SetObserver<E>>(observers);
		}
		for (SetObserver<E> observer : snapshot)
			observer.added(this, element);
	}
```
Or use a _concurrent collection_ known as CopyOnWriteArrayList. It is a variant of ArrayList in which all write operations are implemented by making a fresh copy of the entire underlying array.
The internal array is never modified and iteration requires no locking.

**open call**: An alien method invoked outside of a synchronized region

_As Rule_:

* **do as little work as possible inside synchronized regions**
* **limit the amount of work that you do from within synchronized regions**

## 49. Prefer executors and tasks to threads
ExecutorService possibilities:

* wait for a particular task to complete: `background thread SetObserver`
* wait for any or all of a collection of tasks to complete: `invokeAny` or `invokeAll`
* wait for the executor service's graceful termination to complete: `awaitTermination`
* retrieve the results of tasks one by one as they complete: `ExecutorCompletionService`
*...

For more than one thread use a _thread pool_.
For lightly loaded application, use: `Executors.new-CachedThreadPool`
For heavily loaded application, use: `Executors.newFixedThreadPool`

**executor service**: mechanism for executing tasks

**task**: unit of work. Two types.

* Runnable
* Callable, similar to Runnable but returns a value

Make use `ForkJoinPool`!

## 50. Prefer concurrency utilities to wait() and notify()
Given the difficulty of using wait and notify correctly, you should use the higher-level concurrency utilities instead.

* Executor Framework
* Concurrent Collections
* Synchronizers

**Concurrent Collections**: High-performance concurrent implementations of standard collection interfaces (List, Queue, and Map)  
Use concurrent collections in preference to externally synchronized collections   
Some interfaces have been extended with blocking operations, which wait (or block) until they can be successfully performed. This allows blocking queues to be used for work queues ( _producer-consumer queues_). One or more producer threads enqueue work items and from which one or more consumer threads dequeue and process
items as they become available. ExecutorService implementations, including ThreadPoolExecutor, use a BlockingQueue.

**Synchronizers**: objects that enable threads to wait for one another, allowing them to coordinate their activities (CountDownLatch, Semaphore, CyclicBarrier, Exchanger, Phaser)

**wait**: Always use the wait loop idiom to invoke the wait method; never invoke it outside of a loop. The loop serves to test the condition before and after waiting.
```java
	// The standard idiom for using the wait method
	synchronized (obj) {
		while (<condition does not hold>){
			obj.wait(); // (Releases lock, and reacquires on wakeup)
		}
		... // Perform action appropriate to condition
	}
```
**notify**: Wakes a single waiting thread, assuming such a thread exists.

**notifyAll**: Wakes all waiting threads.

Use always use _notifyAll_ (and not forget to use the wait loop explained before)
You may wake some other threads, but these threads will check the condition for which they're waiting and, finding it false, will continue waiting.
