package parse.wrapper;

import com.googlecode.mp4parser.boxes.apple.PixelAspectRationAtom;

/**
 * <h1>Wrapper for the PixelAspectRationAtom class of mp4parser</h1>
 * <p>PixelAspectRationAtomWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class PixelAspectRationAtomWrapper {

	private PixelAspectRationAtom pasp;
	
	/**
	 * The constructor of the PixelAspectRationAtomWrapper class.
	 * @param box A box of type PixelAspectRationAtom.
	 */
	public PixelAspectRationAtomWrapper(PixelAspectRationAtom box) {
		this.pasp = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
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
