package controller;

import model.CharacterRecord;
import model.CharactersCollection;
import static model.SortComparators.*;
import view.SortPanel;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class responsible for handling sorting actions on character data.
 * Connects the view (SortPanel) with the model (CharactersCollection).
 */
public class SortController {
    /** The collection of characters to be sorted. */
    private final CharactersCollection characterCollection;
    /** The UI panel where sorting options are selected. */
    private final SortPanel view;
    /** Callback to refresh the UI after sorting is done. */
    private final Runnable refreshCallback;

    /**
     * Constructor.
     *
     * @param characterCollection the collection of character data
     * @param view the UI panel that contains sorting controls
     * @param refreshCallback a runnable that refreshes the UI when sorting is applied
     */
    public SortController(CharactersCollection characterCollection, SortPanel view, Runnable refreshCallback) {
        this.characterCollection = characterCollection;
        this.view = view;
        this.refreshCallback = refreshCallback;
        applyEventHandler();
    }


    /**
     * Sets up the sort button to perform sorting when clicked.
     * It checks the selected option from the dropdown and applies the right sort.
     */
    private void applyEventHandler() {
        view.addSortButtonListener(e -> {
            String selectedOption = view.getSelectedSortOption();
            List<CharacterRecord> sorted;

            if ("Name".equals(selectedOption)) {
                sorted = handleSortItems(BY_NAME);
            } else if ("Age".equals(selectedOption)) {
                sorted = handleSortItems(BY_AGE);
            } else {
                sorted = handleSortItems(BY_NAME);

            }
            refreshCallback.run();
        });
    }

    /**
     * Return a sorted list of CharacterRecord
     *
     * @param comparator the rule used to sort (e.g., by name or age)
     * @return a sorted list of CharacterRecord
     */
    public List<CharacterRecord> handleSortItems(Comparator<CharacterRecord> comparator) {
        List<CharacterRecord> filtered = characterCollection.getFilteredCharacters();
        List<CharacterRecord> listToSort;

        if (filtered != null && !filtered.isEmpty()) {
            listToSort = filtered;
        } else {
            listToSort = characterCollection.getAllCharacters();
        }

        return characterCollection.getSorted(listToSort, comparator);
    }


}
