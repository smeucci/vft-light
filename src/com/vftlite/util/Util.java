package com.vftlite.util;

import java.util.Arrays;

import com.vftlite.tree.Tree;

/**
* <h1>Some utility functions.</h1>
* <p>Util is a static class that implements some
* utility methods. 
* </p>
*
* @author  Saverio Meucci
*/
public class Util {

	/**
	 * This method is used to remove brackects from a string.
	 * @param str	The string to modify.
	 * @param bracket The bracket to remove.
	 * @return String It returns the string without brackets.
	 */
	public static String removeBrackets(String str, String bracket) {
		//remove square brackets from the input string
		return str.replaceAll(bracket, "");
	}
	
	/**
	 * This method is used to check if a string contains a substring.
	 * @param text The string to check.
	 * @param token The substring.
	 * @return boolean True if text contains token; false otherwise.
	 */
	public static boolean contains(String text, String token) {
		text = text == null ? "" : text;
		token = token == null ? "" : token;
		return text.toLowerCase().contains(token.toLowerCase());
	}
	
	/**
	 * This method is used to sanitize a string, by removing
	 * some special characters.
	 * @param str The string to sanitize.
	 * @return String It returns the sanitized string.
	 */
	public static String sanitize(String str) {
		str = str.replaceAll("|©|", "");
		return str.trim();
	}
	
	/**
	 * This method is used to extend an array of String.
	 * @param arr The original array of String.
	 * @param elements The new array of String to add to the original.
	 * @return String[] It returns the united array.
	 */
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
	
	/**
	 * This method is used to count the occurrence of a substring
	 * into a string.
	 * @param text The string to check.
	 * @param token The substring to count.
	 * @return int It returns the number of occurrence of token in text.
	 */
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
	
	/**
	 * 
	 * @param field
	 * @return
	 */
	public static Boolean unusedField(String field) {
		if (field.matches("stuff|creationTime|modificationTime|size|duration|entryCount|sampleCount")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param tree
	 * @return
	 */
	public static Boolean unusedAtom(Tree tree) {
		if (tree.getName().contains("xyz")) return true;
		if (tree.getName().contains("unkn-5")) return true;
		if (tree.getName().contains("udta") && tree.getNumChildren() == 1 && tree.getChildren().get(0).getName().contains("xyz")) return true;
		return false;
	}
	
}