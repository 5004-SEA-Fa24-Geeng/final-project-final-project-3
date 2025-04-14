package controller;

import model.CharacterRecord;
import model.CharactersCollection;
import model.Response;
import model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.CharacterListPanel;
import view.WishListPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for WishListController
 * 
 * This class contains unit tests for the WishListController class.
 * It tests the constructor, handleAddToWishList, handleRemoveSingleCharacter,
 * handleClearWishList, and updateView methods.
 */
class WishListControllerTest {
    @Mock
    private WishList wishListModel; // mock the wish list model
    
    @Mock
    private WishListPanel wishListPanel; // mock the wish list panel
    
    @Mock
    private CharacterListPanel characterListPanel; // mock the character list panel
    
    @Mock
    private CharactersCollection charactersCollection; // mock the characters collection
    
    private WishListController controller; // the controller to be tested
    
    @BeforeEach
    // set up the test
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new WishListController(wishListModel, wishListPanel, characterListPanel, charactersCollection);
    }
    
    @Test
    // test the constructor
    void testConstructor() {
        assertNotNull(controller, "Controller should be initialized");
        verify(characterListPanel).setAddToWishListListener(any());
        verify(wishListPanel).setRemoveCharacterListener(any());
        verify(wishListPanel).addClearWishListListener(any());
        verify(wishListPanel).addSaveToFileListener(any());
    }
    
    @Test
    // test the handleAddToWishList method with success
    void testHandleAddToWishListSuccess() {
        // prepare test data
        int characterId = 1;
        CharacterRecord character = new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path");
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(character);
        
        when(charactersCollection.getFilteredCharacters()).thenReturn(characters);
        when(wishListModel.addCharacter(characterId)).thenReturn(true);
        
        // execute add operation
        Response response = controller.handleAddToWishList(characterId);
        
        // verify result
        assertEquals(200, response.getStatus(), "Adding should be successful");
        assertTrue(response.getMessage().contains("Successfully Add The Character!"), 
            "Success message should be returned");
    }
    
    @Test
    // test the handleAddToWishList method with character not found
    void testHandleAddToWishListCharacterNotFound() {
        // prepare test data
        int characterId = 1;
        List<CharacterRecord> characters = new ArrayList<>();
        
        when(charactersCollection.getFilteredCharacters()).thenReturn(characters);
        
        // execute add operation
        Response response = controller.handleAddToWishList(characterId);
        
        // verify result
        assertEquals(400, response.getStatus(), "Adding should fail");
        assertTrue(response.getMessage().contains("The character doesn't exist!"), 
            "Error message should indicate character not found");
    }
    
    @Test
    // test the handleAddToWishList method with character already exists
    void testHandleAddToWishListCharacterAlreadyExists() {
        // prepare test data
        int characterId = 1;
        CharacterRecord character = new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path");
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(character);
        
        when(charactersCollection.getFilteredCharacters()).thenReturn(characters);
        when(wishListModel.addCharacter(characterId)).thenReturn(false);
        
        // execute add operation
        Response response = controller.handleAddToWishList(characterId);
        
        // verify result
        assertEquals(400, response.getStatus(), "Adding should fail");
        assertTrue(response.getMessage().contains("Character Already Exists!"), 
            "Error message should indicate character already exists");
    }
    
    @Test
    // test the handleRemoveSingleCharacter method with success
    void testHandleRemoveSingleCharacterSuccess() {
        // prepare test data
        int characterId = 1;
        CharacterRecord character = new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path");
        
        when(wishListModel.getCharacterById(characterId)).thenReturn(character);
        when(wishListModel.removeSingleCharacter(characterId)).thenReturn(true);
        
        // execute remove operation
        Response response = controller.handleRemoveSingleCharacter(characterId);
        
        // verify result
        assertEquals(200, response.getStatus(), "Removing should be successful");
        assertTrue(response.getMessage().contains("Successfully Remove The Character!"), 
            "Success message should be returned");
    }
    
    @Test
    // test the handleRemoveSingleCharacter method with character not found
    void testHandleRemoveSingleCharacterNotFound() {
        // prepare test data
        int characterId = 1;
        
        when(wishListModel.getCharacterById(characterId)).thenReturn(null);
        
        // execute remove operation
        Response response = controller.handleRemoveSingleCharacter(characterId);
        
        // verify result
        assertEquals(400, response.getStatus(), "Removing should fail");
        assertTrue(response.getMessage().contains("The character doesn't exist!"), 
            "Error message should indicate character not found");
    }
    
    @Test
    // test the handleClearWishList method with success
    void testHandleClearWishListSuccess() {
        // prepare test data
        when(wishListModel.removeAllCharacters()).thenReturn(true);
        
        // execute clear operation
        Response response = controller.handleClearWishList();
        
        // verify result
        assertEquals(200, response.getStatus(), "Clearing should be successful");
        assertTrue(response.getMessage().contains("Successfully Clear The Wish List!"), 
            "Success message should be returned");
    }
    
    @Test
    // test the handleClearWishList method with failure
    void testHandleClearWishListFailure() {
        // prepare test data
        when(wishListModel.removeAllCharacters()).thenReturn(false);
        
        // execute clear operation
        Response response = controller.handleClearWishList();
        
        // verify result
        assertEquals(400, response.getStatus(), "Clearing should fail");
        assertTrue(response.getMessage().contains("Failed To Clear The Wish List!"), 
            "Error message should indicate failure");
    }
    
    @Test
    // test the updateView method
    void testUpdateView() {
        // prepare test data
        Set<CharacterRecord> wishList = mock(Set.class);
        List<CharacterRecord> updatedWishList = new ArrayList<>();
        
        when(wishListModel.getWishList()).thenReturn(wishList);
        when(wishListModel.getWishList().toArray()).thenReturn(new CharacterRecord[0]);
        
        // execute update view
        controller.updateView();
        
        // verify result
        verify(wishListPanel).setWishList(any());
    }
} 