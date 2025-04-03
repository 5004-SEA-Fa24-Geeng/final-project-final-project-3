package model;

public interface Filter {
    boolean matches(CharacterRecord character);

    default Filter and(Filter other) {
        return new Filter() {
            @Override
            public boolean matches(CharacterRecord c) {
                return Filter.this.matches(c) && other.matches(c);
            }
        };
    }

    default Filter or(Filter other) {
        return new Filter() {
            @Override
            public boolean matches(CharacterRecord c) {
                return Filter.this.matches(c) || other.matches(c);
            }
        };
    }

    static Filter nameContains(String keyword) {
        return new Filter() {
            @Override
            public boolean matches(CharacterRecord c) {
                return c.getName().toLowerCase().contains(keyword);
            }
        };
    }

    static Filter genderIs(Integer gender) {
        return new Filter() {
            @Override
            public boolean matches(CharacterRecord c) {
                return c.getGender().equals(gender);
            }
        };
    }

    static Filter astrologyIs(String sign) {
        return new Filter() {
            public boolean matches(CharacterRecord c) {
                return c.getZodiacSign().equalsIgnoreCase(sign);
            }
        };
    }
}
