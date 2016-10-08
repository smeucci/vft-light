package util;

import java.util.Arrays;

public class Util {

	public static String removeBrackets(String str, String bracket) {
		//remove square brackets from the input string
		return str.replaceAll(bracket, "");
	}
	
	public static boolean contains(String text, String token) {
		//check if text contains token
		text = text == null ? "" : text;
		token = token == null ? "" : token;
		return text.toLowerCase().contains(token.toLowerCase());
	}
	
	public static String[] append(String[] arr, String[] elements) {
		if (arr != null) {
			final int N = arr.length;
			final int E = elements.length;
			arr = Arrays.copyOf(arr, N + E);
			for (int i = 0; i < E; i++) {
				arr[N + i] = elements[i];
			}
			return arr;
		} else {
			String[] new_arr = {""};
			final int N = new_arr.length;
			final int E = elements.length;
			new_arr = Arrays.copyOf(new_arr, E);
			for (int i = 0; i < E; i++) {
				new_arr[N - 1 + i] = elements[i];
			}
			return new_arr;
		}
	}
	
	public static int countOccurrences(String text, String token) {
		//return how many times the token is in text
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
