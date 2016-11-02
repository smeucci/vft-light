package parse.wrapper;

import com.coremedia.iso.boxes.TrackHeaderBox;

/**
 * <h1>Wrapper for the TrackHeaderBox class of mp4parser</h1>
 * <p>TrackHeaderBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class TrackHeaderBoxWrapper {

	private TrackHeaderBox tkhd;
	
	/**
	 * The constructor of the TrackHeaderBoxWrapper class.
	 * @param box A box of type TrackHeaderBox.
	 */
	public TrackHeaderBoxWrapper(TrackHeaderBox box) {
		this.tkhd = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("creationTime=").append(this.tkhd.getCreationTime());
        result.append(";");
        result.append("modificationTime=").append(this.tkhd.getModificationTime());
        result.append(";");
        result.append("trackId=").append(this.tkhd.getTrackId());
        result.append(";");
        result.append("duration=").append(this.tkhd.getDuration());
        result.append(";");
        result.append("layer=").append(this.tkhd.getLayer());
        result.append(";");
        result.append("alternateGroup=").append(this.tkhd.getAlternateGroup());
        result.append(";");
        result.append("volume=").append(this.tkhd.getVolume());
        result.append(";");
        result.append("matrix=").append(this.tkhd.getMatrix().toString().replaceAll("Matrix\\{|\\}|\\,", "").replaceAll("\\=", "\\:"));
        result.append(";");
        result.append("width=").append(this.tkhd.getWidth());
        result.append(";");
        result.append("height=").append(this.tkhd.getHeight());
        result.append(";");
		result.append("version=").append(this.tkhd.getVersion());
		result.append(";");
		result.append("flags=").append(this.tkhd.getFlags());
		result.append(";");
		result.append("count=0");
		result.append("}");
        return result.toString();
	}
}
