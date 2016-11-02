package com.vftlite.parse.wrapper;

import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.boxes.apple.CleanApertureAtom;
import com.googlecode.mp4parser.boxes.apple.TrackEncodedPixelsDimensionsAtom;
import com.googlecode.mp4parser.boxes.apple.TrackProductionApertureDimensionsAtom;

/**
 * <h1>Wrapper for the CleanApertureAtom, TrackProductionApertureDimensionsAtom and TrackEncodedPixelsDimensionsAtom
 *  classes of mp4parser</h1>
 * <p>GenericAtomWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class GenericAtomWrapper implements Wrapper {

	private AbstractFullBox box;
	
	/**
	 * The constructor of the GenericAtomWrapper class.
	 * @param box A box of type AbstractFullBox.
	 */
	public GenericAtomWrapper(AbstractFullBox box) {
		this.box = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
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
        result.append(";");
		result.append("count=0");
        result.append("}");
		return result.toString();		
	}
	
}