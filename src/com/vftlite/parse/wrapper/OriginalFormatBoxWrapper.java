package com.vftlite.parse.wrapper;

import com.coremedia.iso.boxes.OriginalFormatBox;

/**
* <h1>Wrapper for the OriginalFormatBox class of mp4parser</h1>
* <p>OriginalFormatBoxWrapper implements the Wrapper interface 
* and it is used to extend the Box classes
* of the mp4parser with a formatted toString method which return
* the attributes of the Box as a String.</p>
* 
* @author Saverio Meucci
*
*/
public class OriginalFormatBoxWrapper {

	private OriginalFormatBox frma;
	
	/**
	 * The constructor of the OriginalFormatBoxWrapper class.
	 * @param box A box of type OriginalFormatBox.
	 */
	public OriginalFormatBoxWrapper(OriginalFormatBox box) {
		this.frma = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(this.frma.toString().replace("[", "{").replace("]", ""));
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
	
	
}
