package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.googlecode.mp4parser.AbstractContainerBox;

import static util.Util.*;

public abstract class BoxParser {
	
	protected List<Box> boxes;
	
	//abstract class
	protected abstract void moovPrint(Element item, MovieBox moov);
	
	protected abstract void recXmlBuilder(Element item, AbstractContainerBox ab, int i);
	
	//concrete class
	public void getBoxes(IsoFile isoFile, Element root) throws Exception {
		//insert in root the content of the container, contained in isoFile
		for (Box box: this.boxes) {
    		if (box.getType() == "ftyp") {
    			Element item = new Element(box.getType());
    			root.addContent(separateNameValueSpecial(item, extractNameValue(box.toString()))); 			
			} else if (box.getType() == "moov") {
				Element item = new Element(box.getType());	
				MovieBox moov = isoFile.getMovieBox();
				if (moov != null) {
					//since it is the main unit of the container, a dedicated function will be used
					moovPrint(item, moov);
				}	 
				root.addContent(item);	    		    				
			} else {				
				Element item = new Element(box.getType());  
				root.addContent(item);	
			}
    	} 
	}
	
	public void getBoxes_new(IsoFile isoFile, AbstractContainerBox ab, Element root) throws Exception {
		List<Box> boxes;
		if (ab == null) {
			boxes = this.boxes;
		} else {
			boxes = ab.getBoxes();
		}
		for (Box box: boxes) {
			Element item = new Element(sanitize(box.getType()));
			try {
				getBoxes_new(isoFile, (AbstractContainerBox) box, item);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			root.addContent(item);
		}
	}
	
	protected String[] extractNameValue(String box) {
		//return a string vector containing the couple name=value for the input box
		String init = removeBrackets(box, "]");		
		String[] result = null;
		String[] splits = init.split("\\[");
		for (String s: splits) {
			result = s.split("\\;");			
		}		
		return result;
	}
	
	protected Element separateNameValue(Element item, String[] str) {
		//return an item with attributes in the form of name="value"
		String[] result = null;			   
		for (String s: str) {			
			result = s.split("\\=");
			item.setAttribute(result[0], result[1]);
		}			
		return item;
	}
	
	protected Element separateNameValueSpecial(Element item, String[] str) {
		//return an item with attributes in the form of name="value"
		String[] result = null;			
		for (int i = 0; i < str.length; i++) {
			result = str[i].split("\\=");
			if (i > 1) {
				//this control on index i was added for the ftyp box because the field
				// compatibleBrand is a list of compatible brands
				String name = "compatibleBrand_" + (i - 1);
				item.setAttribute(name, result[1]);
			} else {		
				item.setAttribute(result[0], result[1]);
			}
		}
		return item;
	}
	
	protected String extractDataReferenceBox(DataReferenceBox db) {
		//extract from the Isofile the content of the fields of the node "dref"
    	StringBuilder result = new StringBuilder();
        result.append("DataReferenceBox[");
        result.append("version=").append(db.getVersion());
        result.append(";");
        result.append("flags=").append(db.getFlags());
        result.append("]");
        return result.toString();
    }
	
    protected String extractSampleDescriptionBox(SampleDescriptionBox stsd) {
    	//extract fromt the IsoFile the content of the fields of the node "stsd"
    	StringBuilder result = new StringBuilder();
        result.append("SampleDescriptionBox[");
        result.append("version=").append(stsd.getVersion());
        result.append(";");
        result.append("flags=").append(stsd.getFlags());
        result.append("]");
		return result.toString();
    }	
	
}
