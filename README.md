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

