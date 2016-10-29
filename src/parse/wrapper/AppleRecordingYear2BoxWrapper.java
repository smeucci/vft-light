package parse.wrapper;

import com.googlecode.mp4parser.boxes.apple.AppleRecordingYear2Box;

/**
 * <h1>Wrapper for the AppleRecordingYear2Box class of mp4parser</h1>
 * <p>AppleRecordingYear2BoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class AppleRecordingYear2BoxWrapper implements Wrapper {

	private AppleRecordingYear2Box day;
	
	/**
	 * The constructor of the AppleRecordingYear2BoxWrapper class.
	 * @param box A box of type AppleRecordingYear2Box.
	 */
	public AppleRecordingYear2BoxWrapper(AppleRecordingYear2Box box) {
		this.day = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("dataType=").append(this.day.getDataType());
		result.append(";");
		result.append("dataCountry=").append(this.day.getDataCountry());
		result.append(";");
		result.append("dataLanguage=").append(this.day.getDataLanguage());
		result.append(";");
		result.append("language=").append(this.day.getLanguageString());
		result.append(";");
		result.append("value=").append(this.day.getValue());
		result.append("}");
		return result.toString();	
	}
}
