package com.vftlite.parse;

import static com.vftlite.util.Util.*;

import java.io.IOException;
import java.util.List;

import org.jdom2.Element;

import com.vftlite.parse.wrapper.*;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.UserBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsHeaderBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.mdat.MediaDataBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.boxes.apple.AppleGPSCoordinatesBox;
import com.googlecode.mp4parser.boxes.apple.PixelAspectRationAtom;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.mp4parser.iso14496.part12.BitRateBox;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;

/**
 * <h1>Parse mp4-like video file containers</h1>
 * <p>The BoxParser class is used to parse video file containers into
 * jdom Element</p>
 * 
 * @author Saverio Meucci
 *
 */
public class BoxParser {

	private IsoFile isoFile;
	
	/**
	 * The constructor of the BoxParser class.
	 * @param isoFile The IsoFile object represents
	 * the video file containers.
	 */
	public BoxParser(IsoFile isoFile) {
		this.isoFile = isoFile;
	}
	
	/**
	 * This method builds a jdom Element representing the tree-like
	 * structur of the container.
	 * @param ab AbstractContainerBox from googlecode.mp4parser.
	 * @param root The jdom Element to build.
	 * @throws Exception
	 */
	public void getBoxes(AbstractContainerBox ab, Element root) throws Exception {
		List<Box> boxes = (ab == null) ? (this.isoFile.getBoxes()) : (ab.getBoxes());
		int numChild = 1;
		for (Box box: boxes) {
			String boxType = sanitize(box.getType());
			Element item;
			
			try {
				item = new Element(boxType + "-" + numChild);
			} catch (Exception e) {
				item = new Element("unkn-" + numChild);
			}
			numChild++;
			
			String attributes = parseBoxAttributesAsString(box, boxType);
			separateNameValue(item, extractNameValue(attributes));			
			
			if (box instanceof AbstractContainerBox) {
				getBoxes((AbstractContainerBox) box, item);
			}
			root.addContent(item);
		}
	}
	
	/**
	 * This method is used to extract the couple name=value from
	 * a string separated by comma.
	 * @param str The string to parse.
	 * @return An array of string representing the couple name=value.
	 */
	protected String[] extractNameValue(String str) {
		String init = removeBrackets(str, "}");
		return init.split("\\{")[1].split("\\;|\\,");
	}
	
	/**
	 * This method separate the couple name=value in order to set attributes for
	 * the jdom Element.
	 * @param item The jdom Element.
	 * @param str An array of string, which contains couple name=value.
	 */
	protected void separateNameValue(Element item, String[] str) {
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
	
	/**
	 * This method is used to parse the attributes of a Box
	 * of the container. Base on the type a different parsing will
	 * be implemented.
	 * @param box The Box to parse.
	 * @param boxType The type of the Box to parse.
	 * @return A string containing the attibutes of the Box.
	 * @throws IOException
	 */
	protected String parseBoxAttributesAsString(Box box, String boxType) throws IOException {
				
		String attr = null;
		
		switch (boxType) {
		case "ftyp":
			attr = new FileTypeBoxWrapper((FileTypeBox) box).toString();
			break;
		case "mdat":
			attr = new MediaDataBoxWrapper((MediaDataBox) box).toString();
			break;
		case "frma":
			attr = new OriginalFormatBoxWrapper((OriginalFormatBox) box).toString();
			break;
		case "mvhd": case "mdhd": case "vmhd": case "smhd": case "stts": 
		case "stss": case "stsc": case "stsz": case "stco": case "sidx": 
		case "mfhd": case "tfhd": case "tfdt": case "trun": case "co64":
			attr = new GenericBoxWrapper((AbstractFullBox) box).toString();
			break;
		case "tkhd":
			attr = new TrackHeaderBoxWrapper((TrackHeaderBox) box).toString();
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
		case "uuid":
			attr = new UserBoxWrapper((UserBox) box).toString();
			break;
		//case "day":
			//attr = new AppleRecordingYear2BoxWrapper((AppleRecordingYear2Box) box).toString();
			//break;
		default:
			attr = new DefaultBoxWrapper(box).toString();
			break;
		}
		
		return attr;
	}
	
}
