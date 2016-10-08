package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;

public class stsdUnderBoxMP4 extends stsdUnderBox {

	public stsdUnderBoxMP4(List<SampleEntry> a) {
		this.avc1 = a;
	}
	
	public void getSTSDboxes(Element root, int i) {
		//used to insert in root the content of the stsd box
    	for (Box box: this.avc1) {
    		if (i == 1) {
    			//the variable i is used to tell if track is video or audio
    			Element item = new Element(box.getType());
    			String str = getavc1((VisualSampleEntry) box);
    			root.addContent(separateNameValue(item, extractNameValue(str)));   				
    			checkIfOptionalBoxes(item, (AbstractContainerBox) box);   
        	} else {
        		//TODO change the same as stsdUnderBoxMOV
        		Element item = new Element(box.getType());
        		String str = getmp4a_new((AudioSampleEntry) box);
        		root.addContent(separateNameValue(item, extractNameValue(str)));
        		checkIfOptionalBoxes(item, (AbstractContainerBox) box);  
        	}   		
    	}
	}
    
    protected void checkIfOptionalBoxes(Element item, AbstractContainerBox ab){  
    	//check if the box mp4a contains optional boxes
		Element new_item;
		List<Box> boxes = ab.getBoxes();
		for (Box box: boxes) {
			if (box.getType().equals("esds")) {
				new_item = new Element(box.getType());
				String str = getESDescriptorBox((ESDescriptorBox) box);
				//item.addContent(separateNameValue(new_item, extractNameValue(str)));
				new_item.setAttribute("stuff", str);
				item.addContent(new_item);
			} else {
				new_item = new Element(box.getType());
				item.addContent(new_item);
			}
		} 	
    }
	
}
