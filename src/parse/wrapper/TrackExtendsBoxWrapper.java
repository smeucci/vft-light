package parse.wrapper;

import com.coremedia.iso.boxes.fragment.TrackExtendsBox;

/**
 * <h1>Wrapper for the TrackExtendsBox class of mp4parser</h1>
 * <p>TrackExtendsBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class TrackExtendsBoxWrapper {

	private TrackExtendsBox trex;
	
	/**
	 * The constructor of the TrackExtendsBoxWrapper class.
	 * @param box A box of type TrackExtendsBox.
	 */
	public TrackExtendsBoxWrapper(TrackExtendsBox box) {
		this.trex = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
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
		result.append(";");
		result.append("count=0");
		result.append("}");
		return result.toString();
	}
	
}
