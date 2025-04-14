package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Used to read characters' information from a JSON file and write the characters in the wish list to a JSON file.
 */
public class JSONFileHandler {

    /**
     * Save the characters in the wish list to a Json file.
     *
     * @param fileName the name of the file saved the wish list to
     * @return if the wish list is successfully saved to the file
     */
    public static void saveWishListToFile(String fileName, Set<CharacterRecord> wishList) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode jsonArray = mapper.createArrayNode();
        for (CharacterRecord character : wishList) {
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put("id", character.getId());
            jsonObject.put("name", character.getName());
            jsonObject.put("age", character.getAge());
            jsonObject.put("gender", character.getGender());
            jsonObject.put("zodiacSign", character.getZodiacSign());
            jsonObject.put("birthday", character.getBirthday());
            jsonObject.put("nationality", character.getNationality());
            jsonObject.put("profile", character.getProfile());
            jsonObject.put("birthday", character.getBirthday());
            jsonObject.put("nationality", character.getNationality());
            jsonArray.add(jsonObject);
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), jsonArray);
        } catch (IOException e) {
            throw new IOException("Failed to write to file: " + e.getMessage());
        }
    }

    /**
     * Read characters' information from the Json file.
     *
     * @param fileName the name of the file to be read.
     * @return a list of characters
     */
    public static List<CharacterRecord> readJsonFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<CharacterRecord> characters = objectMapper.readValue(new File(fileName), new TypeReference<List<CharacterRecord>>() {
            });
            return processCharacters(characters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Process characters' information.
     *
     * @param characters the list of characters to be processed.
     * @return a list of characters
     */
    private static List<CharacterRecord> processCharacters(List<CharacterRecord> characters) {
        AtomicInteger idCounter = new AtomicInteger(1);
        return characters.stream().filter(character -> character.getBirthday() != null && character.getNationality() != null)
                .map(character -> {
                    try {
                        String[] dateOfBirth = character.getBirthday().split("-");
                        int year = Integer.parseInt(dateOfBirth[0]);
                        int month = Integer.parseInt(dateOfBirth[1]);
                        int day = Integer.parseInt(dateOfBirth[2]);

                        String[] nationArr = character.getNationality().split(",");
                        String nationality = nationArr[nationArr.length - 1].trim();

                        character.setId(idCounter.getAndIncrement());
                        character.setNationality(nationality);
                        character.setAge(calculateAge(year, month, day));
                        character.setZodiacSign(calculateZodiacSign(month, day));
                        return character;

                    } catch (Exception e) {
                        System.err.println("Invalid character skipped: " + character.getName());
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Calculate the age of a character based on the birthday.
     *
     * @param year  the year the character born in
     * @param month the month the character born in
     * @param day   the day the character born on
     * @return the age of the character
     */
    private static int calculateAge(int year, int month, int day) {
        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    /**
     * Calculate the zodiac sign of a character based on the birthday.
     *
     * @param month the month the character born in
     * @param day   the day the character born on
     * @return the zodiac sign of the character
     */
    private static String calculateZodiacSign(int month, int day) {

        String[] zodiacSigns = {
                "Aquarius", "Pisces", "Aries", "Taurus", "Gemini",
                "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
        };
        int[] zodiacStartDays = {20, 19, 21, 20, 21, 21, 23, 23, 23, 23, 22, 22}; // Start dates for each zodiac sign

        return (day < zodiacStartDays[month - 1]) ? zodiacSigns[(month + 10) % 12] : zodiacSigns[month - 1];
    }
}
