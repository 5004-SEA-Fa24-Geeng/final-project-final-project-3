package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a character with personal and professional attributes.
 * Supports JSON deserialization via Jackson annotations.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterRecord {

    /**
     * The unique ID of the character.
     */
    private Integer id;

    /**
     * The name of the character.
     */
    private String name;

    /**
     * The gender code (1 = female, 2 = male, other = non-binary).
     */
    private Integer gender;

    /**
     * The age of the character.
     */
    private Integer age;

    /**
     * The zodiac sign of the character.
     */
    private String zodiacSign;

    /**
     * The popularity score of the character.
     */
    private double popularity;

    /**
     * The occupation or department the character is known for.
     */
    @JsonProperty("known_for_department")
    private String occupation;

    /**
     * The character's birth date.
     */
    private String birthday;

    /**
     * The character's place of birth or nationality.
     */
    @JsonProperty("place_of_birth")
    private String nationality;

    /**
     * The relative path to the character's profile image.
     */
    @JsonProperty("profile_path")
    private String profile;

    /**
     * Default constructor required for JSON deserialization.
     */
    public CharacterRecord() {
    }

    /**
     * Full constructor for manually creating a character record.
     *
     * @param id          character ID
     * @param name        character name
     * @param gender      gender code (1 = female, 2 = male, other = non-binary)
     * @param age         character's age
     * @param popularity  popularity score
     * @param zodiacSign  zodiac sign name
     * @param occupation  known for department
     * @param birthday    birthdate
     * @param nationality place of birth
     * @param profile     profile image path
     */
    public CharacterRecord(Integer id, String name, Integer gender, Integer age, double popularity,
                           String zodiacSign, String occupation, String birthday, String nationality, String profile) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.zodiacSign = zodiacSign;
        this.occupation = occupation;
        this.birthday = birthday;
        this.nationality = nationality;
        this.profile = profile;
        this.popularity = popularity;
    }

    /**
     * @return the character ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the character ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the character's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the character name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gender code (1 = female, 2 = male, other = non-binary)
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender the gender code to set
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return a formatted gender string
     */
    public String getGenderString() {
        switch (gender) {
            case 1:
                return "Gender: Female";
            case 2:
                return "Gender: Male";
            default:
                return "Gender: Non-binary";
        }
    }

    /**
     * @return the character's age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the character's age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the zodiac sign with its Unicode symbol, if available
     */
    public String getZodiacSign() {
        switch (zodiacSign) {
            case "Aries":
                return "Aries ♈";
            case "Taurus":
                return "Taurus ♉";
            case "Gemini":
                return "Gemini ♊";
            case "Cancer":
                return "Cancer ♋";
            case "Leo":
                return "Leo ♌";
            case "Virgo":
                return "Virgo ♍";
            case "Libra":
                return "Libra ♎";
            case "Scorpio":
                return "Scorpio ♏";
            case "Sagittarius":
                return "Sagittarius ♐";
            case "Capricorn":
                return "Capricorn ♑";
            case "Aquarius":
                return "Aquarius ♒";
            case "Pisces":
                return "Pisces ♓";
            default:
                return zodiacSign;
        }
    }

    /**
     * @param zodiacSign the zodiac sign to set
     */
    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    /**
     * @return the occupation (known for department)
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return the place of birth
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the place of birth to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the birthday string
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the profile image path
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile the profile image path to set
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return the popularity score
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * @param popularity the popularity to set
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * @return a string representation of the character record
     */
    @Override
    public String toString() {
        return "CharacterRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", zodiacSign='" + zodiacSign + '\'' +
                ", occupation='" + occupation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nationality='" + nationality + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
