package view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import model.CharacterRecord;
import org.junit.jupiter.api.BeforeEach;
import javax.swing.*;
import java.util.List;

/**
 * Test class for the WishListPanel class.
 * 
 * This class contains unit tests for the WishListPanel class.
 * It tests the setRemoveCharacterListener, setWishList, addClearWishListListener, addSaveToFileListener, updateView, testSetWishList, testSetEmptyWishList, testRemoveCharacterListener, testClearButtonListener, and testSaveButtonListener methods.
 */
public class WishListPanelTest {
    private WishListPanel wishListPanel;
    private List<CharacterRecord> testCharacters;

    @BeforeEach
    // set up the test
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
    // test the setRemoveCharacterListener method
    void setRemoveCharacterListener() {
        WishListPanel.RemoveCharacterListener listener = characterId -> {};
        wishListPanel.setRemoveCharacterListener(listener);
        assertNotNull(wishListPanel);
    }

    @Test
    // test the setWishList method
    void setWishList() {
        wishListPanel.setWishList(testCharacters);
        assertNotNull(wishListPanel);
    }

    @Test
    // test the addClearWishListListener method
    void addClearWishListListener() {
        ActionListener listener = e -> {};
        wishListPanel.addClearWishListListener(listener);
        assertNotNull(wishListPanel);
    }

    @Test
    // test the addSaveToFileListener method
    void addSaveToFileListener() {
        ActionListener listener = e -> {};
        wishListPanel.addSaveToFileListener(listener);
        assertNotNull(wishListPanel);
    }

    @Test
    // test the updateView method
    void updateView() {
        wishListPanel.updateView();
        assertNotNull(wishListPanel);
    }

    @Test
    // test the testSetWishList method
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
    // test the testSetEmptyWishList method
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
    // test the testRemoveCharacterListener method
    void testRemoveCharacterListener() {
        final int[] removedId = new int[1];
        wishListPanel.setRemoveCharacterListener(id -> removedId[0] = id);

        // set test data
        wishListPanel.setWishList(testCharacters);

        JScrollPane scrollPane = (JScrollPane) wishListPanel.getComponent(0);
        JPanel contentPanel = (JPanel) scrollPane.getViewport().getView();
        JPanel characterPanel = (JPanel) contentPanel.getComponent(0);
        JPanel buttonPanel = (JPanel) characterPanel.getComponent(2);  // EAST panel
        JPanel topRightWrapper = (JPanel) buttonPanel.getComponent(0); // wrapper inside NORTH
        JButton removeButton = (JButton) topRightWrapper.getComponent(0); // the actual "-" button
        removeButton.doClick();

        assertEquals(1, removedId[0], "Remove listener should be called with correct character ID");
    }

    @Test
    // test the testClearButtonListener method
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
    // test the testSaveButtonListener method
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