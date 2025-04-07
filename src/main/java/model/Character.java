package model;

/**
 * character of celebrity
 */
public class Character {
    private String name;
    private int age;
    private String sign;
    private String gender;

    /**
     * constructor
     * @param name
     * @param age
     * @param sign
     * @param gender
     */
    public Character(String name, int age, String sign, String gender) {
        this.name = name;
        this.age = age;
        this.sign = sign;
        this.gender = gender;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get age
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * get sign
     * @return sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * get gender
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * toString
     * @return string
     */
    @Override
    public String toString() {
        return name + " (" + age + ", " + sign + ", " + gender + ")";
    }
} 