package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import org.mockito.MockedConstruction;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishListTest {
    private WishList wishList;
    private CharactersCollection mockCollection;
    private static final CharacterRecord SAMPLE_CHARACTER =
            new CharacterRecord(1, "Test", 1, 30, 80.0, "Aries",
                    "Acting", "1990-01-01", "US", "/a.jpg");

    @BeforeEach
    void setUp() throws Exception {
        resetStaticWishList();
        initMockDependencies();
    }

    private void resetStaticWishList() throws Exception {
        Field field = WishList.class.getDeclaredField("wishList");
        field.setAccessible(true);
        ((Set<?>) field.get(null)).clear();
    }

    private void initMockDependencies() throws Exception {
        mockCollection = mock(CharactersCollection.class);
        wishList = new WishList();
        Field collectionField = WishList.class.getDeclaredField("charactersCollection");
        collectionField.setAccessible(true);
        collectionField.set(wishList, mockCollection);
    }

    @Test
    void addCharacter_ShouldAddValidCharacter() {
        when(mockCollection.getFilteredCharacters())
                .thenReturn(List.of(SAMPLE_CHARACTER));

        assertTrue(wishList.addCharacter(1));
        assertEquals(1, wishList.getWishList().size());
        assertTrue(wishList.getWishList().contains(SAMPLE_CHARACTER));
    }

    @Test
    void addCharacter_ShouldFailForDuplicate() {
        when(mockCollection.getFilteredCharacters())
                .thenReturn(List.of(SAMPLE_CHARACTER));

        wishList.addCharacter(1);
        assertFalse(wishList.addCharacter(1));
        assertEquals(1, wishList.getWishList().size());
    }


    @Test
    void removeSingleCharacter_ShouldRemoveExisting() {
        wishList.getWishList().add(SAMPLE_CHARACTER);

        assertTrue(wishList.removeSingleCharacter(1));
        assertTrue(wishList.getWishList().isEmpty());
    }

    @Test
    void removeSingleCharacter_ShouldHandleMissingId() {
        wishList.getWishList().add(SAMPLE_CHARACTER);

        assertFalse(wishList.removeSingleCharacter(2));
        assertEquals(1, wishList.getWishList().size());
    }

    @Test
    void removeAllCharacters_ShouldClearList() {
        wishList.getWishList().add(SAMPLE_CHARACTER);

        assertTrue(wishList.removeAllCharacters());
        assertTrue(wishList.getWishList().isEmpty());
    }

    @Test
    void removeAllCharacters_ShouldHandleEmptyList() {
        assertTrue(wishList.removeAllCharacters());
    }

    @Test
    void getCharacterById_ShouldFindExisting() {
        wishList.getWishList().add(SAMPLE_CHARACTER);

        assertEquals(SAMPLE_CHARACTER, wishList.getCharacterById(1));
    }

    @Test
    void getCharacterById_ShouldReturnNullForMissing() {
=======
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class WishListTest {
    private WishList wishList;
    private CharacterRecord testCharacter1;
    private CharacterRecord testCharacter2;

    @BeforeEach
    void setUp() {
        wishList = new WishList();
        wishList.removeAllCharacters();  // clear the wish list
        
        testCharacter1 = new CharacterRecord();
        testCharacter1.setId(1);
        testCharacter1.setName("Test Character 1");
        testCharacter1.setAge(30);
        testCharacter1.setGender(1);
        testCharacter1.setZodiacSign("Aries");
        testCharacter1.setProfile("/test1.jpg");
        testCharacter1.setBirthday("1990-01-01");
        testCharacter1.setNationality("USA");
        
        testCharacter2 = new CharacterRecord();
        testCharacter2.setId(2);
        testCharacter2.setName("Test Character 2");
        testCharacter2.setAge(25);
        testCharacter2.setGender(2);
        testCharacter2.setZodiacSign("Taurus");
        testCharacter2.setProfile("/test2.jpg");
        testCharacter2.setBirthday("1995-02-02");
        testCharacter2.setNationality("UK");
    }

    @Test
    void testAddCharacter() {
        // test adding a new character
        assertTrue(wishList.addCharacter(1));
        
        // test adding an existing character
        assertFalse(wishList.addCharacter(1));
        
        // test adding a non-existent character
        assertTrue(wishList.addCharacter(999));
    }

    @Test
    void testRemoveSingleCharacter() {
        // test removing an existing character
        wishList.addCharacter(1);
        
        // test removing an existing character
        assertTrue(wishList.removeSingleCharacter(1));
        
        // test removing a non-existent character
        assertFalse(wishList.removeSingleCharacter(1));
        assertFalse(wishList.removeSingleCharacter(999));
    }

    @Test
    void testRemoveAllCharacters() {
        // test removing all characters
        wishList.addCharacter(1);
        wishList.addCharacter(2);
        
        // test removing all characters
        assertTrue(wishList.removeAllCharacters());
        
        // verify that the wish list is empty
        Set<CharacterRecord> characters = wishList.getWishList();
        assertTrue(characters.isEmpty());
    }

    @Test
    void testGetCharacterById() {
        // test getting an existing character
        wishList.addCharacter(1);
        
        // test getting an existing character
        CharacterRecord character = wishList.getCharacterById(1);
        assertNotNull(character);
        assertEquals(1, character.getId());
        
        // test getting a non-existent character
>>>>>>> 170291b1d8255c13c12ce1d0d7b3e58b7dfe5232
        assertNull(wishList.getCharacterById(999));
    }

    @Test
<<<<<<< HEAD
    void wishListPersistence_AcrossInstances() throws Exception {
        try (MockedConstruction<CharactersCollection> ignored = mockConstruction(CharactersCollection.class)) {
            WishList firstInstance = new WishList();
            firstInstance.getWishList().add(SAMPLE_CHARACTER);

            WishList secondInstance = new WishList();
            assertEquals(1, secondInstance.getWishList().size());
        }
    }
}
=======
    void testGetWishList() {
        // test adding multiple characters
        wishList.addCharacter(1);
        wishList.addCharacter(2);
        wishList.addCharacter(999);
        
        // test getting the wish list
        Set<CharacterRecord> characters = wishList.getWishList();
        
        // verify that the wish list is not empty
        assertNotNull(characters);
        assertEquals(3, characters.size());
    }
} 
>>>>>>> 170291b1d8255c13c12ce1d0d7b3e58b7dfe5232
