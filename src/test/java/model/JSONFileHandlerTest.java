package model;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JSONFileHandlerTest {

    private static final String TEST_FILE_PATH = "test_wishlist.json";

    private Set<CharacterRecord> testWishList;

    @BeforeEach
    void setUp() {
        CharacterRecord character = new CharacterRecord();
        character.setId(1);
        character.setName("Test User");
        character.setAge(25);
        character.setGender(3);
        character.setZodiacSign("Gemini");
        character.setProfile("Test profile");
        character.setBirthday("2000-05-22");
        character.setNationality("Asia, China");

        testWishList = new HashSet<>();
        testWishList.add(character);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    void testSaveWishListToFileSuccess() {
        assertDoesNotThrow(() -> JSONFileHandler.saveWishListToFile(TEST_FILE_PATH, testWishList));

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void testSaveWishListToFileIOException() {
        assertThrows(IOException.class, () ->
                JSONFileHandler.saveWishListToFile("/invalid_path/test.json", testWishList)
        );
    }

    @Test
    void testReadJsonFileNotExist() {
        List<CharacterRecord> characters = JSONFileHandler.readJsonFile("non_existent.json");
        assertNotNull(characters);
        assertTrue(characters.isEmpty());
    }

    @Test
    void testProcessCharactersIgnoresInvalidBirthday() throws IOException {
        String badJson = "[{\"name\": \"Invalid\", \"birthday\": \"abcd-ef-gh\", \"nationality\": \"Europe, France\"}]";
        Files.write(Paths.get(TEST_FILE_PATH), badJson.getBytes());

        List<CharacterRecord> characters = JSONFileHandler.readJsonFile(TEST_FILE_PATH);
        assertEquals(0, characters.size());
    }

    @Test
    void testProcessCharactersMissingFields() throws IOException {
        // Character missing nationality
        String json = "[{\"name\": \"NoNation\", \"birthday\": \"1995-07-01\"}]";
        Files.write(Paths.get(TEST_FILE_PATH), json.getBytes());

        List<CharacterRecord> characters = JSONFileHandler.readJsonFile(TEST_FILE_PATH);
        assertEquals(0, characters.size()); // Should be filtered out
    }
}
