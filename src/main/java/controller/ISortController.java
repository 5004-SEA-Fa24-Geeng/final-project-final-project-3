package controller;

import model.CharacterRecord;
import model.Response;

import java.util.Comparator;

/**
 * Interface for SortController.
 */
public interface ISortController extends IController {
    /**
     * Sorts a list of characters based on the given comparator.
     * @param comparator the rule used to sort the characters (e.g., by name or age)
     * @return a Response object indicating success or failure of the sort operation
     */
    Response handleSortItems(Comparator<CharacterRecord> comparator);
}
