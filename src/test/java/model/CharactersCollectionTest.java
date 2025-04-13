package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CharactersCollectionTest {
    private CharactersCollection collection;
    private static final List<CharacterRecord> MOCK_DATA = List.of(
            new CharacterRecord(1, "Actor A", 1, 30, 80.0, "Aries",
                    "Acting", "1990-01-01", "US", "/a.jpg"),
            new CharacterRecord(2, "Director B", 2, 45, 90.5, "Gemini",
                    "Directing", "1980-05-15", "UK", "/b.jpg"),
            new CharacterRecord(3, "Producer C", 3, 35, 75.3, "Leo",
                    "Production", "1995-12-24", "CA", "/c.jpg")
    );

    @BeforeEach
    void setup() {
        // Mock JSON文件读取
        try (MockedStatic<JSONFileHandler> mocked = mockStatic(JSONFileHandler.class)) {
            mocked.when(() -> JSONFileHandler.readJsonFile(anyString()))
                    .thenReturn(new ArrayList<>(MOCK_DATA));
            collection = new CharactersCollection();
        }
    }

    @Test
    void constructor_ShouldLoadDataCorrectly() {
        assertEquals(3, collection.getAllCharacters().size());
        assertEquals(3, collection.getFilteredCharacters().size());
    }

    @Test
    void applyFilters_ShouldUpdateFilteredList() {
        // Age filter
        Filter ageFilter = c -> c.getAge() > 30;
        collection.applyFilters(ageFilter);

        List<CharacterRecord> result = collection.getFilteredCharacters();
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getAge() > 30));
    }

    @Test
    void getSorted_ShouldReturnOrderedList() {
        // Name descending sort
        Comparator<CharacterRecord> nameComparator =
                (c1, c2) -> c2.getName().compareTo(c1.getName());

        List<CharacterRecord> sorted = collection.getSorted(
                collection.getAllCharacters(),
                nameComparator
        );

        assertEquals("Producer C", sorted.get(0).getName());
        assertEquals("Director B", sorted.get(1).getName());
        assertEquals("Actor A", sorted.get(2).getName());
    }

    @Test
    void loadData_ShouldResetFilteredList() {
        // Apply filter first
        collection.applyFilters(c -> c.getId() == 1);
        assertEquals(1, collection.getFilteredCharacters().size());

        // Reload data
        try (MockedStatic<JSONFileHandler> mocked = mockStatic(JSONFileHandler.class)) {
            mocked.when(() -> JSONFileHandler.readJsonFile(anyString()))
                    .thenReturn(new ArrayList<>(MOCK_DATA));
            collection.loadData();
        }

        assertEquals(3, collection.getFilteredCharacters().size());
    }

    @Test
    void setFilteredCharacters_ShouldReplaceCurrentList() {
        List<CharacterRecord> newList = List.of(MOCK_DATA.get(0));
        collection.setFilteredCharacters(newList);

        assertEquals(1, collection.getFilteredCharacters().size());
        assertNotSame(newList, collection.getFilteredCharacters());
    }

    @Test
    void getFiltered_ShouldReturnUnmodifiableList() {
        assertThrows(UnsupportedOperationException.class, () -> {
            collection.getFilteredCharacters().add(MOCK_DATA.get(0));
        });
    }

    @Test
    void emptyDataSource_ShouldHandleGracefully() {
        try (MockedStatic<JSONFileHandler> mocked = mockStatic(JSONFileHandler.class)) {
            mocked.when(() -> JSONFileHandler.readJsonFile(anyString()))
                    .thenReturn(Collections.emptyList());

            CharactersCollection emptyCollection = new CharactersCollection();
            assertEquals(0, emptyCollection.getAllCharacters().size());
            assertEquals(0, emptyCollection.getFilteredCharacters().size());
        }
    }

    @Test
    void filterChaining_ShouldApplyLatestFilter() {
        collection.applyFilters(c -> c.getAge() > 30);
        assertEquals(2, collection.getFilteredCharacters().size());

        collection.applyFilters(c -> c.getPopularity() < 80);
        assertEquals(1, collection.getFilteredCharacters().size());
    }

    @Test
    void sortingEmptyList_ShouldReturnEmpty() {
        collection.applyFilters(c -> false);
        List<CharacterRecord> result = collection.getSorted(
                collection.getFilteredCharacters(),
                Comparator.comparing(CharacterRecord::getName)
        );
        assertTrue(result.isEmpty());
    }
}
