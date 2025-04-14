package view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import model.CharacterRecord;
import org.junit.jupiter.api.BeforeEach;
import javax.swing.*;
import java.util.List;

public class WishListPanelTest {
    private WishListPanel wishListPanel;
    private List<CharacterRecord> testCharacters;

    @BeforeEach
    void setUp() {
        wishListPanel = new WishListPanel();
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
    void setRemoveCharacterListener() {
        WishListPanel.RemoveCharacterListener listener = characterId -> {};
        wishListPanel.setRemoveCharacterListener(listener);
        assertNotNull(wishListPanel);
    }

    @Test
    void setWishList() {
        wishListPanel.setWishList(testCharacters);
        assertNotNull(wishListPanel);
    }

    @Test
    void addClearWishListListener() {
        ActionListener listener = e -> {};
        wishListPanel.addClearWishListListener(listener);
        assertNotNull(wishListPanel);
    }

    @Test
    void addSaveToFileListener() {
        ActionListener listener = e -> {};
        wishListPanel.addSaveToFileListener(listener);
        assertNotNull(wishListPanel);
    }

    @Test
    void updateView() {
        wishListPanel.updateView();
        assertNotNull(wishListPanel);
    }

    @Test
    void testSetWishList() {
        // test setting wish list
        wishListPanel.setWishList(testCharacters);
        
        // verify button status
        JPanel buttonPanel = (JPanel) wishListPanel.getComponent(1);
        JButton clearButton = (JButton) buttonPanel.getComponent(0);
        JButton saveButton = (JButton) buttonPanel.getComponent(1);
        assertTrue(clearButton.isEnabled(), "Clear button should be enabled");
        assertTrue(saveButton.isEnabled(), "Save button should be enabled");
    }

    @Test
    void testSetEmptyWishList() {
        // test setting empty wish list
        wishListPanel.setWishList(new ArrayList<>());
        
        // verify button status
        JPanel buttonPanel = (JPanel) wishListPanel.getComponent(1);
        JButton clearButton = (JButton) buttonPanel.getComponent(0);
        JButton saveButton = (JButton) buttonPanel.getComponent(1);
        assertFalse(clearButton.isEnabled(), "Clear button should be disabled");
        assertFalse(saveButton.isEnabled(), "Save button should be disabled");
    }

    @Test
    void testRemoveCharacterListener() {
        // test remove character listener
        final int[] removedId = new int[1];
        wishListPanel.setRemoveCharacterListener(id -> removedId[0] = id);
        
        // set test data
        wishListPanel.setWishList(testCharacters);
        
        // simulate click remove button
        JScrollPane scrollPane = (JScrollPane) wishListPanel.getComponent(0);
        JPanel contentPanel = (JPanel) scrollPane.getViewport().getView();
        JPanel characterPanel = (JPanel) contentPanel.getComponent(0);
        JPanel buttonPanel = (JPanel) characterPanel.getComponent(2);
        JButton removeButton = (JButton) buttonPanel.getComponent(0);
        removeButton.doClick();
        
        // verify listener is called
        assertEquals(1, removedId[0], "Remove listener should be called with correct character ID");
    }

    @Test
    void testClearButtonListener() {
        // test clear button listener
        final boolean[] listenerCalled = new boolean[1];
        wishListPanel.addClearWishListListener(e -> listenerCalled[0] = true);
        
        // set test data
        wishListPanel.setWishList(testCharacters);
        
        // simulate click clear button
        JPanel buttonPanel = (JPanel) wishListPanel.getComponent(1);
        JButton clearButton = (JButton) buttonPanel.getComponent(0);
        clearButton.doClick();
        
        // verify listener is called
        assertTrue(listenerCalled[0], "Clear button listener should be called");
    }

    @Test
    void testSaveButtonListener() {
        // test save button listener
        final boolean[] listenerCalled = new boolean[1];
        wishListPanel.addSaveToFileListener(e -> listenerCalled[0] = true);
        
        // set test data
        wishListPanel.setWishList(testCharacters);
        
        // simulate click save button
        JPanel buttonPanel = (JPanel) wishListPanel.getComponent(1);
        JButton saveButton = (JButton) buttonPanel.getComponent(1);
        saveButton.doClick();
        
        // verify listener is called
        assertTrue(listenerCalled[0], "Save button listener should be called");
    }
}