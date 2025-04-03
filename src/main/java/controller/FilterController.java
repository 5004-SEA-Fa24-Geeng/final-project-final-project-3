package controller;

import model.CharactersCollection;
import model.Filter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FilterController {
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
        view.getSearchButton().addActionListener(this::handleSearch);
        view.getResetButton().addActionListener(e -> {
            view.resetFilters();
            handleSearch(e);
        });
    }

    private void handleSearch(ActionEvent e) {
        try {
            Filter filter = buildCompositeFilter();
            model.applyFilters(filter);
            refreshCallback.run();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Filter buildCompositeFilter() throws IllegalArgumentException {
        Filter filter = character -> true;

        List<String> genders = view.getSelectedGenders();
        if (!genders.isEmpty()) {
            filter = filter.and(character -> genders.contains(character.getGender()));
        }

        filter = filter.and(buildAgeFilter());

        List<String> zodiacs = view.getSelectedZodiacs();
        if (!zodiacs.isEmpty()) {
            filter = filter.and(character -> zodiacs.contains(character.getZodiacSign()));
        }

        return filter;
    }

    private Filter buildAgeFilter() throws IllegalArgumentException {
        try {
            int minAge = parseAge(view.getMinAge(), 0);
            int maxAge = parseAge(view.getMaxAge(), Integer.MAX_VALUE);

            if (minAge > maxAge) {
                throw new InvalidFilterException("age",
                        minAge + "-" + maxAge,
                        "minAge cannot be greater than maxAge");
            }

            return character -> character.getAge() >= minAge
                    && character.getAge() <= maxAge;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private int parseAge(String input, int defaultValue) throws NumberFormatException {
        if (input == null || input.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(input.trim());
    }
}