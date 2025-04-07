package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * json celebrity   
 * this class is used to represent a celebrity in the json file
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonCelebrity {
    private String name;
    private String birthday;
    private String gender;
    private String known_for_department;
    private double popularity;
    private String profile_path;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getKnown_for_department() { return known_for_department; }
    public void setKnown_for_department(String known_for_department) { this.known_for_department = known_for_department; }
    public double getPopularity() { return popularity; }
    public void setPopularity(double popularity) { this.popularity = popularity; }
    public String getProfile_path() { return profile_path; }
    public void setProfile_path(String profile_path) { this.profile_path = profile_path; }
} 