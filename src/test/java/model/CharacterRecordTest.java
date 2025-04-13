package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharacterRecordTest {
    private CharacterRecord fullParamsCharacter;
    private CharacterRecord defaultCharacter;

    @BeforeEach
    void setUp() {
        // Full parameter constructor
        fullParamsCharacter = new CharacterRecord(
                1,
                "John Doe",
                1,
                30,
                85.5,
                "Aries",
                "Acting",
                "1990-04-01",
                "New York",
                "/profile.jpg"
        );

        // Default constructor + setters
        defaultCharacter = new CharacterRecord();
        defaultCharacter.setId(2);
        defaultCharacter.setName("Jane Smith");
        defaultCharacter.setGender(2);
        defaultCharacter.setAge(25);
        defaultCharacter.setPopularity(92.3);
        defaultCharacter.setZodiacSign("Pisces");
        defaultCharacter.setOccupation("Directing");
        defaultCharacter.setBirthday("1995-03-15");
        defaultCharacter.setNationality("London");
        defaultCharacter.setProfile("/avatar.png");
    }

    // Constructor validation tests
    @Test
    void fullParameterConstructor_ShouldSetAllFields() {
        assertEquals(1, fullParamsCharacter.getId());
        assertEquals("John Doe", fullParamsCharacter.getName());
        assertEquals(1, fullParamsCharacter.getGender());
        assertEquals(30, fullParamsCharacter.getAge());
        assertEquals(85.5, fullParamsCharacter.getPopularity(), 0.001);
        assertEquals("Aries ♈", fullParamsCharacter.getZodiacSign());
        assertEquals("Acting", fullParamsCharacter.getOccupation());
        assertEquals("1990-04-01", fullParamsCharacter.getBirthday());
        assertEquals("New York", fullParamsCharacter.getNationality());
        assertEquals("/profile.jpg", fullParamsCharacter.getProfile());
    }

    // Getter/Setter validation tests
    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        assertEquals(2, defaultCharacter.getId());
        assertEquals("Jane Smith", defaultCharacter.getName());
        assertEquals(2, defaultCharacter.getGender());
        assertEquals(25, defaultCharacter.getAge());
        assertEquals(92.3, defaultCharacter.getPopularity(), 0.001);
        assertEquals("Pisces ♓", defaultCharacter.getZodiacSign());
        assertEquals("Directing", defaultCharacter.getOccupation());
        assertEquals("1995-03-15", defaultCharacter.getBirthday());
        assertEquals("London", defaultCharacter.getNationality());
        assertEquals("/avatar.png", defaultCharacter.getProfile());
    }

    // Gender string conversion tests
    @Test
    void getGenderString_ShouldReturnFemale_WhenGenderCode1() {
        fullParamsCharacter.setGender(1);
        assertEquals("Gender: Female", fullParamsCharacter.getGenderString());
    }

    @Test
    void getGenderString_ShouldReturnMale_WhenGenderCode2() {
        fullParamsCharacter.setGender(2);
        assertEquals("Gender: Male", fullParamsCharacter.getGenderString());
    }

    @Test
    void getGenderString_ShouldReturnNonBinary_WhenOtherCodes() {
        fullParamsCharacter.setGender(3);
        assertEquals("Gender: Non-binary", fullParamsCharacter.getGenderString());

        fullParamsCharacter.setGender(0);
        assertEquals("Gender: Non-binary", fullParamsCharacter.getGenderString());

        fullParamsCharacter.setGender(-1);
        assertEquals("Gender: Non-binary", fullParamsCharacter.getGenderString());
    }

    @Test
    void getGenderString_ShouldThrowNPE_WhenGenderNull() {
        fullParamsCharacter.setGender(null);
        assertThrows(NullPointerException.class, () -> fullParamsCharacter.getGenderString());
    }

    // Zodiac sign formatting tests
    @Test
    void getZodiacSign_ShouldFormatWithSymbol() {
        String[] signs = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
                "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
        String[] expected = {"♈", "♉", "♊", "♋", "♌", "♍",
                "♎", "♏", "♐", "♑", "♒", "♓"};

        for (int i = 0; i < signs.length; i++) {
            fullParamsCharacter.setZodiacSign(signs[i]);
            assertTrue(fullParamsCharacter.getZodiacSign().contains(expected[i]));
        }
    }

    @Test
    void getZodiacSign_ShouldReturnOriginal_WhenUnknownSign() {
        fullParamsCharacter.setZodiacSign("Unknown");
        assertEquals("Unknown", fullParamsCharacter.getZodiacSign());
    }

    @Test
    void getZodiacSign_ShouldThrowNPE_WhenSignNull() {
        fullParamsCharacter.setZodiacSign(null);
        assertThrows(NullPointerException.class, () -> fullParamsCharacter.getZodiacSign());
    }

    // Boundary value tests
    @Test
    void ageBoundaryValues_ShouldBeHandled() {
        fullParamsCharacter.setAge(0);
        assertEquals(0, fullParamsCharacter.getAge());

        fullParamsCharacter.setAge(-1);
        assertEquals(-1, fullParamsCharacter.getAge());

        fullParamsCharacter.setAge(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, fullParamsCharacter.getAge());
    }

    @Test
    void popularityBoundaryValues_ShouldBeHandled() {
        fullParamsCharacter.setPopularity(0.0);
        assertEquals(0.0, fullParamsCharacter.getPopularity(), 0.001);

        fullParamsCharacter.setPopularity(-100.5);
        assertEquals(-100.5, fullParamsCharacter.getPopularity(), 0.001);

        fullParamsCharacter.setPopularity(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, fullParamsCharacter.getPopularity(), 0.001);
    }

    // ToString validation
    @Test
    void toString_ShouldContainAllFields() {
        String result = fullParamsCharacter.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("name='John Doe'"));
        assertTrue(result.contains("gender=1"));
        assertTrue(result.contains("age=30"));
        assertTrue(result.contains("zodiacSign='Aries'"));
        assertTrue(result.contains("occupation='Acting'"));
        assertTrue(result.contains("birthday='1990-04-01'"));
        assertTrue(result.contains("nationality='New York'"));
        assertTrue(result.contains("profile='/profile.jpg'"));
    }
}