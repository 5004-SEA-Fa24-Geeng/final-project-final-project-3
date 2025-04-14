package controller;

import model.CharacterRecord;
import model.CharactersCollection;
import model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.MainFrame;
import view.CharacterListPanel;
import view.WishListPanel;
import view.SortPanel;
import view.FilterPanel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the MainController class.
 * 
 * This class contains unit tests for the MainController class.
 * It uses Mockito to mock the MainFrame, CharactersCollection, WishList,
 * SortController, FilterController, WishListController, CharacterListPanel,
 * and WishListPanel.
 * It also contains tests for the constructor, start, refreshCharacterList,
 * and testConstructor methods. 
*/
class MainControllerTest {
    private MainFrame mainFrame;
    private CharactersCollection charactersCollection;
    private WishList wishList;
    private SortController sortController;
    private FilterController filterController;
    private WishListController wishListController;
    private CharacterListPanel characterListPanel;
    private WishListPanel wishListPanel;
    private SortPanel sortPanel;
    private FilterPanel filterPanel;
    private MainController controller;
    
    @BeforeEach
    void setUp() {
        // create all mock objects
        mainFrame = mock(MainFrame.class);
        charactersCollection = mock(CharactersCollection.class);
        wishList = mock(WishList.class);
        sortController = mock(SortController.class);
        filterController = mock(FilterController.class);
        wishListController = mock(WishListController.class);
        characterListPanel = mock(CharacterListPanel.class);
        wishListPanel = mock(WishListPanel.class);
        sortPanel = mock(SortPanel.class);
        filterPanel = mock(FilterPanel.class);
        
        // set the return value of MainFrame
        when(mainFrame.getCharacterListPanel()).thenReturn(characterListPanel);
        when(mainFrame.getWishListPanel()).thenReturn(wishListPanel);
        when(mainFrame.getSortPanel()).thenReturn(sortPanel);
        when(mainFrame.getFilterPanel()).thenReturn(filterPanel);
        
        // create a new MainController instance, using dependency injection
        controller = new MainController(
            mainFrame,
            charactersCollection,
            wishList,
            sortController,
            filterController,
            wishListController
        );
    }
    
    private void refreshCharacterList() {
        // empty implementation, only for testing
    }
    
    /**
     * Tests the constructor of the MainController class.
     * 
     * This method checks if the controller is initialized and if the main frame is visible.
     */
    @Test
    void testConstructor() {
        verify(mainFrame).setVisible(true);
    }
    
    /**
     * Tests the start method of the MainController class.
     * 
     * This method checks if the controller starts the application and if the characters collection is loaded.
     */
    @Test
    void testStart() {
        // prepare test data
        List<CharacterRecord> characters = new ArrayList<>();
        when(charactersCollection.getFilteredCharacters()).thenReturn(characters);
        
        // execute the start operation
        controller.start();
        
        // verify the result
        verify(charactersCollection).loadData();
        verify(characterListPanel).setCharacterList(characters);
        verify(sortController).updateView();
        verify(filterController).updateView();
        verify(wishListController).updateView();
    }
    
    @Test
    void testCharacterListUpdate() {
        // prepare test data
        List<CharacterRecord> characters = new ArrayList<>();
        when(charactersCollection.getFilteredCharacters()).thenReturn(characters);
        
        // execute the start operation
        controller.start();
        
        // verify the result
        verify(characterListPanel).setCharacterList(characters);
    }
} 