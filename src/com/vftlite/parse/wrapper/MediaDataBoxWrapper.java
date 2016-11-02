package com.vftlite.parse.wrapper;

import com.coremedia.iso.boxes.mdat.MediaDataBox;

/**
 * <h1>Wrapper for the MediaDataBox class of mp4parser</h1>
 * <p>MediaDataBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class MediaDataBoxWrapper {

	private MediaDataBox mdat;
	
	/**
	 * The constructor of the MediaDataBoxWrapper class.
	 * @param box A box of type MediaDataBox.
	 */
	public MediaDataBoxWrapper(MediaDataBox box) {
		this.mdat = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(this.mdat.toString().replace("}", ""));
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
	
}
