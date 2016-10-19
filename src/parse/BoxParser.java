package parse;

import static util.Util.*;

import java.io.IOException;
import java.util.List;

import org.jdom2.Element;

import parse.wrapper.*;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsHeaderBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.boxes.apple.AppleGPSCoordinatesBox;
import com.googlecode.mp4parser.boxes.apple.PixelAspectRationAtom;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.mp4parser.iso14496.part12.BitRateBox;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;

public class BoxParser {

	private IsoFile isoFile;
	
	public BoxParser(IsoFile isoFile) {
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
				item = new Element("unkn");
			}
			
			String attributes = parseBoxAttributesAsString(box, boxType);
			separateNameValue(item, extractNameValue(attributes));			
			
			if (box instanceof AbstractContainerBox) {
				getBoxes((AbstractContainerBox) box, item);
			}
			root.addContent(item);
		}
	}
	
	protected String[] extractNameValue(String str) {
		//return a string vector containing the couple name=value for the input box
		String init = removeBrackets(str, "}");
		return init.split("\\{")[1].split("\\;|\\,");
	}
	
	protected void separateNameValue(Element item, String[] str) {
		//return an item with attributes in the form of name="value"
		String[] result = null;			   
		for (String s: str) {			
			result = s.split("\\=");
			String value = (result.length == 1) ? "null" : result[1];
			try {
				item.setAttribute(result[0].trim(), value);
			} catch (Exception e) {
				item.setAttribute(result[0].trim(), "null");
			}
		}			
	}
	
	protected String parseBoxAttributesAsString(Box box, String boxType) throws IOException {
				
		String attr = null;
		
		switch (boxType) {
		case "ftyp":
			attr = new FileTypeBoxWrapper((FileTypeBox) box).toString();
			break;
		case "mdat":
			attr = box.toString();
			break;
		case "frma":
			attr = box.toString().replace("[", "{").replace("]", "}");
			break;
		case "mvhd": case "tkhd": case "mdhd": case "vmhd": case "smhd":
		case "stts": case "stss": case "stsc": case "stsz": case "stco":
		case "sidx": case "mfhd": case "tfhd": case "tfdt": case "trun":
		case "co64":
			attr = new GenericBoxWrapper((AbstractFullBox) box).toString();
			break;
		case "clef": case "prof": case "enof":
			attr = new GenericAtomWrapper((AbstractFullBox) box).toString();
			break;
		case "elst":
			attr = new EditListBoxWrapper((EditListBox) box).toString();
			break;
		case "hdlr":
			attr = new HandlerBoxWrapper((HandlerBox) box).toString();
			break;
		case "dref": case "stsd": case "meta":
			attr = new GenericContainerBoxWrapper((AbstractContainerBox) box).toString();
			break;
		case "avc1":
			attr = new VisualSampleEntryWrapper((VisualSampleEntry) box).toString();
			break;
		case "avcC":
			attr = new AvcConfigurationBoxWrapper((AvcConfigurationBox) box).toString();
			break;
		case "sdtp":
			attr = new SampleDependencyTypeBoxWrapper((SampleDependencyTypeBox) box).toString();
			break;
		case "mp4a":
			attr = new AudioSampleEntryWrapper((AudioSampleEntry) box).toString();
			break;
		case "esds":
			attr = new ESDescriptorBoxWrapper((ESDescriptorBox) box).toString();
			break;
		case "xyz":
			attr = new AppleGPSCoordinatesBoxWrapper((AppleGPSCoordinatesBox) box).toString();
			break;
		case "moof":
			attr = new MovieFragmentBoxWrapper((MovieFragmentBox) box).toString();
			break;
		case "mehd":
			attr = new MovieExtendsHeaderBoxWrapper((MovieExtendsHeaderBox) box).toString();
			break;
		case "trex":
			attr = new TrackExtendsBoxWrapper((TrackExtendsBox) box).toString();
			break;
		case "btrt":
			attr = new BitRateBoxWrapper((BitRateBox) box).toString();
			break;
		case "pasp":
			attr = new PixelAspectRationAtomWrapper((PixelAspectRationAtom) box).toString();
			break;
		case "day":
			//attr = new AppleRecordingYear2BoxWrapper((AppleRecordingYear2Box) box).toString();
			//break;
		default:
			attr = new DefaultBoxWrapper(box).toString();
			break;
		}
		
		return attr;
	}
	
}
