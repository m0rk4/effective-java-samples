# effective-java-samples
_Effective Java v3 / Programming Language Guide By Joshua Bloch / Samples with notes_

_Represents a diary in the format of git commits + README quick reference_

# CONTENTS:
- [2. Creating and destroying objects](#2-creating-and-destroying-objects)
    - [1. Use static factory methods](#1-use-static-factory-methods)

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

