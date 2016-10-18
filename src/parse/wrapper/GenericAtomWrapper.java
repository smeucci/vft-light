package parse.wrapper;

import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.boxes.apple.CleanApertureAtom;
import com.googlecode.mp4parser.boxes.apple.TrackEncodedPixelsDimensionsAtom;
import com.googlecode.mp4parser.boxes.apple.TrackProductionApertureDimensionsAtom;

public class GenericAtomWrapper implements Wrapper {

	private AbstractFullBox box;
	
	public GenericAtomWrapper(AbstractFullBox box) {
		this.box = box;
	}
	
	public String toString() {
		double width = 0, height = 0;
		int version = 0, flags = 0;
		if (box instanceof CleanApertureAtom) {
			width = ((CleanApertureAtom) this.box).getWidth();
			height = ((CleanApertureAtom) this.box).getHeight();
			version = ((CleanApertureAtom) this.box).getVersion();
			flags = ((CleanApertureAtom) this.box).getFlags();
		} else if (box instanceof TrackProductionApertureDimensionsAtom) {
			width = ((TrackProductionApertureDimensionsAtom) this.box).getWidth();
			height = ((TrackProductionApertureDimensionsAtom) this.box).getHeight();
			version = ((TrackProductionApertureDimensionsAtom) this.box).getVersion();
			flags = ((TrackProductionApertureDimensionsAtom) this.box).getFlags();
		} else if (box instanceof TrackEncodedPixelsDimensionsAtom) {
			width = ((TrackEncodedPixelsDimensionsAtom) this.box).getWidth();
			height = ((TrackEncodedPixelsDimensionsAtom) this.box).getHeight();
			version = ((TrackEncodedPixelsDimensionsAtom) this.box).getVersion();
			flags = ((TrackEncodedPixelsDimensionsAtom) this.box).getFlags();
		}
		
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("width=").append(width);
        result.append(";");
        result.append("height=").append(height);
        result.append(";");
        result.append("version=").append(version);
        result.append(";");
        result.append("flags=").append(flags);
        result.append("}");
		return result.toString();		
	}
	
}