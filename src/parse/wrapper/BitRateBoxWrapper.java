package parse.wrapper;

import com.mp4parser.iso14496.part12.BitRateBox;

public class BitRateBoxWrapper {

	private BitRateBox btrt;
	
	public BitRateBoxWrapper(BitRateBox box) {
		this.btrt = box;
	}
	
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