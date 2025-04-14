package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel that allows users to sort a list of characters by various criteria.
 * Provides options to select sorting field and order, and shows count of found characters.
 */
public class SortPanel extends JPanel {
    /** Dropdown for selecting sort field (Name, Age, Popularity). */
    private final JComboBox<String> sortFieldComboBox = new JComboBox<>(new String[]{"Name", "Age", "Popularity"});
    /** Dropdown for selecting sort order (Ascending or Descending). */
    private final JComboBox<String> sortOrderComboBox = new JComboBox<>(new String[]{"Ascending", "Descending"});
    /** Button to trigger sorting. */
    private final JButton sortButton = new JButton("Sort");
    /** Label to display number of characters found. */
    private final JLabel countLabel = new JLabel("Found: 0");


    /**
     * Constructs the SortPanel UI with layout and components.
     */
    public SortPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setPreferredSize(new Dimension(1000, 40));
        setBackground(new Color(255, 242, 247));
        setOpaque(true);
        initComponents();
    }

    /**
     * Initializes and adds all UI components to the panel.
     */
    private void initComponents() {
        // set the font and size for sort panel
        Font largeFont = new Font("SansSerif", Font.PLAIN, 16);
        // add count label
        countLabel.setFont(largeFont);
        countLabel.setOpaque(false);
        add(countLabel);
        add(Box.createHorizontalStrut(20));

        // add sort field selection box
        JLabel sortByLabel = new JLabel("Sort by:");
        sortByLabel.setFont(largeFont); // 🔧 apply font
        add(sortByLabel);
        sortFieldComboBox.setFont(largeFont);
        add(sortFieldComboBox);

        // add sort order selection box
        JLabel orderLabel = new JLabel("Order:");
        orderLabel.setFont(largeFont); // 🔧 apply font
        add(orderLabel);
        sortOrderComboBox.setFont(largeFont);
        add(sortOrderComboBox);

        // add sort button
        sortButton.setFont(largeFont);
        add(sortButton);
    }

    /**
     * Adds an action listener to the sort button.
     * Use getSelectedSortOption() in the controller to check which option is selected.
     */
    public void addSortListener(ActionListener listener) {
        sortButton.addActionListener(listener);
    }

    /**
     * Gets the selected sort option from the dropdown.
     *
     * @return the selected option as a string ("Name" or "Age")
     */
    public String getSelectedSortOption() {
        return (String) sortFieldComboBox.getSelectedItem();
    }

    /**
     * Refreshes the view to reflect the latest changes in the panel.
     */
    public void updateView() {
        // update sort options
        sortFieldComboBox.setSelectedItem("Name");
        // update sort button status
        sortButton.setEnabled(true);
    }

    /**
     * Sets the count of characters found and updates the label.
     *
     * @param count number of characters found
     */
    public void setCount(int count) {
        countLabel.setText("Found: " + count);
    }

    /**
     * Gets the selected sort order from the dropdown.
     *
     * @return the selected order ("Ascending" or "Descending")
     */
    public String getSortOrder() {
        return (String) sortOrderComboBox.getSelectedItem();
    }
}
