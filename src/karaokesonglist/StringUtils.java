package karaokesonglist;

/**
 *
 * @author rodcastro
 */
public class StringUtils {

    public static String formatName(String name) {
        try {
            String formattedName = name.replace("_", " ").trim().toLowerCase();
            String finalName = "";
            for (String part : formattedName.split(" ")) {
                if (part.length() > 0) {
                    finalName += part.substring(0, 1).toUpperCase() + part.substring(1, part.length()) + " ";
                }
            }
            return finalName.trim();
        } catch (Exception ex) {
            System.out.println("Exception trying to format the string \"" + name + "\": " + ex.getMessage());
            ex.printStackTrace();
            return name;
        }
    }

    /**
     * Joins an array of Strings into a single String, with the delimiter specified.
     * 
     * @param array The starting array of strings
     * @param delimiter The delimiter character or string
     * @param startIndex The first element of the array to be joined
     * @param endIndex The last element of the array to be joined
     * @return A string containing all elements specified joined with the specified delimiter
     */
    public static String join(String[] array, String delimiter, int startIndex, int endIndex) {
        String finalString = "";
        for (int i = startIndex; i < endIndex; i++) {
            finalString += array[i] + delimiter;
        }
        if (finalString.length() > 0) {
            return finalString.trim().substring(0, finalString.length() - 1);
        }
        return "";
    }

    public static String join(String[] array) {
        return join(array, " ", 0, array.length);
    }

    public static String addTrailingWhitespace(String string, int expectedLength) {
        int difference = expectedLength - string.length();
        String result = string;
        for (int i = 0; i < difference; i++) {
            result += " ";
        }
        return result;
    }

    public static int generateHash(String source) {
        int hash = 1;
        if (source != null) {
            for (char c : source.toCharArray()) {
                hash += c;
            }
        }
        return hash;
    }
}
