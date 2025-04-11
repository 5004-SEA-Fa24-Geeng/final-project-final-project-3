package controller;

import model.CharactersCollection;
import model.Filter;
import view.FilterPanel;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FilterController implements IFilterController, IController {
    private final CharactersCollection model;
    private final FilterPanel view;
    private final Runnable refreshCallback;

    public FilterController(CharactersCollection model,
                            FilterPanel view,
                            Runnable refreshCallback) {
        this.model = model;
        this.view = view;
        this.refreshCallback = refreshCallback;
        initEventHandlers();
    }

    private void initEventHandlers() {
        view.addSearchListener(this::onSearch);
        view.addResetListener(this::onReset);
    }

    @Override
    public void onSearch(ActionEvent e) {
        try {
            Filter filter = buildCompositeFilter();
            model.applyFilters(filter);
            refreshCallback.run();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void onReset(ActionEvent e) {
        view.resetFilters();
        onSearch(e);
    }

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

    private Filter buildAgeFilter() throws IllegalArgumentException {
        String minAgeStr = view.getMinAge();
        String maxAgeStr = view.getMaxAge();
        
        // if both input fields are empty, do not apply age filter
        if ((minAgeStr == null || minAgeStr.trim().isEmpty()) && 
            (maxAgeStr == null || maxAgeStr.trim().isEmpty())) {
            return null;
        }

        try {
            int minAge = parseAge(minAgeStr, 0);
            int maxAge = parseAge(maxAgeStr, Integer.MAX_VALUE);
            
            // if only the minimum age is entered, filter records greater than or equal to the minimum age
            if (maxAgeStr == null || maxAgeStr.trim().isEmpty()) {
                return character -> character.getAge() >= minAge;
            }
            
            // if only the maximum age is entered, filter records less than or equal to the maximum age
            if (minAgeStr == null || minAgeStr.trim().isEmpty()) {
                return character -> character.getAge() <= maxAge;
            }
            
            // if both values are entered, check if the range is valid
            if (minAge > maxAge) {
                throw new IllegalArgumentException("Age range is not valid");
            }
            
            return character -> character.getAge() >= minAge && character.getAge() <= maxAge;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be a valid number");
        }
    }

    private int parseAge(String input, int defaultValue) {
        if (input == null || input.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(input.trim());
    }

    @Override
    public void updateView() {
        // update the status label
        view.setStatusMessage("");
        // other UI components' status is updated during user interaction
    }
}