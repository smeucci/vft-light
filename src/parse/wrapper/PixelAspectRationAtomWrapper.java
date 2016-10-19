package parse.wrapper;

import com.googlecode.mp4parser.boxes.apple.PixelAspectRationAtom;

public class PixelAspectRationAtomWrapper {

	private PixelAspectRationAtom pasp;
	
	public PixelAspectRationAtomWrapper(PixelAspectRationAtom box) {
		this.pasp = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("hSpacing=").append(this.pasp.gethSpacing());
        result.append(";");
        result.append("vSpacing=").append(this.pasp.getvSpacing());
        result.append("}");
		return result.toString();
	}
	
}
