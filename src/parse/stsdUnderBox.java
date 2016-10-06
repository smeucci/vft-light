package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;

import static util.Util.*;

public abstract class stsdUnderBox {
	
	protected List<SampleEntry> avc1;
	
	// abstract class
	public abstract void getSTSDboxes(Element root, int i);
	
	protected abstract void checkIfOptionalBoxes(Element item, AbstractContainerBox ab);
	
	//concrete classe
	protected String getavc1(VisualSampleEntry vcc) {
    	//extract the content of the fields of avc1 node from stsd
    	StringBuilder result = new StringBuilder();
        result.append("VisualSampleEntry[");
        result.append("data_reference_index=").append(vcc.getDataReferenceIndex());
        result.append(";");
        result.append("width=").append(vcc.getWidth());
        result.append(";");
        result.append("height=").append(vcc.getHeight());
        result.append(";");
        result.append("horizresolution=").append(vcc.getHorizresolution());
        result.append(";");
        result.append("vertresolution=").append(vcc.getVertresolution());
        result.append(";");
        result.append("frame_count=").append(vcc.getFrameCount());
        result.append(";");
        result.append("compressorname=").append(vcc.getCompressorname());
        result.append(";");
        result.append("depth=").append(vcc.getDepth());
        result.append("]");
        return result.toString();
    }
	
	protected String[] getmp4a(String box) {
		//extract the content of the fiels of the node mp4a from stsd
    	int i = countOccurrences(box, "{");    	
    	String[] result = null;    	
    	if (i == 1) {
    		String[] splits = box.split("\\{");
    		for (String s:splits) {    			
    			result = s.split("\\,");	
    		}	
    	}
    	return result;	
    }
	
	protected String[] extractNameValue(String box) {
		//return a string vector containing the couple name=value for the input box
		String init = removeSquareBrackets(box);
		String[] result = null;
		String[] splits = init.split("\\[");
		for (String s:splits) {			
			result = s.split("\\;");			
		}		
		return result;
	}
	
	protected Element separateNameValue(Element item, String[] str) {	
		//return an item with attributes in the form name="value"
		String[] result = null;			   
		for (String s:str) {			
			result = s.split("\\=");					
			if (result.length == 1) {				
				item.setAttribute(result[0].trim(), "");
			} else {				
				item.setAttribute(result[0].trim(), result[1]);
			}			
		}			
		return item;
	}
	
	
	
}
