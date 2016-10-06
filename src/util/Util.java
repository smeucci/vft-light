package util;

public class Util {

	public static String removeSquareBrackets(String str) {
		return str.replaceAll("]", "");
	}
	
	public static int countOccurrences(String text, String token) {
        int n = 0;
        for (int i = 0; i <= text.length() - token.length(); i++) {
            String str = text.substring(i, i + token.length());
            if (str.equals(token)) {
                n++;
            }
        }
        return n;
    }
	
}
