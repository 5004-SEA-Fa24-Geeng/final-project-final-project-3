package view;

import model.CharacterRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class SortPanelTest {
    private SortPanel sortPanel;
    private List<CharacterRecord> testCharacters;

    @BeforeEach
    void setUp() {
        sortPanel = new SortPanel();
        testCharacters = new ArrayList<>();
        
        // create test characters
        CharacterRecord character1 = new CharacterRecord();
        character1.setId(1);
        character1.setName("Test Character 1");
        character1.setAge(25);
        character1.setGender(1);
        character1.setZodiacSign("Aries");
        character1.setOccupation("Actor");
        character1.setProfile("/test1.jpg");
        
        CharacterRecord character2 = new CharacterRecord();
        character2.setId(2);
        character2.setName("Test Character 2");
        character2.setAge(30);
        character2.setGender(2);
        character2.setZodiacSign("Leo");
        character2.setOccupation("Director");
        character2.setProfile("/test2.jpg");
        
        testCharacters.add(character1);
        testCharacters.add(character2);
    }

    @Test
    void testGetSelectedSortOption() {
        // test getting selected sort option
        JComboBox<String> sortFieldComboBox = null;
        for (int i = 0; i < sortPanel.getComponentCount(); i++) {
            if (sortPanel.getComponent(i) instanceof JComboBox) {
                sortFieldComboBox = (JComboBox<String>) sortPanel.getComponent(i);
                break;
            }
        }
        assertNotNull(sortFieldComboBox, "Sort field combo box should exist");
        sortFieldComboBox.setSelectedItem("Age");
        assertEquals("Age", sortPanel.getSelectedSortOption(), "Selected sort option should be Age");
    }

    @Test
    void testGetSortOrder() {
        // test getting sort order
        JComboBox<String> sortOrderComboBox = null;
        int comboBoxCount = 0;
        for (int i = 0; i < sortPanel.getComponentCount(); i++) {
            if (sortPanel.getComponent(i) instanceof JComboBox) {
                comboBoxCount++;
                if (comboBoxCount == 2) {  // 第二个 JComboBox 是 sortOrderComboBox
                    sortOrderComboBox = (JComboBox<String>) sortPanel.getComponent(i);
                    break;
                }
            }
        }
        assertNotNull(sortOrderComboBox, "Sort order combo box should exist");
        
        // set to Descending
        sortOrderComboBox.setSelectedItem("Descending");
        assertEquals("Descending", sortPanel.getSortOrder(), "Sort order should be Descending");
        
        // set to Ascending
        sortOrderComboBox.setSelectedItem("Ascending");
        assertEquals("Ascending", sortPanel.getSortOrder(), "Sort order should be Ascending");
    }

    @Test
    void testSetCount() {
        // test setting count
        sortPanel.setCount(5);
        JLabel countLabel = null;
        for (int i = 0; i < sortPanel.getComponentCount(); i++) {
            if (sortPanel.getComponent(i) instanceof JLabel) {
                countLabel = (JLabel) sortPanel.getComponent(i);
                break;
            }
        }
        assertNotNull(countLabel, "Count label should exist");
        assertEquals("Found: 5", countLabel.getText(), "Count label should show correct count");
    }

    @Test
    void testAddSortListener() {
        // test adding sort listener
        final boolean[] listenerCalled = new boolean[1];
        sortPanel.addSortListener(e -> listenerCalled[0] = true);
        
        // simulate click sort button
        JButton sortButton = null;
        for (int i = 0; i < sortPanel.getComponentCount(); i++) {
            if (sortPanel.getComponent(i) instanceof JButton) {
                sortButton = (JButton) sortPanel.getComponent(i);
                break;
            }
        }
        assertNotNull(sortButton, "Sort button should exist");
        sortButton.doClick();
        
        // verify listener is called
        assertTrue(listenerCalled[0], "Sort listener should be called");
    }

    @Test
    void testUpdateView() {
        // test updating view
        sortPanel.updateView();
        
        // verify sort options are reset to default values
        JComboBox<String> sortFieldComboBox = null;
        for (int i = 0; i < sortPanel.getComponentCount(); i++) {
            if (sortPanel.getComponent(i) instanceof JComboBox) {
                sortFieldComboBox = (JComboBox<String>) sortPanel.getComponent(i);
                break;
            }
        }
        assertNotNull(sortFieldComboBox, "Sort field combo box should exist");
        assertEquals("Name", sortFieldComboBox.getSelectedItem(), "Sort field should be reset to Name");
        
        // verify sort button is enabled
        JButton sortButton = null;
        for (int i = 0; i < sortPanel.getComponentCount(); i++) {
            if (sortPanel.getComponent(i) instanceof JButton) {
                sortButton = (JButton) sortPanel.getComponent(i);
                break;
            }
        }
        assertNotNull(sortButton, "Sort button should exist");
        assertTrue(sortButton.isEnabled(), "Sort button should be enabled");
    }
}