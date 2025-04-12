package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

import model.CharacterRecord;

public class SortPanel extends JPanel {
    private final JComboBox<String> sortFieldComboBox = new JComboBox<>(new String[]{"Name", "Age", "Popularity"});
    private final JComboBox<String> sortOrderComboBox = new JComboBox<>(new String[]{"Ascending", "Descending"});
    private final JButton sortButton = new JButton("Sort");
    private final JLabel countLabel = new JLabel("Found: 0");
    private String currentSortOption;
    private DefaultListModel<CharacterRecord> listModel;
    private java.util.List<CharacterRecord> sortedCharacters;

    public SortPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setPreferredSize(new Dimension(600, 40));
        initComponents();
    }

    private void initComponents() {
        // add count label
        add(countLabel);
        add(Box.createHorizontalStrut(20));

        // add sort field selection box
        add(new JLabel("Sort by:"));
        add(sortFieldComboBox);

        // add sort order selection box
        add(new JLabel("Order:"));
        add(sortOrderComboBox);

        // add sort button
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

    public void updateView() {
        // update sort options
        sortFieldComboBox.setSelectedItem("Name");
        // update sort button status
        sortButton.setEnabled(true);
    }

    public void setCount(int count) {
        countLabel.setText("Found: " + count);
    }

    public String getSortOrder() {
        return (String) sortOrderComboBox.getSelectedItem();
    }
}
