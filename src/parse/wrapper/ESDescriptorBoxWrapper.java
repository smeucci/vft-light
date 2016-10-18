package parse.wrapper;

import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;

public class ESDescriptorBoxWrapper implements Wrapper {

	private ESDescriptorBox esds;
	
	public ESDescriptorBoxWrapper(ESDescriptorBox box) {
		this.esds = box;
	}
	
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
		return "{" + result + "}";
	}
}
