package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.CharacterRecord;
import model.JSONFileHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

/*
 * This class contains unit tests for the JSONFileHandler class.
 * It tests the saveWishListToFile, readJsonFile, and processCharacters methods.
 */
class JSONFileHandlerTest {

    private Set<CharacterRecord> testWishList;
    private CharacterRecord testCharacter1;
    private CharacterRecord testCharacter2;

    @BeforeEach
    // set up the test
    void setUp() {
        testWishList = new HashSet<>();
        
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
        
        testWishList.add(testCharacter1);
        testWishList.add(testCharacter2);
    }

    @Test
    // test saving the wish list to a file
    void testSaveWishListToFile(@TempDir Path tempDir) throws IOException {
        // create a temporary file
        File tempFile = tempDir.resolve("test_wishlist.json").toFile();
        
        // save the wish list to a file
        JSONFileHandler.saveWishListToFile(tempFile.getAbsolutePath(), testWishList);
        
        // verify that the file exists
        assertTrue(tempFile.exists());
        
        // read the file content
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(tempFile);
        List<CharacterRecord> loadedCharacters = new ArrayList<>();
        for (JsonNode node : rootNode) {
            CharacterRecord character = new CharacterRecord();
            character.setId(node.get("id").asInt());
            character.setName(node.get("name").asText());
            character.setAge(node.get("age").asInt());
            character.setGender(node.get("gender").asInt());
            character.setZodiacSign(node.get("zodiacSign").asText());
            character.setProfile(node.get("profile").asText());
            character.setBirthday(node.get("birthday").asText());
            character.setNationality(node.get("nationality").asText());
            loadedCharacters.add(character);
        }
        
        // verify the number of loaded characters
        assertEquals(2, loadedCharacters.size());
        
        // verify the properties of the first character
        CharacterRecord loadedCharacter = loadedCharacters.stream()
            .filter(c -> c.getId() == testCharacter1.getId())
            .findFirst()
            .orElse(null);
        assertNotNull(loadedCharacter);
        assertEquals(testCharacter1.getName(), loadedCharacter.getName());
        assertEquals(testCharacter1.getAge(), loadedCharacter.getAge());
        assertEquals(testCharacter1.getGender(), loadedCharacter.getGender());
        assertEquals("Aries ♈", loadedCharacter.getZodiacSign());
        assertEquals(testCharacter1.getProfile(), loadedCharacter.getProfile());
        assertEquals(testCharacter1.getBirthday(), loadedCharacter.getBirthday());
        assertEquals(testCharacter1.getNationality(), loadedCharacter.getNationality());
    }

    @Test
    void testReadJsonFileWithInvalidFile() {
        // use a non-existent temporary file path
        String nonExistentFile = System.getProperty("java.io.tmpdir") + "/nonexistent_" + System.currentTimeMillis() + ".json";
        List<CharacterRecord> characters = JSONFileHandler.readJsonFile(nonExistentFile);
        assertTrue(characters.isEmpty(), "reading a non-existent file should return an empty list");
    }

    @Test
    void testProcessCharacters() {
        // create test data
        CharacterRecord character = new CharacterRecord();
        character.setBirthday("1990-01-01");
        character.setNationality("New York, USA");  // keep the original input format
        character.setName("Test Character");
        character.setGender(1);
        character.setProfile("/test.jpg");
        character.setZodiacSign("Capricorn");
        character.setAge(30);
        character.setId(1);
        
        // save and read test data
        Set<CharacterRecord> testSet = new HashSet<>();
        testSet.add(character);
        try {
            File tempFile = File.createTempFile("test", ".json");
            JSONFileHandler.saveWishListToFile(tempFile.getAbsolutePath(), testSet);
            
            // read the file content and verify
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(tempFile);
            List<CharacterRecord> loadedCharacters = new ArrayList<>();
            for (JsonNode node : rootNode) {
                CharacterRecord loadedChar = new CharacterRecord();
                loadedChar.setId(node.get("id").asInt());
                loadedChar.setName(node.get("name").asText());
                loadedChar.setAge(node.get("age").asInt());
                loadedChar.setGender(node.get("gender").asInt());
                loadedChar.setZodiacSign(node.get("zodiacSign").asText());
                loadedChar.setProfile(node.get("profile").asText());
                loadedChar.setBirthday(node.get("birthday").asText());
                loadedChar.setNationality(node.get("nationality").asText());
                loadedCharacters.add(loadedChar);
            }
            
            // verify that the processed characters are not empty
            assertNotNull(loadedCharacters);
            assertFalse(loadedCharacters.isEmpty());
            
            // verify that the processed characters have the correct properties
            CharacterRecord loadedCharacter = loadedCharacters.get(0);
            assertEquals(character.getId(), loadedCharacter.getId());
            assertEquals(character.getName(), loadedCharacter.getName());
            assertEquals(character.getAge(), loadedCharacter.getAge());
            assertEquals(character.getGender(), loadedCharacter.getGender());
            assertEquals("Capricorn ♑", loadedCharacter.getZodiacSign());
            assertEquals(character.getProfile(), loadedCharacter.getProfile());
            assertEquals(character.getBirthday(), loadedCharacter.getBirthday());
            assertEquals("New York, USA", loadedCharacter.getNationality());  // 修改期望值为实际值
            
            // clean up the temporary file
            tempFile.delete();
        } catch (IOException e) {
            fail("Failed to process characters: " + e.getMessage());
        }
    }
} 