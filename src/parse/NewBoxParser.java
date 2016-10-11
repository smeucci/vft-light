package parse;

import static util.Util.*;

import java.util.List;

import org.jdom2.Element;

import parse.boxwrappers.*;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.boxes.apple.AppleRecordingYear2Box;
import com.googlecode.mp4parser.boxes.apple.CleanApertureAtom;
import com.googlecode.mp4parser.boxes.apple.TrackEncodedPixelsDimensionsAtom;
import com.googlecode.mp4parser.boxes.apple.TrackProductionApertureDimensionsAtom;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;

public class NewBoxParser {

	private IsoFile isoFile;
	
	public NewBoxParser(IsoFile isoFile) {
		this.isoFile = isoFile;
	}
	
	public void getBoxes(AbstractContainerBox ab, Element root) throws Exception {
		List<Box> boxes = (ab == null) ? (this.isoFile.getBoxes()) : (ab.getBoxes());
		for (Box box: boxes) {
			String boxType = sanitize(box.getType());
			
			Element item;
			try {
				item = new Element(boxType);
			} catch (Exception e) {
				item = new Element("unknown");
			}
			
			switch (boxType) {
			case "ftyp":
				separateNameValueSpecial(item, extractNameValue(box.toString()));
				break;
			case "mdat": case "frma":
				separateNameValue(item, extractNameValue(box.toString()));
				break;
			case "mvhd": case "tkhd": case "mdhd": case "vmhd": case "smhd":
			case "stts": case "stss": case "stsc": case "stsz": case "stco":
				separateNameValue(item, extractNameValue(new GenericBoxWrapper<AbstractFullBox>((AbstractFullBox) box).toString()));
				break;
			case "clef":
				separateNameValue(item, extractNameValue(new GenericAtomWrapper((CleanApertureAtom) box).toString()));
				break;
			case "prof":
				separateNameValue(item, extractNameValue(new GenericAtomWrapper((TrackProductionApertureDimensionsAtom) box).toString()));
				break;
			case "enof":
				separateNameValue(item, extractNameValue(new GenericAtomWrapper((TrackEncodedPixelsDimensionsAtom) box).toString()));
				break;
			case "elst":
				separateNameValue(item, extractNameValue(new EditListBoxWrapper((EditListBox) box).toString()));
				break;
			case "hdlr":
				separateNameValue(item, extractNameValue(new HandlerBoxWrapper((HandlerBox) box).toString()));
				break;
			case "dref":
				separateNameValue(item, extractNameValue(new GenericContainerBoxWrapper((DataReferenceBox) box).toString()));
				break;
			case "stsd":
				separateNameValue(item, extractNameValue(new GenericContainerBoxWrapper((SampleDescriptionBox) box).toString()));
				break;
			case "avc1":
				separateNameValue(item, extractNameValue(new VisualSampleEntryWrapper((VisualSampleEntry) box).toString()));
				break;
			case "avcC":
				separateNameValue(item, extractNameValue(new AvcConfigurationBoxWrapper((AvcConfigurationBox) box).toString()));
				break;
			case "sdtp":
				separateNameValue(item, extractNameValue(new SampleDependencyTypeBoxWrapper((SampleDependencyTypeBox) box).toString()));
				break;
			case "meta":
				separateNameValue(item, extractNameValue(new GenericContainerBoxWrapper((MetaBox) box).toString()));
				break;
			case "mp4a":
				separateNameValue(item, extractNameValue(new AudioSampleEntryWrapper((AudioSampleEntry) box).toString()));
				break;
			case "esds":
				separateNameValue(item, extractNameValue(new ESDescriptorBoxWrapper((ESDescriptorBox) box).toString()));
				break;
			case "day":
				//separateNameValue(item, extractNameValue(new AppleRecordingYear2BoxWrapper((AppleRecordingYear2Box) box).toString()));
			default:
				try {
					item.setAttribute("stuff", box.toString());
				} catch (Exception e) {
					item.setAttribute("stuff", "unknown");
				}
				break;
			}
			
			if (box instanceof AbstractContainerBox) {
				getBoxes((AbstractContainerBox) box, item);
			}
			root.addContent(item);
		}
	}
	
	protected String[] extractNameValue(String str) {
		//return a string vector containing the couple name=value for the input box
		String init = removeBrackets(removeBrackets(str, "}"), "]");
		return init.split("\\[|\\{")[1].split("\\;|\\,");
	}
	
	protected void separateNameValue(Element item, String[] str) {
		//return an item with attributes in the form of name="value"
		String[] result = null;			   
		for (String s: str) {			
			result = s.split("\\=");
			String value = (result.length == 1) ? "null" : result[1];
			item.setAttribute(result[0].trim(), value);
		}			
	}
	
	protected void separateNameValueSpecial(Element item, String[] str) {
		//return an item with attributes in the form of name="value"
		String[] result = null;			
		for (int i = 0; i < str.length; i++) {
			result = str[i].split("\\=");
			if (i > 1) {
				//this control on index i was added for the ftyp box because the field
				// compatibleBrand is a list of compatible brands
				String name = result[0] + "_" + (i - 1);
				item.setAttribute(name.trim(), result[1]);
			} else {		
				item.setAttribute(result[0].trim(), result[1]);
			}
		}
	}
	
}
