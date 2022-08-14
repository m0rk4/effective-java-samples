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

# 4. Enforce noninstantiability with a private constructor
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

# 5. DI
Use constructor injection instead of tight coupling (resource instantiation directly in class).

```java
// Passing resource factory to constructor, builder, static factory method
Mosaic create(Supplier<? extends Tile> tileFactory) {
```

# 6. Avoid extra objects creation
```java
// Compile first then do the job (See implementation to identify possible extra creations -> try to avoid)
private static final Pattern ROMAN =
            Pattern.compile("Л(?=.)M*(C[MD]|D?C{0,3})"
                    + "(X[CL]IL?X{0,3})(I[XV]|V?I{0,3})$");
```

**Prefer primitives over wrapper ones where possible.**

# 7. Eliminate obsolete object references
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

# 8. Try-with-resources
Rules:
* Avoid try-finally nesting hell;
* If your resources should be closed, use `Autocloseable` interface.

# 9. Avoid cleaners and finalizers
* Finalizers are unpredictable, often dangerous and generally not useful;
* Same for cleaners;
* **Never do anything time-critical in a finalizer**;
* **Never depend on a finalizer to update critical persistent state**;
* Uncaught exceptions inside a finalizer won't even print a warning.

## 3. Methods common to all objects
  

  

