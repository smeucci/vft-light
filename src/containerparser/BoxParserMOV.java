package containerparser;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;

public class BoxParserMOV {
	
	private List <Box> boxes;
	
	public BoxParserMOV(List<Box> b) {
		this.boxes = b;
	}
	
	private String myFourCCcode(Box box) {
		//return a four characters code of the box accordingly to the ISO standard
		 return box.getType();
    }
	
	private  Element separateNameValue(Element item,String[] str) {
		//return an item with attributes in the form of 'name="value"'
		String []result = null;			   
		for(String s: str){			
			result = s.split("\\=");
			item.setAttribute(result[0], result[1]);
		}			
		return item;
	}
	
	private  Element separateNameValueSpecial(Element item,String [] str) {
		//return an item with attributes in the form of 'name="value"'
		String [] result = null;			
		for(int i = 0; i < str.length; i++) {
			result = str[i].split("\\=");
			if (i > 1) {
				//this control on index i was added for the ftyp box because the field
				// compatibleBrand is a list of compatible brands.
				String name = "compatibleBrand_" + (i - 1);
				item.setAttribute(name, result[1]);
			} else {		
				item.setAttribute(result[0], result[1]);
			}
		}
		return item;
	}
	
	private String removeSquareBrackets(String str) {
		return str.replaceAll("]","");
	}
	
	private  String[] extractNameValue(String box){
		//return a string vector containing the couple name=value for the input box.
		String init = removeSquareBrackets(box);		
		String [] result = null;
		String [] splits = init.split("\\[");
		for (String s:splits) {
			result = s.split("\\;");			
		}		
		return result;
	}
	
	private String extractDataReferenceBox(DataReferenceBox db) {
		//extract from the Isofile the content of the fields of the node "dref"
    	StringBuilder result = new StringBuilder();
        result.append("DataReferenceBox[");
        result.append("version=").append(db.getVersion());
        result.append(";");
        result.append("flags=").append(db.getFlags());
        result.append("]");
        return result.toString();
    }
	
	private String extractSampleDescriptionBox(SampleDescriptionBox stsd) {
		//extract fromt the IsoFile the content of the fields of the node "stsd"
    	StringBuilder result = new StringBuilder();
        result.append("SampleDescriptionBox[");
        result.append("version=").append(stsd.getVersion());
        result.append(";");
        result.append("flags=").append(stsd.getFlags());
        result.append("]");
		return result.toString();
    }
	
	private Element hdlrSpecial(Element itemp, String[] str) {
		//extract the content of the fields of the node "hdlr" from the IsoFile.
		try {
			String []result = null;			   
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
	
	public void recXmlBuilder(Element item, AbstractContainerBox ab, int i) {
		//insert in item the content of the box ab
		AbstractContainerBox new_ab;
		List<Box> boxes = ab.getBoxes();
		for (Box box: boxes) {
			if (box.toString().endsWith("[]")) {
				//if the current box has children
				Element new_item = new Element(myFourCCcode(box));
				if (myFourCCcode(box) == "meta") {
					item.addContent(new_item);
				} else {
					try {
						new_ab = (AbstractContainerBox) box;
						if (myFourCCcode(box) == "dref") {
							DataReferenceBox db = (DataReferenceBox) new_ab;
							item.addContent(separateNameValueSpecial(new_item, extractNameValue(extractDataReferenceBox(db))));
						} 
						if (myFourCCcode(box) == "stsd") { //TODO add else if
							SampleDescriptionBox stsd = (SampleDescriptionBox) new_ab;
							item.addContent(separateNameValueSpecial(new_item, extractNameValue(extractSampleDescriptionBox(stsd))));
							try {
								//the class stsdUnderBoxMOV is used to insert in item
								//the content of the box stsd 
								List<SampleEntry> avc1 = new_ab.getBoxes(SampleEntry.class, true);
								//stsdUnderBoxMOV underBoxes = new stsdUnderBoxMOV(avc1); //TODO implement the stsdUnderBoxMOV class
								//underBoxes.getSTSDboxes(new_item, i);
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
				Element new_item = new Element(myFourCCcode(box));
    			if (myFourCCcode(box) == "tkhd" || myFourCCcode(box) == "mdhd" ||
        		    myFourCCcode(box) == "vmhd" || myFourCCcode(box) == "smhd" ||
        		    myFourCCcode(box) == "stts" || myFourCCcode(box) == "stco" ||
        		    myFourCCcode(box) == "stss" || myFourCCcode(box) == "stsc" || 
        		    myFourCCcode(box) == "stsz" )        		    
    			{
    				item.addContent(separateNameValue(new_item,extractNameValue(box.toString())));
    			} else if (myFourCCcode(box) == "hdlr") {
    				item.addContent(hdlrSpecial(new_item, extractNameValue(box.toString())));
    			} else {    				
    				if (myFourCCcode(box).equals("avc1") || myFourCCcode(box).equals("mp4a")) {
    				} else {
        				item.addContent(new_item);
    				}    				
    			}
			}
		}
	}
	
	public void moovPrint(Element item, MovieBox moov) {
		//insert in item the content of the box moov
		AbstractContainerBox ab;
		int i = 0; //used by the class stsdUnderBoxMOV
		List<Box> boxes = moov.getBoxes();
		for (Box box: boxes) {
			if (box.toString().endsWith("[]") || box.toString().endsWith("]}")) {
				Element new_item = new Element(myFourCCcode(box));
				if (myFourCCcode(box) == "trak") {
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
				Element new_item = new Element(myFourCCcode(box));
				if(myFourCCcode(box) == "mvhd") {
					item.addContent(separateNameValueSpecial(new_item, extractNameValue(box.toString())));
				} else {
					item.addContent(new_item);
				}
			}
		}
	}
	
	public void getBoxes(IsoFile isoFile, Document document, Element root) throws Exception {
		//insert in root the content of the container, contained in isoFile	
		for (Box box: this.boxes) {
			if (box.getType() == "ftyp") {
				//TODO myFourCCcode(box) is just a wrapper for box.getTypes()
				Element item = new Element(myFourCCcode(box));
				root.addContent(separateNameValueSpecial(item, extractNameValue(box.toString())));
			} else if (box.getType() == "moov") {
				Element item = new Element(myFourCCcode(box));
				MovieBox moov = isoFile.getMovieBox();
				if (moov != null) {
					//since it is the main unit of the container, a dedicated function will be used
					moovPrint(item, moov);
				}
				root.addContent(item);
			} else {
				Element item = new Element(myFourCCcode(box));
				root.addContent(item);
			}
		}
	}

}
