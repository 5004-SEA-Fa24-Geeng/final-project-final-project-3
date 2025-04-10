package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SortPanel extends JPanel {
    private JComboBox<String> sortOptions;
    private JButton sortButton;

    public SortPanel() {
        setLayout(new FlowLayout());

        // Dropdown with options
        sortOptions = new JComboBox<>(new String[]{"Name", "Age"});
        sortButton = new JButton("Sort");

        add(new JLabel("Sort by:"));
        add(sortOptions);
        add(sortButton);
    }

    /**
     * Adds an action listener to the sort button.
     * Use getSelectedSortOption() in the controller to check which option is selected.
     */
    public void addSortButtonListener(ActionListener listener) {
        sortButton.addActionListener(listener);
    }

    /**
     * Gets the selected sort option from the dropdown.
     *
     * @return the selected option as a string ("Name" or "Age")
     */
    public String getSelectedSortOption() {
        return (String) sortOptions.getSelectedItem();
    }
}
