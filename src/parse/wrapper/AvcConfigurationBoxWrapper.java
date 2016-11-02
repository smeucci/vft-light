package parse.wrapper;

import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import com.mp4parser.iso14496.part15.AvcDecoderConfigurationRecord;

/**
 * <h1>Wrapper for the AvcConfigurationBox class of mp4parser</h1>
 * <p>AvcConfigurationBox implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class AvcConfigurationBoxWrapper implements Wrapper {

	private AvcConfigurationBox avcC;
	
	/**
	 * The constructor of the AvcConfigurationBoxWrapper class.
	 * @param box A box of type AvcConfigurationBox.
	 */
	public AvcConfigurationBoxWrapper(AvcConfigurationBox box) {
		this.avcC = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		AvcDecoderConfigurationRecord record = this.avcC.getavcDecoderConfigurationRecord();
		result.append(record.toString().replace("}", ""));
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
}
