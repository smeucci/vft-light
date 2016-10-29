package parse.wrapper;

import com.googlecode.mp4parser.boxes.apple.AppleGPSCoordinatesBox;

/**
 * <h1>Wrapper for the AppleGPSCoordinatesBox class of mp4parser</h1>
 * <p>AppleGPSCoordinatesBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class AppleGPSCoordinatesBoxWrapper {

	private AppleGPSCoordinatesBox xyz;
	
	/**
	 * The constructor of the AppleGPSCoordinatesBoxWrapper class.
	 * @param box A box of type AppleGPSCoordinatesBox.
	 */
	public AppleGPSCoordinatesBoxWrapper(AppleGPSCoordinatesBox box) {
		this.xyz = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		return "{gpscoords=" + this.xyz.getValue() + "}";
	}
	
}
