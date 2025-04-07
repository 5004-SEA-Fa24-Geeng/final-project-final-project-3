package util;

/**
 * gender utils
 */     
public class GenderUtils {
    /**
     * convert gender
     * this method is used to convert the gender code to the gender name
     * @param genderCode
     * @return gender
     */
    public static String convertGender(String genderCode) {
        if (genderCode == null) return "Unknown";
        try {
            int code = Integer.parseInt(genderCode);
            switch (code) {
                case 1: return "Female";
                case 2: return "Male";
                case 3: return "Non-binary";
                default: return "Unknown";
            }
        } catch (NumberFormatException e) {
            return "Unknown";
        }
    }
} 