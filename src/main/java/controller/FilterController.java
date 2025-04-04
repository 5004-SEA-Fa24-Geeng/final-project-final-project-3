package controller;

import model.CharactersCollection;
import model.Filter;
import view.FilterPanel;

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
        view.addSearchListener(this::handleSearch);
        view.addResetListener(e -> {
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

        List<Integer> genders = view.getSelectedGenders();
        if (!genders.isEmpty()) {
            filter = filter.and(Filter.genderIn(genders));
        }

        filter = filter.and(buildAgeFilter());

        List<String> zodiacs = view.getSelectedZodiacs();
        if (!zodiacs.isEmpty()) {
            filter = filter.and(Filter.zodiacIn(zodiacs));
        }

        String keyword = view.getSearchKeyword();
        if (keyword != null && !keyword.isBlank()) {
            filter = filter.and(Filter.nameContains(keyword.trim()));
        }

        return filter;
    }

    private Filter buildAgeFilter() throws IllegalArgumentException {
        try {
            int minAge = parseAge(view.getMinAge(), 0);
            int maxAge = parseAge(view.getMaxAge(), Integer.MAX_VALUE);
            // TODO:年龄范围不合法报错

            return Filter.ageBetween(minAge, maxAge);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private int parseAge(String input, int defaultValue) throws NumberFormatException {
        if (input == null || input.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(input.trim());
    }
}