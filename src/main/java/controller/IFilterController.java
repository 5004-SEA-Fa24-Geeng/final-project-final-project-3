package controller;

import java.awt.event.ActionEvent;

/**
 * Interface for filter controller event handling.
 * Defines actions related to searching and resetting filter criteria.
 */
public interface IFilterController extends IController {

    /**
     * Triggered when the user clicks the search button.
     * Applies the filters based on current user input.
     *
     * @param e the ActionEvent associated with the search button
     */
    void onSearch(ActionEvent e);

    /**
     * Triggered when the user clicks the reset button.
     * Clears all filter fields and reapplies default filtering.
     *
     * @param e the ActionEvent associated with the reset button
     */
    void onReset(ActionEvent e);
}
