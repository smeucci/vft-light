package parse.wrapper;

import com.googlecode.mp4parser.boxes.apple.AppleGPSCoordinatesBox;

public class AppleGPSCoordinatesBoxWrapper {

	private AppleGPSCoordinatesBox xyz;
	
	public AppleGPSCoordinatesBoxWrapper(AppleGPSCoordinatesBox box) {
		this.xyz = box;
	}
	
	public String toString() {
		return "{gpscoords=" + this.xyz.getValue() + "}";
	}
	
}
