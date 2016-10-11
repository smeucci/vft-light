package parse.boxwrappers;

import com.googlecode.mp4parser.boxes.apple.CleanApertureAtom;
import com.googlecode.mp4parser.boxes.apple.TrackEncodedPixelsDimensionsAtom;
import com.googlecode.mp4parser.boxes.apple.TrackProductionApertureDimensionsAtom;

public class GenericAtomWrapper implements Wrapper {

	private CleanApertureAtom clef;
	private TrackProductionApertureDimensionsAtom prof;
	private TrackEncodedPixelsDimensionsAtom enof;
	
	public GenericAtomWrapper(CleanApertureAtom atom) {
		this.clef = atom;
		this.prof = null;
		this.enof = null;
	}
	
	public GenericAtomWrapper(TrackProductionApertureDimensionsAtom atom) {
		this.clef = null;
		this.prof = atom;
		this.enof = null;
	}
	
	public GenericAtomWrapper(TrackEncodedPixelsDimensionsAtom atom) {
		this.clef = null;
		this.prof = null;
		this.enof = atom;
	}
	
	public String toString() {
		double width = 0, height = 0;
		int version = 0, flags = 0;
		if (this.clef != null) {
			width = this.clef.getWidth();
			height = this.clef.getHeight();
			version = this.clef.getVersion();
			flags = this.clef.getFlags();
		} else if (this.prof != null) {
			width = this.prof.getWidth();
			height = this.prof.getHeight();
			version = this.prof.getVersion();
			flags = this.prof.getFlags();
		} else if (this.enof != null) {
			width = this.enof.getWidth();
			height = this.enof.getHeight();
			version = this.enof.getVersion();
			flags = this.enof.getFlags();
		}
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		result.append("width=").append(width);
        result.append(";");
        result.append("height=").append(height);
        result.append(";");
        result.append("version=").append(version);
        result.append(";");
        result.append("flags=").append(flags);
        result.append("]");
		return result.toString();		
	}
	
}
