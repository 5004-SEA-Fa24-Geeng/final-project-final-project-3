package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterRecord {
    private Integer id;
    private String name;
    private Integer gender;
    private Integer age;
    private String zodiacSign;
    private double popularity;
    @JsonProperty("known_for_department")
    private String occupation;
    private String birthday;
    @JsonProperty("place_of_birth")
    private String nationality;
    @JsonProperty("profile_path")
    private String profile;

    public CharacterRecord() {
    }

    public CharacterRecord(Integer id, String name, Integer gender, Integer age, double popularity, String zodiacSign, String occupation, String birthday, String nationality, String profile) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getZodiacSign() {
        switch (zodiacSign) {
            case "Aries":
                return "♈ " + "Aries";
            case "Taurus":
                return "♉ " + "Taurus";
            case "Gemini":
                return "♊ " + "Gemini";
            case "Cancer":
                return "♋ " + "Cancer";
            case "Leo":
                return "♌ " + "Leo";
            case "Virgo":
                return "♍ " + "Virgo";
            case "Libra":
                return "♎ " + "Libra";
            case "Scorpio":
                return "♏ " + "Scorpio";
            case "Sagittarius":
                return "♐ " + "Sagittarius";
            case "Capricorn":
                return "♑ " + "Capricorn";
            case "Aquarius":
                return "♒ " + "Aquarius";
            case "Pisces":
                return "♓ " + "Pisces";
            default:
                return zodiacSign;
        }
    }

    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public double getPopularity() {
        return popularity;
    }


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
