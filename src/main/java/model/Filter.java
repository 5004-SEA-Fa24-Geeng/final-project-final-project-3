package model;

/**
 * A functional interface representing a condition that a {@link CharacterRecord} must satisfy.
 * This interface supports logical composition of filters using the {@code and} method.
 */
@FunctionalInterface
public interface Filter {

    /**
     * Tests whether the specified character matches this filter.
     *
     * @param character the character to be tested
     * @return {@code true} if the character matches the filter condition; {@code false} otherwise
     */
    boolean matches(CharacterRecord character);

    /**
     * Combines this filter with another filter using logical AND.
     * The resulting filter matches if both this and the other filter match the character.
     *
     * @param other the other filter to combine with
     * @return a new filter representing the logical AND of this and the other filter
     */
    default Filter and(Filter other) {
        return c -> this.matches(c) && other.matches(c);
    }
}
