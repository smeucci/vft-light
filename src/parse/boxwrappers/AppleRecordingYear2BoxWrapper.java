package parse.boxwrappers;

import com.googlecode.mp4parser.boxes.apple.AppleRecordingYear2Box;

public class AppleRecordingYear2BoxWrapper {

	private AppleRecordingYear2Box day;
	
	public AppleRecordingYear2BoxWrapper(AppleRecordingYear2Box box) {
		this.day = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[");
		result.append("dataType=").append(this.day.getDataType());
		result.append(";");
		result.append("dataCountry=").append(this.day.getDataCountry());
		result.append(";");
		result.append("dataLanguage=").append(this.day.getDataLanguage());
		result.append(";");
		result.append("language=").append(this.day.getLanguageString());
		result.append(";");
		result.append("value=").append(this.day.getValue());
		result.append("]");
		return result.toString();		
	}
}
