package com.vftlite.parse.wrapper;

import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;

/**
 * <h1>Wrapper for the ESDescriptorBox class of mp4parser</h1>
 * <p>ESDescriptorBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class ESDescriptorBoxWrapper implements Wrapper {

	private ESDescriptorBox esds;
	
	/**
	 * The constructor of the ESDescriptorBoxWrapper class.
	 * @param box A box of type ESDescriptorBox.
	 */
	public ESDescriptorBoxWrapper(ESDescriptorBox box) {
		this.esds = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		String result = "";
		String descr = this.esds.getDescriptorAsString().replaceAll("\\}|\\[\\[\\]\\]", "");
		String[] splits = descr.split("ESDescriptor\\{"
				+ "|decoderConfigDescriptor=DecoderConfigDescriptor\\{"
				+ "|audioSpecificInfo=AudioSpecificConfig\\{"
				+ "|slConfigDescriptor=SLConfigDescriptor\\{");
		for (int i = 1; i < splits.length; i++) {
			result = result + splits[i];
		}
		return "{" + result + ";count=0}";
	}
}
