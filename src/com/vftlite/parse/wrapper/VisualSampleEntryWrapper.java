package com.vftlite.parse.wrapper;

import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;

/**
 * <h1>Wrapper for the VisualSampleEntry class of mp4parser</h1>
 * <p>VisualSampleEntryWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class VisualSampleEntryWrapper implements Wrapper {

	private VisualSampleEntry avc1;
	
	/**
	 * The constructor of the VisualSampleEntryWrapper class.
	 * @param box A box of type VisualSampleEntry.
	 */
	public VisualSampleEntryWrapper(VisualSampleEntry box) {
		this.avc1 = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
        result.append("{");
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
        result.append(";");
		result.append("count=0");
        result.append("}");
        return result.toString();
	}
}
