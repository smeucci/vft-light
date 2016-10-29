package parse.wrapper;

import com.mp4parser.iso14496.part12.BitRateBox;

/**
 * <h1>Wrapper for the BitRateBox class of mp4parser</h1>
 * <p>BitRateBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class BitRateBoxWrapper {

	private BitRateBox btrt;
	
	/**
	 * The constructor of the BitRateBoxWrapper class.
	 * @param box A box of type BitRateBox.
	 */
	public BitRateBoxWrapper(BitRateBox box) {
		this.btrt = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("bufferSizeDb=").append(this.btrt.getBufferSizeDb());
        result.append(";");
        result.append("maxBitRate=").append(this.btrt.getMaxBitrate());
        result.append(";");
        result.append("avgBitRate=").append(this.btrt.getAvgBitrate());
        result.append("}");
		return result.toString();
	}
}