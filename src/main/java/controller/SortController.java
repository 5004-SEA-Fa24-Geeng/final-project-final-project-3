package controller;

import model.CharacterRecord;
import model.CharactersCollection;
import model.Response;
import static model.SortComparators.*;
import view.SortPanel;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class responsible for handling sorting actions on character data.
 * Aligns with WishListController structure.
 */
public class SortController {
    /** The collection of characters to be sorted. */
    private final CharactersCollection characterCollection;
    /** The UI panel where sorting options are selected. */
    private final SortPanel sortPanel;
    /** Callback to refresh the UI after sorting is done. */
    private final Runnable refreshCallback;

    /**
     * Constructor.
     *
     * @param characterCollection the collection of character data
     * @param sortPanel the UI panel that contains sorting controls
     * @param refreshCallback a runnable that refreshes the UI when sorting is applied
     */
    public SortController(CharactersCollection characterCollection, SortPanel sortPanel, Runnable refreshCallback) {
        this.characterCollection = characterCollection;
        this.sortPanel = sortPanel;
        this.refreshCallback = refreshCallback;
        initListeners();
    }

    /**
     * Register listeners for sort actions.
     */
    private void initListeners() {
        sortPanel.addSortButtonListener(e -> {
            String selectedOption = sortPanel.getSelectedSortOption();
            Response response;

            switch (selectedOption) {
                case "Name":
                    response = handleSortItems(BY_NAME);
                    break;
                case "Age":
                    response = handleSortItems(BY_AGE);
                    break;
                default:
                    response = handleSortItems(BY_NAME);
                    break;
            }

            if (response.getStatus() == 200) {
                refreshCallback.run();
                JOptionPane.showMessageDialog(null, response.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Perform the sorting based on the given comparator.
     *
     * @param comparator the sorting rule
     * @return a Response indicating success or failure
     */
    public Response handleSortItems(Comparator<CharacterRecord> comparator) {
        List<CharacterRecord> filtered = characterCollection.getFilteredCharacters();
        List<CharacterRecord> listToSort;

        if (filtered != null && !filtered.isEmpty()) {
            listToSort = filtered;
        } else {
            listToSort = characterCollection.getAllCharacters();
        }

        try {
            List<CharacterRecord> sorted = characterCollection.getSorted(listToSort, comparator);
            characterCollection.setFilteredCharacters(sorted);
            return Response.success("Successfully sorted by selected option.");
        } catch (Exception e) {
            return Response.failure("Sorting failed: " + e.getMessage());
        }
    }
}
