package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;

public class stsdUnderBoxMOV extends stsdUnderBox {
	
	public stsdUnderBoxMOV(List<SampleEntry> a) {
		this.avc1 = a;
	}
	
	public void getSTSDboxes(Element root, int i) {
		//used to insert in root the content of the stsd box
		for (Box box: this.avc1) {
			//the variable i is used to tell if track is video or audio
			if (i == 0) {
				try {
					Element item = new Element(box.getType());
					String str = getavc1((VisualSampleEntry) box);
					root.addContent(separateNameValue(item, extractNameValue(str)));
					checkIfOptionalBoxes(item, (AbstractContainerBox) box);			
				} catch (Exception e) {
					System.out.println("error in avc1, in underBox");
				}
			} else if (i == 1) {
				try {
					Element item = new Element(box.getType());
					String[] str = getmp4a(box.toString());
					root.addContent(separateNameValue(item, str));
					checkIfOptionalBoxes(item, (AbstractContainerBox) box);
				} catch (Exception e) {
					System.out.println("error in mp4a, in underBox");
				}
			}
		}		
	}
	
	//TODO check original (if else)
	protected void checkIfOptionalBoxes(Element item, AbstractContainerBox ab) {
		//check if the box stsd contains optional boxes
		List<Box> boxes = ab.getBoxes();
		for (Box box: boxes) {
			if (box.getType() != "avc1") {
				System.out.println(); //TODO what does it do?
    			Element new_item = new Element(box.getType());
    			item.addContent(new_item);
			}
		}
    }

}
