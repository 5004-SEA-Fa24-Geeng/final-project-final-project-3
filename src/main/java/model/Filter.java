package model;

import java.util.List;

@FunctionalInterface
public interface Filter {
    boolean matches(CharacterRecord character);

    default Filter and(Filter other) {
        return c -> this.matches(c) && other.matches(c);
    }

    default Filter or(Filter other) {
        return c -> this.matches(c) || other.matches(c);
    }

    static Filter nameContains(String keyword) {
        String lowered = keyword.toLowerCase();
        return c -> c.getName().toLowerCase().contains(lowered);
    }

    static Filter genderIs(Integer gender) {
        return c -> c.getGender().equals(gender);
    }

    static Filter genderIn(List<Integer> genders) {
        return c -> genders.contains(c.getGender());
    }

    static Filter zodiacIs(String sign) {
        return c -> c.getZodiacSign().equalsIgnoreCase(sign);
    }

    static Filter zodiacIn(List<String> signs) {
        return c -> signs.contains(c.getZodiacSign());
    }

    static Filter ageBetween(int min, int max) {
        return c -> c.getAge() >= min && c.getAge() <= max;
    }
}
