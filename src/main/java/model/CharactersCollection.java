package model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages a collection of {@link CharacterRecord} entries.
 * Supports filtering, sorting, and loading data from a JSON source file.
 */
public class CharactersCollection {

    /**
     * Full list of all characters loaded from source
     */
    private List<CharacterRecord> allCharacters;

    /**
     * Currently filtered list of characters
     */
    private List<CharacterRecord> filteredCharacters;

    /**
     * The active filter applied to the character list
     */
    private Filter activeFilter = c -> true;

    /**
     * The JSON source file name
     */
    private final String SOURCEFILE = "celebrities.json";

    /**
     * Constructs a new CharactersCollection and loads character data from the JSON file.
     */
    public CharactersCollection() {
        this.allCharacters = JSONFileHandler.readJsonFile(SOURCEFILE);
        this.filteredCharacters = new ArrayList<>(allCharacters);
    }

    /**
     * Applies the given filter to the full character list and updates the filtered list.
     *
     * @param filter the filter to apply
     */
    public void applyFilters(Filter filter) {
        this.activeFilter = filter;
        filteredCharacters = allCharacters.stream()
                .filter(activeFilter::matches)
                .collect(Collectors.toList());
    }

    /**
     * Returns an unmodifiable list of the currently filtered characters.
     *
     * @return the filtered character list
     */
    public List<CharacterRecord> getFilteredCharacters() {
        return Collections.unmodifiableList(filteredCharacters);
    }

    /**
     * Returns an unmodifiable list of all characters.
     *
     * @return the full character list
     */
    public List<CharacterRecord> getAllCharacters() {
        return Collections.unmodifiableList(allCharacters);
    }

    /**
     * Returns a sorted version of the given character list using the provided comparator.
     *
     * @param characters the list to sort
     * @param comparator the comparator used for sorting
     * @return a sorted list of characters
     */
    public List<CharacterRecord> getSorted(List<CharacterRecord> characters, Comparator<CharacterRecord> comparator) {
        return characters.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Reloads the character data from the JSON file and resets the filtered list.
     */
    public void loadData() {
        allCharacters = JSONFileHandler.readJsonFile(SOURCEFILE);
        filteredCharacters = new ArrayList<>(allCharacters);
    }

    /**
     * Replaces the current filtered character list with the given list.
     *
     * @param characters the new filtered character list
     */
    public void setFilteredCharacters(List<CharacterRecord> characters) {
        filteredCharacters = new ArrayList<>(characters);
    }
}
