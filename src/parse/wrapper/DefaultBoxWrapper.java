package parse.wrapper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.coremedia.iso.boxes.Box;

public class DefaultBoxWrapper {

	private Box box;
	
	public DefaultBoxWrapper(Box box) throws IOException {
		this.box = box;
		//saveToCSVFile();		
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("stuff=").append(this.box.toString());
		result.append("}");		
		return result.toString();
	}
	
	public void saveToCSVFile() throws IOException {
		Writer out = new BufferedWriter(new OutputStreamWriter(
					 new FileOutputStream("/home/saverio/Projects/vft-lite/dataset/newboxes.csv", true), "UTF-8"));
		StringBuilder text = new StringBuilder();
		text.append(box.getType());
		text.append(",");
		text.append(box.getClass().toString());
		text.append(",");
		text.append(box.toString());
		text.append("\n");
		try {
		    out.write(text.toString());
		} finally {
		    out.close();
		}
	}
}
