package model;

/**
 * extended character: character with popularity
 */
public class ExtendedCharacter extends Character {
    private final double popularity;

    /**
     * constructor
     * @param name
     * @param age
     * @param sign
     * @param gender
     * @param popularity
     */
    public ExtendedCharacter(String name, int age, String sign, String gender, double popularity) {
        super(name, age, sign, gender);
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }
} 