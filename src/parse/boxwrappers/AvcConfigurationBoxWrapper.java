package parse.boxwrappers;

import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import com.mp4parser.iso14496.part15.AvcDecoderConfigurationRecord;

public class AvcConfigurationBoxWrapper implements Wrapper {

	private AvcConfigurationBox avcC;
	
	public AvcConfigurationBoxWrapper(AvcConfigurationBox box) {
		this.avcC = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		AvcDecoderConfigurationRecord record = this.avcC.getavcDecoderConfigurationRecord();
		result.append(record.toString());
		return result.toString();
	}
}
