package model;

import org.junit.jupiter.api.Test;

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