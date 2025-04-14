package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class FilterPanelTest {
    private FilterPanel filterPanel;
    private JTextField searchField;
    private JTextField minAgeField;
    private JTextField maxAgeField;
    private JCheckBox femaleCheckBox;
    private JCheckBox maleCheckBox;
    private JCheckBox otherCheckBox;
    private JList<String> zodiacList;
    private JButton searchButton;
    private JButton resetButton;
    private JLabel statusLabel;

    @BeforeEach
    void setUp() {
        filterPanel = new FilterPanel();
        // initialize components
        initializeComponents();
    }

    private void initializeComponents() {
        // traverse all components and find the needed components
        for (Component component : filterPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component subComponent : panel.getComponents()) {
                    if (subComponent instanceof JPanel) {
                        JPanel innerPanel = (JPanel) subComponent;
                        for (Component innerComponent : innerPanel.getComponents()) {
                            if (innerComponent instanceof JTextField) {
                                JTextField textField = (JTextField) innerComponent;
                                if (textField.getColumns() == 15) { // search field
                                    searchField = textField;
                                } else if (textField.getColumns() == 5) { // age input field
                                    if (minAgeField == null) {
                                        minAgeField = textField;
                                    } else {
                                        maxAgeField = textField;
                                    }
                                }
                            } else if (innerComponent instanceof JCheckBox) {
                                JCheckBox checkBox = (JCheckBox) innerComponent;
                                if (checkBox.getText().equals("Female")) {
                                    femaleCheckBox = checkBox;
                                } else if (checkBox.getText().equals("Male")) {
                                    maleCheckBox = checkBox;
                                } else if (checkBox.getText().equals("Non-binary")) {
                                    otherCheckBox = checkBox;
                                }
                            } else if (innerComponent instanceof JButton) {
                                JButton button = (JButton) innerComponent;
                                if (button.getText().equals("Search")) {
                                    searchButton = button;
                                } else if (button.getText().equals("Reset")) {
                                    resetButton = button;
                                }
                            } else if (innerComponent instanceof JScrollPane) {
                                JScrollPane scrollPane = (JScrollPane) innerComponent;
                                zodiacList = (JList<String>) scrollPane.getViewport().getView();
                            }
                        }
                    } else if (subComponent instanceof JLabel) {
                        JLabel label = (JLabel) subComponent;
                        if (label.getText().equals("Ready")) {
                            statusLabel = label;
                        }
                    }
                }
            }
        }
    }

    @Test
    void testGetSearchKeyword() {
        assertNotNull(searchField, "Search field should exist");
        searchField.setText("test keyword");
        assertEquals("test keyword", filterPanel.getSearchKeyword(), "Search keyword should match");
    }

    @Test
    void testGetMinMaxAge() {
        assertNotNull(minAgeField, "Min age field should exist");
        assertNotNull(maxAgeField, "Max age field should exist");
        
        minAgeField.setText("18");
        maxAgeField.setText("30");
        
        assertEquals("18", filterPanel.getMinAge(), "Min age should be 18");
        assertEquals("30", filterPanel.getMaxAge(), "Max age should be 30");
    }

    @Test
    void testGetSelectedGenders() {
        assertNotNull(femaleCheckBox, "Female checkbox should exist");
        assertNotNull(maleCheckBox, "Male checkbox should exist");
        assertNotNull(otherCheckBox, "Other checkbox should exist");
        
        femaleCheckBox.setSelected(true);
        List<Integer> genders = filterPanel.getSelectedGenders();
        assertEquals(1, genders.size(), "Should have one gender selected");
        assertEquals(1, genders.get(0), "Selected gender should be female (1)");
        
        femaleCheckBox.setSelected(false);
        maleCheckBox.setSelected(true);
        otherCheckBox.setSelected(true);
        genders = filterPanel.getSelectedGenders();
        assertEquals(2, genders.size(), "Should have two genders selected");
        assertTrue(genders.contains(2), "Should contain male (2)");
        assertTrue(genders.contains(3), "Should contain other (3)");
    }

    @Test
    void testGetSelectedZodiacs() {
        assertNotNull(zodiacList, "Zodiac list should exist");
        zodiacList.setSelectedIndices(new int[]{0, 1});
        List<String> selectedZodiacs = filterPanel.getSelectedZodiacs();
        assertEquals(2, selectedZodiacs.size(), "Should have two zodiacs selected");
    }

    @Test
    void testResetFilters() {
        searchField.setText("test");
        minAgeField.setText("18");
        maxAgeField.setText("30");
        femaleCheckBox.setSelected(true);
        zodiacList.setSelectedIndex(0);
        
        filterPanel.resetFilters();
        
        assertEquals("", filterPanel.getSearchKeyword(), "Search keyword should be empty");
        assertEquals("", filterPanel.getMinAge(), "Min age should be empty");
        assertEquals("", filterPanel.getMaxAge(), "Max age should be empty");
        assertFalse(femaleCheckBox.isSelected(), "Female checkbox should be unselected");
        assertTrue(filterPanel.getSelectedZodiacs().isEmpty(), "No zodiac should be selected");
    }

    @Test
    void testSetStatusMessage() {
        assertNotNull(statusLabel, "Status label should exist");
        assertEquals("Ready", statusLabel.getText(), "Initial status message should be 'Ready'");
        filterPanel.setStatusMessage("Test message", Color.RED);
        assertEquals("Test message", statusLabel.getText(), "Status message should match");
        assertEquals(Color.RED, statusLabel.getForeground(), "Status color should be red");
    }

    @Test
    void testAddSearchListener() {
        assertNotNull(searchButton, "Search button should exist");
        final boolean[] listenerCalled = new boolean[1];
        ActionListener listener = e -> listenerCalled[0] = true;
        
        filterPanel.addSearchListener(listener);
        searchButton.doClick();
        
        assertTrue(listenerCalled[0], "Search listener should be called");
    }

    @Test
    void testAddResetListener() {
        assertNotNull(resetButton, "Reset button should exist");
        final boolean[] listenerCalled = new boolean[1];
        ActionListener listener = e -> listenerCalled[0] = true;
        
        filterPanel.addResetListener(listener);
        resetButton.doClick();
        
        assertTrue(listenerCalled[0], "Reset listener should be called");
    }
}
