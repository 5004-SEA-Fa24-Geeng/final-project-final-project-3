package controller;

import model.CharacterRecord;
import model.CharactersCollection;
import model.Response;

import static model.SortComparators.*;

import view.SortPanel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

/**
 * Controller class responsible for handling sorting actions on character data.
 * Aligns with WishListController structure.
 */
public class SortController implements ISortController {
    /**
     * The collection of characters to be sorted.
     */
    private final CharactersCollection model;
    /**
     * The UI panel where sorting options are selected.
     */
    private final SortPanel view;
    /**
     * Callback to refresh the UI after sorting is done.
     */
    private final Runnable refreshCallback;

    /**
     * Constructor.
     *
     * @param model           the collection of character data
     * @param view            the UI panel that contains sorting controls
     * @param refreshCallback a runnable that refreshes the UI when sorting is applied
     */
    public SortController(CharactersCollection model, SortPanel view, Runnable refreshCallback) {
        this.model = model;
        this.view = view;
        this.refreshCallback = refreshCallback;
        initEventHandlers();
    }

    /**
     * Register listeners for sort actions.
     */
    private void initEventHandlers() {
        view.addSortListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = view.getSelectedSortOption();
                String order = view.getSortOrder();
                boolean ascending = order.equals("Ascending");

                Comparator<CharacterRecord> comparator;

                switch (field) {
                    case "Name":
                        comparator = BY_NAME;
                        break;
                    case "Age":
                        comparator = BY_AGE;
                        break;
                    case "Popularity":
                        comparator = BY_POPULARITY;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid sort field: " + field);
                }

                if (!ascending) {
                    comparator = comparator.reversed();
                }

                Response response = handleSortItems(comparator);
                if (response.getStatus() == 200) {
                    refreshCallback.run();
                }
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
        List<CharacterRecord> filtered = model.getFilteredCharacters();
        List<CharacterRecord> listToSort;

        if (filtered != null && !filtered.isEmpty()) {
            listToSort = filtered;
        } else {
            listToSort = model.getAllCharacters();
        }

        try {
            List<CharacterRecord> sorted = model.getSorted(listToSort, comparator);
            model.setFilteredCharacters(sorted);
            return Response.success("Successfully sorted by selected option.");
        } catch (Exception e) {
            return Response.failure("Sorting failed: " + e.getMessage());
        }
    }

    @Override
    public void updateView() {
        view.updateView();
    }
}
