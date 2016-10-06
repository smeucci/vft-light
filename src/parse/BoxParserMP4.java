package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;

public class BoxParserMP4 extends BoxParser {
	
	public BoxParserMP4(List<Box> boxes) {
		this.boxes = boxes;
	}
	
	protected void moovPrint(Element item, MovieBox moov) { 
		//insert in item the content of the box moov
    	AbstractContainerBox ab;
    	int i = 0; //used by the class stsdUnderBox
		List<Box> boxes = moov.getBoxes(); 		
		for (Box box: boxes) {			
			if (box.toString().endsWith("[]") || box.toString().endsWith("]}")) {	
				//if the current box has children
				Element new_item = new Element(box.getType());
    			try {
    				ab = (AbstractContainerBox) box;
        			recXmlBuilder(new_item, ab, i++); 
        			item.addContent(new_item);
    			} catch (Exception e) {
    				//try catch used to understand when to stop exploring the moov box
    			} 			  			
    		} else {    			
    			Element new_item = new Element(box.getType());    			
    			item.addContent(separateNameValue(new_item, extractNameValue(box.toString())));
    		}			
		} 	
    }
	
	protected void recXmlBuilder(Element item, AbstractContainerBox ab, int i){
		//insert in item the content of the box ab
    	AbstractContainerBox new_ab;    	
    	List<Box> boxes= ab.getBoxes();
		for (Box box: boxes) {								
			if (box.toString().endsWith("[]")) {    	
				//if the current box has children
    			Element new_item = new Element(box.getType());
    			try {    				
    				new_ab = (AbstractContainerBox) box;    				
    				if (new_ab.getType() == "dref") {        				     				       				
        				DataReferenceBox db = (DataReferenceBox) new_ab;
        				item.addContent(separateNameValue(new_item, extractNameValue(extractDataReferenceBox(db))));
        			}
    				if (new_ab.getType() == "stsd") { //TODO add else if   				   				      				
        				SampleDescriptionBox stsd = (SampleDescriptionBox ) new_ab;	   			
        				item.addContent(separateNameValue(new_item, extractNameValue(extractSampleDescriptionBox(stsd))));        				
        				//the class stsdUnderBoxMOV is used to insert in item
						//the content of the box stsd 
        				List<SampleEntry> avc1 = new_ab.getBoxes(SampleEntry.class,true);  
        				stsdUnderBox underBoxes = new stsdUnderBoxMP4(avc1);      				
        				underBoxes.getSTSDboxes(new_item, i); 
        			}
    				recXmlBuilder(new_item, new_ab, i); 
        			item.addContent(new_item);
    			}catch(Exception e){
    				//try catch used to understand when to stop exploring the AB box
    			}	 			
    		} else {  
    			Element new_item = new Element(box.getType());  			
    			item.addContent(separateNameValue(new_item, extractNameValue(box.toString())));
    		}
		}
    }	
	
}
