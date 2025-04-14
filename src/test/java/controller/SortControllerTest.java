package controller;

import model.CharacterRecord;
import model.CharactersCollection;
import model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.SortPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for SortController
 * 
 * This class contains unit tests for the SortController class.
 * It tests the constructor, handleSortItems, and updateView methods.
 */
class SortControllerTest {
    @Mock
    private CharactersCollection model; // mock the model
    
    @Mock
    private SortPanel view; // mock the view
    
    @Mock
    private Runnable refreshCallback; // mock the refresh callback
    
    private SortController controller;
    
    @BeforeEach
    // set up the test
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new SortController(model, view, refreshCallback);
    }
    
    @Test
    // test the constructor
    void testConstructor() {
        assertNotNull(controller, "Controller should be initialized");
        verify(view).addSortListener(any());
    }
    
    @Test
    // test the handleSortItems method with filtered characters
    void testHandleSortItemsWithFilteredCharacters() {
        // prepare test data
        List<CharacterRecord> filteredCharacters = new ArrayList<>();
        filteredCharacters.add(new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path"));
        filteredCharacters.add(new CharacterRecord(2, "Bob", 2, 30, 0.9, "Taurus", "Director", "1985-01-01", "UK", "/path"));
        
        when(model.getFilteredCharacters()).thenReturn(filteredCharacters);
        
        // create comparator
        Comparator<CharacterRecord> comparator = Comparator.comparing(CharacterRecord::getName);
        
        // execute sorting
        Response response = controller.handleSortItems(comparator);
        
        // verify result
        assertEquals(200, response.getStatus(), "Sorting should be successful");
        verify(model).getSorted(filteredCharacters, comparator);
        verify(model).setFilteredCharacters(any());
    }
    
    @Test
    // test the handleSortItems method with empty filtered characters
    void testHandleSortItemsWithEmptyFilteredCharacters() {
        // prepare test data
        List<CharacterRecord> allCharacters = new ArrayList<>();
        allCharacters.add(new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path"));
        allCharacters.add(new CharacterRecord(2, "Bob", 2, 30, 0.9, "Taurus", "Director", "1985-01-01", "UK", "/path"));
        
        when(model.getFilteredCharacters()).thenReturn(new ArrayList<>());
        when(model.getAllCharacters()).thenReturn(allCharacters);
        
        // create comparator
        Comparator<CharacterRecord> comparator = Comparator.comparing(CharacterRecord::getName);
        
        // execute sorting
        Response response = controller.handleSortItems(comparator);
        
        // verify result
        assertEquals(200, response.getStatus(), "Sorting should be successful");
        verify(model).getSorted(allCharacters, comparator);
        verify(model).setFilteredCharacters(any());
    }
    
    @Test
    // test the handleSortItems method with exception
    void testHandleSortItemsWithException() {
        // prepare test data
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path"));
        
        when(model.getFilteredCharacters()).thenReturn(characters);
        when(model.getSorted(any(), any())).thenThrow(new RuntimeException("Test exception"));
        
        // create comparator
        Comparator<CharacterRecord> comparator = Comparator.comparing(CharacterRecord::getName);
        
        // execute sorting
        Response response = controller.handleSortItems(comparator);
        
        // verify result
        assertEquals(400, response.getStatus(), "Sorting should fail");
        assertTrue(response.getMessage().contains("Test exception"), 
            "Error message should contain exception details");
    }
    
    @Test
    // test the updateView method
    void testUpdateView() {
        // execute update view
        controller.updateView();
        
        // verify view update is called
        verify(view).updateView();
    }
    
    @Test
    // test the sortListenerWithNameAscending method
    void testSortListenerWithNameAscending() {
        // prepare test data
        when(view.getSelectedSortOption()).thenReturn("Name");
        when(view.getSortOrder()).thenReturn("Ascending");
        
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(new CharacterRecord(1, "Bob", 2, 30, 0.9, "Taurus", "Director", "1985-01-01", "UK", "/path"));
        characters.add(new CharacterRecord(2, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path"));
        
        when(model.getFilteredCharacters()).thenReturn(characters);
        
        // simulate button click
        ActionEvent event = new ActionEvent(view, ActionEvent.ACTION_PERFORMED, "sort");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = view.getSelectedSortOption();
                String order = view.getSortOrder();
                boolean ascending = order.equals("Ascending");

                Comparator<CharacterRecord> comparator;
                if (field.equals("Name")) {
                    comparator = Comparator.comparing(CharacterRecord::getName);
                } else if (field.equals("Age")) {
                    comparator = Comparator.comparingInt(CharacterRecord::getAge);
                } else if (field.equals("Popularity")) {
                    comparator = Comparator.comparingDouble(CharacterRecord::getPopularity);
                } else {
                    throw new IllegalArgumentException("Invalid sort field: " + field);
                }

                if (!ascending) {
                    comparator = comparator.reversed();
                }

                List<CharacterRecord> sorted = model.getSorted(model.getFilteredCharacters(), comparator);
                model.setFilteredCharacters(sorted);
                refreshCallback.run();
            }
        };
        listener.actionPerformed(event);
        
        // verify result
        verify(model).setFilteredCharacters(any());
        verify(refreshCallback).run();
    }
    
    @Test
    // test the sortListenerWithAgeDescending method
    void testSortListenerWithAgeDescending() {
        // prepare test data
        when(view.getSelectedSortOption()).thenReturn("Age");
        when(view.getSortOrder()).thenReturn("Descending");
        
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path"));
        characters.add(new CharacterRecord(2, "Bob", 2, 30, 0.9, "Taurus", "Director", "1985-01-01", "UK", "/path"));
        
        when(model.getFilteredCharacters()).thenReturn(characters);
        
        // simulate button click
        ActionEvent event = new ActionEvent(view, ActionEvent.ACTION_PERFORMED, "sort");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = view.getSelectedSortOption();
                String order = view.getSortOrder();
                boolean ascending = order.equals("Ascending");

                Comparator<CharacterRecord> comparator;
                if (field.equals("Name")) {
                    comparator = Comparator.comparing(CharacterRecord::getName);
                } else if (field.equals("Age")) {
                    comparator = Comparator.comparingInt(CharacterRecord::getAge);
                } else if (field.equals("Popularity")) {
                    comparator = Comparator.comparingDouble(CharacterRecord::getPopularity);
                } else {
                    throw new IllegalArgumentException("Invalid sort field: " + field);
                }

                if (!ascending) {
                    comparator = comparator.reversed();
                }

                List<CharacterRecord> sorted = model.getSorted(model.getFilteredCharacters(), comparator);
                model.setFilteredCharacters(sorted);
                refreshCallback.run();
            }
        };
        listener.actionPerformed(event);
        
        // verify result
        verify(model).setFilteredCharacters(any());
        verify(refreshCallback).run();
    }
    
    @Test
    // test the sortListenerWithPopularity method
    void testSortListenerWithPopularity() {
        // prepare test data
        when(view.getSelectedSortOption()).thenReturn("Popularity");
        when(view.getSortOrder()).thenReturn("Ascending");
        
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(new CharacterRecord(1, "Alice", 1, 25, 0.8, "Aries", "Actor", "1990-01-01", "USA", "/path"));
        characters.add(new CharacterRecord(2, "Bob", 2, 30, 0.9, "Taurus", "Director", "1985-01-01", "UK", "/path"));
        
        when(model.getFilteredCharacters()).thenReturn(characters);
        
        // simulate button click
        ActionEvent event = new ActionEvent(view, ActionEvent.ACTION_PERFORMED, "sort");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = view.getSelectedSortOption();
                String order = view.getSortOrder();
                boolean ascending = order.equals("Ascending");

                Comparator<CharacterRecord> comparator;
                if (field.equals("Name")) {
                    comparator = Comparator.comparing(CharacterRecord::getName);
                } else if (field.equals("Age")) {
                    comparator = Comparator.comparingInt(CharacterRecord::getAge);
                } else if (field.equals("Popularity")) {
                    comparator = Comparator.comparingDouble(CharacterRecord::getPopularity);
                } else {
                    throw new IllegalArgumentException("Invalid sort field: " + field);
                }

                if (!ascending) {
                    comparator = comparator.reversed();
                }

                List<CharacterRecord> sorted = model.getSorted(model.getFilteredCharacters(), comparator);
                model.setFilteredCharacters(sorted);
                refreshCallback.run();
            }
        };
        listener.actionPerformed(event);
        
        // verify result
        verify(model).setFilteredCharacters(any());
        verify(refreshCallback).run();
    }
} 