package parse.wrapper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.coremedia.iso.boxes.Box;

/**
 * <h1>Wrapper for the Box class of mp4parser</h1>
 * <p>DefaultBoxWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class DefaultBoxWrapper {

	private Box box;
	
	/**
	 * The constructor of the DefaultBoxWrapper class.
	 * @param box A box of type Box.
	 */
	public DefaultBoxWrapper(Box box) throws IOException {
		this.box = box;
		//saveToCSVFile();		
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("stuff=").append(this.box.toString());
		result.append(";");
		result.append("count=0");
		result.append("}");		
		return result.toString();
	}
	
	/*
	 * Save the type of the box that use DefaultBoxWrapper to a csv file.
	 */
	protected void saveToCSVFile() throws IOException {
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
