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
    @JsonProperty("known_for_department")
    private String occupation;
    private String birthday;
    @JsonProperty("place_of_birth")
    private String nationality;
    @JsonProperty("profile_path")
    private String profile;

    public CharacterRecord() {
    }

    public CharacterRecord(Integer id, String name, Integer gender, Integer age, String zodiacSign, String occupation, String birthday, String nationality, String profile) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.zodiacSign = zodiacSign;
        this.occupation = occupation;
        this.birthday = birthday;
        this.nationality = nationality;
        this.profile = profile;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getZodiacSign() {
        return zodiacSign;
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
