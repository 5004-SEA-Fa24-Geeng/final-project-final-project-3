package model;

public class Character {
    private String name;
    private int age;
    private String sign;
    private String gender;

    public Character(String name, int age, String sign, String gender) {
        this.name = name;
        this.age = age;
        this.sign = sign;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSign() {
        return sign;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return name + " (" + age + ", " + sign + ", " + gender + ")";
    }
} 