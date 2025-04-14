package model;

import org.junit.jupiter.api.Test;
<<<<<<< HEAD

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SortComparatorsTest {

    private List<CharacterRecord> createSampleList() {
        CharacterRecord a = new CharacterRecord(1, "Alice", 2, 30, 80.5,
                "Aries", "Acting", "1994-02-28", "USA", "/a.jpg");
        CharacterRecord b = new CharacterRecord(2, "bob", 1, 25, 90.1,
                "Taurus", "Directing", "1999-08-15", "UK", "/b.jpg");
        CharacterRecord c = new CharacterRecord(3, "Charlie", 3, 35, 75.0,
                "Cancer", "Writing", "1988-07-10", "Canada", "/c.jpg");

        return new ArrayList<>(List.of(a, b, c));
    }

    @Test
    void testSortByName() {
        List<CharacterRecord> list = createSampleList();
        Collections.shuffle(list);

        list.sort(SortComparators.BY_NAME);

        assertEquals("Alice", list.get(0).getName());
        assertEquals("bob", list.get(1).getName());
        assertEquals("Charlie", list.get(2).getName());
    }

    @Test
    void testSortByAge() {
        List<CharacterRecord> list = createSampleList();
        Collections.shuffle(list);

        list.sort(SortComparators.BY_AGE);

        assertEquals(25, list.get(0).getAge()); // youngest
        assertEquals(30, list.get(1).getAge());
        assertEquals(35, list.get(2).getAge()); // oldest
    }

    @Test
    void testSortByPopularity() {
        List<CharacterRecord> list = createSampleList();
        Collections.shuffle(list);

        list.sort(SortComparators.BY_POPULARITY);

        assertEquals(90.1, list.get(0).getPopularity()); // most popular
        assertEquals(80.5, list.get(1).getPopularity());
        assertEquals(75.0, list.get(2).getPopularity()); // least popular
    }
}
=======
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class SortComparatorsTest {

    @Test
    void testByNameComparator() {
        // create test data
        CharacterRecord char1 = new CharacterRecord(1, "Alice", 1, 32, 80.0, "Aries", "Actor", "1990-01-01", "USA", "/profile1");
        CharacterRecord char2 = new CharacterRecord(2, "bob", 2, 31, 75.0, "Taurus", "Actor", "1991-02-02", "UK", "/profile2");
        CharacterRecord char3 = new CharacterRecord(3, "Charlie", 2, 30, 70.0, "Gemini", "Actor", "1992-03-03", "Canada", "/profile3");
        
        // create a list and sort it
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(char2);
        characters.add(char3);
        characters.add(char1);
        
        characters.sort(SortComparators.BY_NAME);
        
        // verify the sorting result
        assertEquals("Alice", characters.get(0).getName());
        assertEquals("bob", characters.get(1).getName());
        assertEquals("Charlie", characters.get(2).getName());
    }
    
    @Test
    void testByAgeComparator() {
        // create test data
        CharacterRecord char1 = new CharacterRecord(1, "Alice", 1, 32, 80.0, "Aries", "Actor", "1990-01-01", "USA", "/profile1");
        CharacterRecord char2 = new CharacterRecord(2, "Bob", 2, 31, 75.0, "Taurus", "Actor", "1991-02-02", "UK", "/profile2");
        CharacterRecord char3 = new CharacterRecord(3, "Charlie", 2, 30, 70.0, "Gemini", "Actor", "1992-03-03", "Canada", "/profile3");
        
        // create a list and sort it
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(char2);
        characters.add(char3);
        characters.add(char1);
        
        characters.sort(SortComparators.BY_AGE);
        
        // verify the sorting result (from youngest to oldest)
        assertEquals(30, characters.get(0).getAge()); // Charlie
        assertEquals(31, characters.get(1).getAge()); // Bob
        assertEquals(32, characters.get(2).getAge()); // Alice
    }
    
    @Test
    void testByPopularityComparator() {
        // create test data
        CharacterRecord char1 = new CharacterRecord(1, "Alice", 1, 32, 80.0, "Aries", "Actor", "1990-01-01", "USA", "/profile1");
        CharacterRecord char2 = new CharacterRecord(2, "Bob", 2, 31, 75.0, "Taurus", "Actor", "1991-02-02", "UK", "/profile2");
        CharacterRecord char3 = new CharacterRecord(3, "Charlie", 2, 30, 70.0, "Gemini", "Actor", "1992-03-03", "Canada", "/profile3");
        
        // create a list and sort it
        List<CharacterRecord> characters = new ArrayList<>();
        characters.add(char2);
        characters.add(char3);
        characters.add(char1);
        
        characters.sort(SortComparators.BY_POPULARITY);
        
        // verify the sorting result
        assertEquals(80.0, characters.get(0).getPopularity());
        assertEquals(75.0, characters.get(1).getPopularity());
        assertEquals(70.0, characters.get(2).getPopularity());
    }
} 
>>>>>>> 170291b1d8255c13c12ce1d0d7b3e58b7dfe5232
