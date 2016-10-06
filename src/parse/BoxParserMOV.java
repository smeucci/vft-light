package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;

public class BoxParserMOV extends BoxParser {
		
	public BoxParserMOV(List<Box> boxes) {
		this.boxes = boxes;
	}
	
	protected void moovPrint(Element item, MovieBox moov) {
		//insert in item the content of the box moov
		AbstractContainerBox ab;
		int i = 0; //used by the class stsdUnderBox
		List<Box> boxes = moov.getBoxes();
		for (Box box: boxes) {
			if (box.toString().endsWith("[]") || box.toString().endsWith("]}")) {
				Element new_item = new Element(box.getType());
				if (box.getType() == "trak") {
					try {
						ab = (AbstractContainerBox) box;
						recXmlBuilder(new_item, ab, i++);
						item.addContent(new_item);
					} catch (Exception e) {
						System.out.println("Inside if of trak, i am the last node, inside moov"); //TODO WHAT?
					}
				} else {
					item.addContent(new_item);
				}
			} else {
				Element new_item = new Element(box.getType());
				if(box.getType() == "mvhd") {
					item.addContent(separateNameValueSpecial(new_item, extractNameValue(box.toString())));
				} else {
					item.addContent(new_item);
				}
			}
		}
	}
	
	protected void recXmlBuilder(Element item, AbstractContainerBox ab, int i) {
		//insert in item the content of the box ab
		AbstractContainerBox new_ab;
		List<Box> boxes = ab.getBoxes();
		for (Box box: boxes) {
			if (box.toString().endsWith("[]")) {
				//if the current box has children
				Element new_item = new Element(box.getType());
				if (box.getType() == "meta") {
					item.addContent(new_item);
				} else {
					try {
						new_ab = (AbstractContainerBox) box;
						if (new_ab.getType() == "dref") {
							DataReferenceBox db = (DataReferenceBox) new_ab;
							item.addContent(separateNameValueSpecial(new_item, extractNameValue(extractDataReferenceBox(db))));
						} 
						if (new_ab.getType() == "stsd") { //TODO add else if
							SampleDescriptionBox stsd = (SampleDescriptionBox) new_ab;
							item.addContent(separateNameValueSpecial(new_item, extractNameValue(extractSampleDescriptionBox(stsd))));
							try {
								//the class stsdUnderBoxMOV is used to insert in item
								//the content of the box stsd
								List<SampleEntry> avc1 = new_ab.getBoxes(SampleEntry.class, true);
								stsdUnderBox underBoxes = new stsdUnderBoxMOV(avc1);
								underBoxes.getSTSDboxes(new_item, i);
							} catch (Exception e) {
								//System.out.println("error stsd, i am in func");
							}
						}
						recXmlBuilder(new_item, new_ab, i);
						item.addContent(new_item);
					} catch (Exception e) {
						//try catch used to understand when to stop exploring the AB box
					}
				}
			} else {
				Element new_item = new Element(box.getType());
    			if (box.getType() == "tkhd" || box.getType() == "mdhd" ||
        		    box.getType() == "vmhd" || box.getType() == "smhd" ||
        		    box.getType() == "stts" || box.getType() == "stco" ||
        		    box.getType() == "stss" || box.getType() == "stsc" || 
        		    box.getType() == "stsz" )        		    
    			{
    				item.addContent(separateNameValue(new_item, extractNameValue(box.toString())));
    			} else if (box.getType() == "hdlr") {
    				item.addContent(hdlrSpecial(new_item, extractNameValue(box.toString())));
    			} else {    				
    				if (box.getType().equals("avc1") || box.getType().equals("mp4a")) {
    				} else {
        				item.addContent(new_item);
    				}    				
    			}
			}
		}
	}
	
	protected Element hdlrSpecial(Element itemp, String[] str) {
		//extract the content of the fields of the node "hdlr" from the IsoFile.
		try {
			String[] result = null;			   
			for (String s: str) {			
				result = s.split("\\=");
				if ( result[1].startsWith("Core")) {
					String test = result[1].replaceAll("Core","Core");	  
					result[1] = test;
				} else if ( result[1].endsWith("Data Handler")) {
					result[1] = "";	  
					String test = "Core Media Data Handler";
					result[1] = test;
				}
				itemp.setAttribute(result[0], result[1]);
			}
		} catch (Exception e) {
			System.out.println("error in hdlr, i am in func");
		}
		return itemp;
	}
	
}
