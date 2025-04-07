package util;

/**
 * age utils
 */
public class AgeUtils {
    /**
     * calculate age
     * this method is used to calculate the age of a celebrity based on their birthday
     * @param birthday
     * @return age
     */
    public static int calculateAge(String birthday) {
        if (birthday == null) return 0;
        try {
            String[] parts = birthday.split("-");
            int birthYear = Integer.parseInt(parts[0]);
            int currentYear = java.time.Year.now().getValue();
            return currentYear - birthYear;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * parse age
     * @param ageText
     * @return age
     */
    public static int parseAge(String ageText) {
        try {
            return Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
} 