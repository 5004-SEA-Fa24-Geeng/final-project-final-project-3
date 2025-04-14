package controller;

import model.CharactersCollection;
import model.Filter;
import view.FilterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Controller for handling filter-related user interactions in the GUI.
 * Coordinates between the view (FilterPanel) and model (CharactersCollection)
 * to apply composite filters and update the display.
 */
public class FilterController implements IFilterController {

    /**
     * The model that stores and filters the list of characters.
     */
    private final CharactersCollection model;

    /**
     * The view panel that provides UI components for filter input.
     */
    private final FilterPanel view;

    /**
     * The callback function used to refresh the character list in the UI.
     */
    private final Runnable refreshCallback;

    /**
     * Constructs a FilterController with the specified model, view, and refresh action.
     *
     * @param model           the character collection to be filtered
     * @param view            the panel providing UI components for filtering
     * @param refreshCallback the callback to refresh the character list view
     */
    public FilterController(CharactersCollection model,
                            FilterPanel view,
                            Runnable refreshCallback) {
        this.model = model;
        this.view = view;
        this.refreshCallback = refreshCallback;
        initEventHandlers();
    }

    /**
     * Initializes event handlers for search and reset actions.
     */
    private void initEventHandlers() {
        view.addSearchListener(this::onSearch);
        view.addResetListener(this::onReset);
    }

    /**
     * Handles the search button click event.
     * Applies composite filters based on user input.
     *
     * @param e the ActionEvent triggered by the search button
     */
    @Override
    public void onSearch(ActionEvent e) {
        try {
            Filter filter = buildCompositeFilter();
            model.applyFilters(filter);
            view.setStatusMessage("Results updated", new Color(0, 128, 0));
            refreshCallback.run();
        } catch (IllegalArgumentException ex) {
            view.setStatusMessage(ex.getMessage(), Color.RED);
        }
    }

    /**
     * Handles the reset button click event.
     * Resets all filters and reapplies default filtering.
     *
     * @param e the ActionEvent triggered by the reset button
     */
    @Override
    public void onReset(ActionEvent e) {
        view.resetFilters();
        onSearch(e);
        view.setStatusMessage("Reset successfully", new Color(0, 128, 0));
    }

    /**
     * Builds a composite filter based on the current values in the view.
     * Combines filters for gender, age, zodiac sign, and keyword.
     *
     * @return the composite Filter to be applied
     * @throws IllegalArgumentException if input validation fails
     */
    private Filter buildCompositeFilter() throws IllegalArgumentException {
        Filter filter = character -> true;

        List<Integer> genders = view.getSelectedGenders();
        if (!genders.isEmpty()) {
            filter = filter.and(c -> genders.contains(c.getGender()));
        }

        Filter ageFilter = buildAgeFilter();
        if (ageFilter != null) {
            filter = filter.and(ageFilter);
        }

        List<String> zodiacs = view.getSelectedZodiacs();
        if (!zodiacs.isEmpty()) {
            filter = filter.and(c -> zodiacs.contains(c.getZodiacSign()));
        }

        String keyword = view.getSearchKeyword();
        if (keyword != null && !keyword.isBlank()) {
            filter = filter.and(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()));
        }

        return filter;
    }

    /**
     * Builds a filter for age range based on the min and max age input fields.
     *
     * @return the age-based Filter or null if both fields are empty
     * @throws IllegalArgumentException if input is invalid or range is incorrect
     */
    private Filter buildAgeFilter() throws IllegalArgumentException {
        String minAgeStr = view.getMinAge();
        String maxAgeStr = view.getMaxAge();

        if ((minAgeStr == null || minAgeStr.trim().isEmpty()) &&
                (maxAgeStr == null || maxAgeStr.trim().isEmpty())) {
            return null;
        }

        try {
            int minAge = parseAge(minAgeStr, 0);
            int maxAge = parseAge(maxAgeStr, Integer.MAX_VALUE);

            if (maxAgeStr == null || maxAgeStr.trim().isEmpty()) {
                return character -> character.getAge() >= minAge;
            }

            if (minAgeStr == null || minAgeStr.trim().isEmpty()) {
                return character -> character.getAge() <= maxAge;
            }

            if (minAge > maxAge) {
                throw new IllegalArgumentException("Age range is not valid");
            }

            return character -> character.getAge() >= minAge && character.getAge() <= maxAge;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be a valid number");
        }
    }

    /**
     * Parses a string into an integer with a default fallback.
     *
     * @param input        the string input
     * @param defaultValue the default value if input is empty
     * @return the parsed integer or default
     */
    private int parseAge(String input, int defaultValue) {
        if (input == null || input.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(input.trim());
    }

    /**
     * Updates the view status label and clears any previous messages.
     * Called when the controller is re-initialized.
     */
    @Override
    public void updateView() {
        view.setStatusMessage("", Color.BLACK);
    }
}
