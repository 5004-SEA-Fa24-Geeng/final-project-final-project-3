package model;

import java.util.Comparator;

/**
 * This is a utility class which provides common comparators for sorting Character objects.
 */
public class SortComparators {

    /**
     * Comparator for sorting characters alphabetically by name, ignoring case.
     */
    public static final Comparator<CharacterRecord> BY_NAME =
            Comparator.comparing(CharacterRecord::getName, String.CASE_INSENSITIVE_ORDER);

    /**
     * Comparator for sorting characters by age (based on birthday), from youngest to oldest.
     */
    public static final Comparator<CharacterRecord> BY_AGE =
            Comparator.comparingInt(CharacterRecord::getAge);

    /**
     * Comparator for sorting characters by popularity, from highest to lowest.
     */
    public static final Comparator<CharacterRecord> BY_POPULARITY =
            Comparator.comparingDouble(CharacterRecord::getPopularity).reversed();

}
