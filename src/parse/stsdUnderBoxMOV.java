package parse;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.coremedia.iso.boxes.UnknownBox;
import com.coremedia.iso.boxes.apple.AppleWaveBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.SampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.DecoderConfigDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;

import static util.Util.*;

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
					//String str = getmp4a_new((AudioSampleEntry) box);
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
		//check if the box mp4a contains optional boxes
		Element new_item;
		List<Box> boxes = ab.getBoxes();
		for (Box box: boxes) {
			if (box.getType().equals("esds")) {
				new_item = new Element(box.getType());
				//String str = getESDescriptorBox((ESDescriptorBox) box);
				//item.addContent(separateNameValue(new_item, extractNameValue(str)));
				item.addContent(new_item);
			} else if (box.getType().equals("wave")) {
				new_item = new Element(box.getType());
				getAppleWaveBox(new_item, (AppleWaveBox) box);
				item.addContent(new_item);
			} else {
				new_item = new Element(box.getType());
				item.addContent(new_item);
			}
		}
    }
	
	protected void getAppleWaveBox(Element item, AppleWaveBox wave) {
		//ectract the content of the field wave insinde mp4a
		Element new_item;
		List<Box> boxes = wave.getBoxes();
		for (Box box: boxes) {
			if (box.getType().equals("frma")) {
				new_item = new Element((box.getType()));
				String[] str = getOriginalFormatBox(box.toString());
				item.addContent(separateNameValue(new_item, str));
			} else if (box.getType().equals("mp4a")) {
				new_item = new Element((box.getType()));
				String[] str = getmp4a(box.toString());
				item.addContent(separateNameValue(new_item, str));
			} else if (box.getType().equals("esds")) {
				new_item = new Element((box.getType()));
				String str = getESDescriptorBox((ESDescriptorBox) box);
				//item.addContent(separateNameValue(new_item, extractNameValue(str)));
				new_item.setAttribute("stuff", str);
				item.addContent(new_item);
			} else if (box.getType().equals("")){
				new_item = new Element((box.getType()));
				item.addContent(new_item);
			}
		}
	}

}
