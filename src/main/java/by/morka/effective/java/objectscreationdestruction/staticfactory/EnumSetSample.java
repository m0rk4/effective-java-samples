package by.morka.effective.java.objectscreationdestruction.staticfactory;

public class EnumSetSample {
    /**
     * 1. Sub-class (Jumbo, regular) can be returned based on specific logic.
     */
//    /**
//     * Creates an empty enum set with the specified element type.
//     *
//     * @param <E> The class of the elements in the set
//     * @param elementType the class object of the element type for this enum
//     *     set
//     * @return An empty enum set of the specified type.
//     * @throws NullPointerException if {@code elementType} is null
//     */
//     public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
//        Enum<?>[] universe = getUniverse(elementType);
//        if (universe == null)
//            throw new ClassCastException(elementType + " not an enum");
//
//        if (universe.length <= 64)
//            return new RegularEnumSet<>(elementType, universe);
//        else
//            return new JumboEnumSet<>(elementType, universe);
//    }
}
