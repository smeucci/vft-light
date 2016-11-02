package com.vftlite.parse.wrapper;

/**
 * <h1>Wrapper for the Box class of mp4parser</h1>
 * <p>Wrapper is an interface used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public interface Wrapper {

	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString();
	
}
