package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        assertNull(wishList.getCharacterById(999));
    }

    @Test
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