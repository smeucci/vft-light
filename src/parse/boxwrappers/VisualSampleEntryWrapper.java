package parse.boxwrappers;

import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;

public class VisualSampleEntryWrapper implements Wrapper {

	private VisualSampleEntry avc1;
	
	public VisualSampleEntryWrapper(VisualSampleEntry box) {
		this.avc1 = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
        result.append("[");
        result.append("data_reference_index=").append(this.avc1.getDataReferenceIndex());
        result.append(";");
        result.append("width=").append(this.avc1.getWidth());
        result.append(";");
        result.append("height=").append(this.avc1.getHeight());
        result.append(";");
        result.append("horizresolution=").append(this.avc1.getHorizresolution());
        result.append(";");
        result.append("vertresolution=").append(this.avc1.getVertresolution());
        result.append(";");
        result.append("frame_count=").append(this.avc1.getFrameCount());
        result.append(";");
        result.append("compressorname=").append(this.avc1.getCompressorname());
        result.append(";");
        result.append("depth=").append(this.avc1.getDepth());
        result.append("]");
        return result.toString();
	}
}
