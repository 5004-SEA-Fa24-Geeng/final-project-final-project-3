package view;

import model.CharacterRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class CharacterListPanelTest {
    private CharacterListPanel characterListPanel;
    private SortPanel sortPanel;
    private List<CharacterRecord> testCharacters;

    @BeforeEach
    void setUp() {
        sortPanel = new SortPanel();
        characterListPanel = new CharacterListPanel(sortPanel);
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
    void testSetAddToWishListListener() {
        // test setting listener
        final boolean[] listenerCalled = new boolean[1];
        final int[] characterId = new int[1];
        
        CharacterListPanel.AddToWishListListener listener = id -> {
            listenerCalled[0] = true;
            characterId[0] = id;
        };
        
        characterListPanel.setAddToWishListListener(listener);
        assertTrue(characterListPanel.isAddToWishListListenerSet(), "Listener should be set");
    }

    @Test
    void testSetCharacterList() {
        // test setting character list
        characterListPanel.setCharacterList(testCharacters);
        
        // verify number of components in panel
        Component[] components = ((JPanel)((JScrollPane)characterListPanel.getComponent(0))
                .getViewport().getView()).getComponents();
        assertEquals(2, components.length, "Should display 2 character panels");
        
        // verify content of each character panel
        for (Component component : components) {
            JPanel characterPanel = (JPanel) component;
            JPanel infoPanel = (JPanel) characterPanel.getComponent(1);
            
            // verify labels in info panel
            Component[] labels = infoPanel.getComponents();
            assertTrue(labels.length >= 5, "Should have at least 5 labels for character info");
            
            // verify label content
            boolean hasNameLabel = false;
            boolean hasAgeLabel = false;
            for (Component label : labels) {
                if (label instanceof JLabel) {
                    String text = ((JLabel) label).getText();
                    if (text.startsWith("Name:")) hasNameLabel = true;
                    if (text.startsWith("Age:")) hasAgeLabel = true;
                }
            }
            assertTrue(hasNameLabel, "Should have name label");
            assertTrue(hasAgeLabel, "Should have age label");
        }
    }

    @Test
    void testUpdateView() {
        // test updating view
        characterListPanel.setCharacterList(testCharacters);
        characterListPanel.updateView();
        
        // verify panel is visible
        assertTrue(characterListPanel.isVisible(), "Panel should be visible after update");
    }

    @Test
    void testAddToWishListButtonClick() {
        // test adding to wish list button click
        final boolean[] listenerCalled = new boolean[1];
        final int[] addedCharacterId = new int[1];
        
        CharacterListPanel.AddToWishListListener listener = id -> {
            listenerCalled[0] = true;
            addedCharacterId[0] = id;
        };
        
        characterListPanel.setAddToWishListListener(listener);
        characterListPanel.setCharacterList(testCharacters);
        
        // find and click add button in first character panel
        Component[] components = ((JPanel)((JScrollPane)characterListPanel.getComponent(0))
                .getViewport().getView()).getComponents();
        JPanel firstCharacterPanel = (JPanel) components[0];
        JPanel buttonPanel = (JPanel) firstCharacterPanel.getComponent(2);
        JButton addButton = (JButton) buttonPanel.getComponent(0);
        
        addButton.doClick();
        
        assertTrue(listenerCalled[0], "Listener should be called when add button is clicked");
        assertEquals(1, addedCharacterId[0], "First character ID should be 1");
    }
} 