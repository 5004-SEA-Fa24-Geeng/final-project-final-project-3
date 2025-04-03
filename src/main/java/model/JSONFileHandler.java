package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Used to read characters' information from a JSON file and write the characters in the wish list to a JSON file.
 */
public class JSONFileHandler {

    /**
     * Save the characters in the wish list to a Json file.
     * @param fileName the name of the file saved the wish list to
     * @return if the wish list is successfully saved to the file
     */
    public static boolean writeWishListToFile(String fileName) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), WishList.wishList);
            return true;
        } catch (IOException e) {
            throw new IOException("Failed to write the wish list to the file!", e);
        }
    }

    /**
     * Read characters' information from the Json file.
     * @param fileName the name of the file to be read.
     * @return a list of characters
     */
    public static List<CharacterRecord> readJsonFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<CharacterRecord> characters = objectMapper.readValue(new File(fileName), new TypeReference<>() {});
            return processCharacters(characters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Process characters' information.
     * @param characters the list of characters to be processed.
     * @return a list of characters
     */
    private static List<CharacterRecord> processCharacters(List<CharacterRecord> characters) {
        AtomicInteger idCounter = new AtomicInteger(1);
        return characters.stream().filter(character -> character.getBirthday() != null && character.getNationality() != null)
                .map(character -> {
                    String[] dateOfBirth = character.getBirthday().split("-");
                    int year = 0;
                    int month = 0;
                    int day = 0;
                    try {
                        year = Integer.parseInt(dateOfBirth[0]);
                        month = Integer.parseInt(dateOfBirth[1]);
                        day = Integer.parseInt(dateOfBirth[2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    String[] nationArr = character.getNationality().split(",");
                    String nationality = nationArr[nationArr.length - 1].trim();
                    character.setId(idCounter.getAndIncrement());
                    character.setNationality(nationality);
                    character.setAge(calculateAge(year, month, day));
                    character.setZodiacSign((calculateZodiacSign(month, day)));
                    return character;
                }).collect(Collectors.toList());
    }

    /**
     * Calculate the age of a character based on the birthday.
     * @param year the year the character born in
     * @param month the month the character born in
     * @param day the day the character born on
     * @return the age of the character
     */
    private static int calculateAge(int year, int month, int day) {
        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    /**
     * Calculate the zodiac sign of a character based on the birthday.
     * @param month the month the character born in
     * @param day the day the character born on
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
