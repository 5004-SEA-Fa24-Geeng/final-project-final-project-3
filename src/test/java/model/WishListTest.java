package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        initMockDependencies();
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


    void wishListPersistence_AcrossInstances() throws Exception {
        try (MockedConstruction<CharactersCollection> ignored = mockConstruction(CharactersCollection.class)) {
            WishList firstInstance = new WishList();
            firstInstance.getWishList().add(SAMPLE_CHARACTER);

            WishList secondInstance = new WishList();
            assertEquals(1, secondInstance.getWishList().size());
        }
    }
}

