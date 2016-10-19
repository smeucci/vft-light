package parse.wrapper;

import com.coremedia.iso.boxes.fragment.TrackExtendsBox;

public class TrackExtendsBoxWrapper {

	private TrackExtendsBox trex;
	
	public TrackExtendsBoxWrapper(TrackExtendsBox box) {
		this.trex = box;
	}
	
	public String toString() {
		//TODO check toString method
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("trackId=").append(this.trex.getTrackId());
		result.append(";");
		result.append("defaultSampleDescriptionIndex=").append(this.trex.getDefaultSampleDescriptionIndex());
		result.append(";");
		result.append("defaultSampleDuration=").append(this.trex.getDefaultSampleDuration());
		result.append(";");
		result.append("defaultSampleSize=").append(this.trex.getDefaultSampleSize());
		result.append(";");
		result.append("defaultSampleFlags=").append(this.trex.getDefaultSampleFlagsStr());
		result.append(";");
		result.append("version=").append(this.trex.getVersion());
		result.append(";");
		result.append("flags=").append(this.trex.getFlags());
		result.append("}");
		return result.toString();
	}
	
}
