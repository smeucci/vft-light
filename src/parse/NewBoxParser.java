package parse;

import static util.Util.removeBrackets;
import static util.Util.sanitize;

import java.util.List;

import org.jdom2.Element;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MetaBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleToChunkBox;
import com.coremedia.iso.boxes.StaticChunkOffsetBox;
import com.coremedia.iso.boxes.SyncSampleBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.EditListBox.Entry;
import com.coremedia.iso.boxes.mdat.MediaDataBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.VideoMediaHeaderBox;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.boxes.apple.CleanApertureAtom;
import com.googlecode.mp4parser.boxes.apple.TrackEncodedPixelsDimensionsAtom;
import com.googlecode.mp4parser.boxes.apple.TrackProductionApertureDimensionsAtom;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import com.mp4parser.iso14496.part15.AvcDecoderConfigurationRecord;

public class NewBoxParser {

	private IsoFile isoFile;
	
	public NewBoxParser(IsoFile isoFile) {
		this.isoFile = isoFile;
	}
	
	public void getBoxes(AbstractContainerBox ab, Element root) throws Exception {
		List<Box> boxes = (ab == null) ? (this.isoFile.getBoxes()) : (ab.getBoxes());
		for (Box box: boxes) {
			String boxType = sanitize(box.getType());
			Element item = new Element(boxType);
			switch (boxType) {
			case "ftyp":
				separateNameValueSpecial(item, extractNameValue(box.toString()));
				break;
			case "mdat":
				separateNameValue(item, extractNameValue(getMediaDataBoxFields((MediaDataBox) box)));
				break;
			case "mvhd": 
				separateNameValue(item, extractNameValue(getMovieHeaderBoxFields((MovieHeaderBox) box) ));
				break;
			case "tkhd":
				separateNameValue(item, extractNameValue(getTrackHeaderBoxFields((TrackHeaderBox) box)));
				break;
			case "clef":
				separateNameValue(item, extractNameValue(getCleanApertureAtomFields((CleanApertureAtom) box)));
				break;
			case "prof":
				separateNameValue(item, extractNameValue(getTrackProductionApertureDimensionsAtomFields((TrackProductionApertureDimensionsAtom) box)));
				break;
			case "enof":
				separateNameValue(item, extractNameValue(getTrackEncodedPixelsDimensionsAtomFields((TrackEncodedPixelsDimensionsAtom) box)));
				break;
			case "elst":
				separateNameValue(item, extractNameValue(getEditListBoxFields((EditListBox) box)));
				break;
			case "mdhd":
				separateNameValue(item, extractNameValue(getMediaHeaderBoxFields((MediaHeaderBox) box)));
				break;
			case "hdlr":
				separateNameValue(item, extractNameValue(getHandlerBoxFields((HandlerBox) box)));
				break;
			case "vmhd":
				separateNameValue(item, extractNameValue(getVideoMediaHeaderBox((VideoMediaHeaderBox) box)));
				break;
			case "dref":
				separateNameValue(item, extractNameValue(getDataReferenceBoxFields((DataReferenceBox) box)));
				break;
			case "stsd":
				separateNameValue(item, extractNameValue(getSampleDescriptionBoxFields((SampleDescriptionBox) box)));
				break;
			case "avc1":
				separateNameValue(item, extractNameValue(getVisualSampleEntryFields((VisualSampleEntry) box)));
				break;
			case "avcC":
				separateNameValue(item, extractNameValue(getAvcConfigurationBoxFields((AvcConfigurationBox) box)));
				break;
			case "stts":
				separateNameValue(item, extractNameValue(getTimeToSampleBoxFields((TimeToSampleBox) box)));
				break;
			case "stss":
				separateNameValue(item, extractNameValue(getSyncSampleBoxFields((SyncSampleBox) box)));
				break;
			case "sdtp":
				separateNameValue(item, extractNameValue(getSampleDependencyTypeBoxFields((SampleDependencyTypeBox) box)));
				break;
			case "stsc":
				separateNameValue(item, extractNameValue(getSampleToChunkBoxFields((SampleToChunkBox) box)));
				break;
			case "stsz":
				separateNameValue(item, extractNameValue(getSampleSizeBoxFields((SampleSizeBox) box)));
				break;
			case "stco":
				separateNameValue(item, extractNameValue(getStaticChunkOffsetBoxFields((StaticChunkOffsetBox) box)));
				break;
			case "meta":
				separateNameValue(item, extractNameValue(getMetaBoxFields((MetaBox) box)));
				break;
			default:
				System.out.println(box.getType());
				break;
			}
			
			try {getBoxes((AbstractContainerBox) box, item);}
			catch (Exception e) { /*System.out.println(e.getMessage());*/}
			root.addContent(item);
		}
	}
	
	protected String[] extractNameValue(String str) {
		//return a string vector containing the couple name=value for the input box
		String init = removeBrackets(str, "]");
		return init.split("\\" + "[")[1].split("\\;");
	}
	
	protected void separateNameValue(Element item, String[] str) {
		//return an item with attributes in the form of name="value"
		String[] result = null;			   
		for (String s: str) {			
			result = s.split("\\=");
			item.setAttribute(result[0].trim(), result[1]);
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
	
	protected String getMediaDataBoxFields(MediaDataBox box) {
		StringBuilder result = new StringBuilder();
		result.append("MediaDataBox[");
		result.append("size=").append(box.getSize());
		result.append("]");
		return result.toString();
	}
	
	protected String getMovieHeaderBoxFields(MovieHeaderBox box) {
		StringBuilder result = new StringBuilder();
		result.append("MovieHeaderBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(box.toString().replace("MovieHeaderBox[", ""));        
		return result.toString();
	}
	
	protected String getTrackHeaderBoxFields(TrackHeaderBox box) {
		StringBuilder result = new StringBuilder();
		result.append("TrackHeaderBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(box.toString().replace("TrackHeaderBox[", ""));        
		return result.toString();
	}
	
	protected String getCleanApertureAtomFields(CleanApertureAtom box) {
		StringBuilder result = new StringBuilder();
		result.append("CleanApertureAtom[");
        result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append("width=").append(box.getWidth());
        result.append(";");
        result.append("height=").append(box.getHeight());
        result.append("]");
		return result.toString();
	}
	
	protected String getTrackProductionApertureDimensionsAtomFields(TrackProductionApertureDimensionsAtom box) {
		StringBuilder result = new StringBuilder();
		result.append("TrackProductionApertureDimensionsAtom[");
        result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append("width=").append(box.getWidth());
        result.append(";");
        result.append("height=").append(box.getHeight());
        result.append("]");
		return result.toString();
	}
	
	protected String getTrackEncodedPixelsDimensionsAtomFields(TrackEncodedPixelsDimensionsAtom box) {
		StringBuilder result = new StringBuilder();
		result.append("TrackProductionApertureDimensionsAtom[");
        result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append("width=").append(box.getWidth());
        result.append(";");
        result.append("height=").append(box.getHeight());
        result.append("]");
		return result.toString();
	}
	
	protected String getEditListBoxFields(EditListBox box) {
		StringBuilder result = new StringBuilder();
		Entry entry = box.getEntries().get(0);
		result.append("EditListBox[");
        result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(entry.toString().replace("Entry{", "").replace("}", "").replace(",", ";"));
		return result.toString();		
	}
	
	protected String getMediaHeaderBoxFields(MediaHeaderBox box) {
		StringBuilder result = new StringBuilder();
		result.append("MediaHeaderBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(box.toString().replace("MediaHeaderBox[", ""));        
		return result.toString();
	}
	
	protected String getHandlerBoxFields(HandlerBox box) {
		StringBuilder result = new StringBuilder();
		result.append("HandlerBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append("handlerType=").append(box.getHandlerType());
        result.append(";");
        if (box.getName().hashCode() == 0) {
        	result.append("name=null");
        } else {
        	result.append("name=").append(box.getName().replace("HandlerBox[", "").replaceAll("||", ""));
        }
        result.append("]");
		return result.toString();
	}
	
	protected String getVideoMediaHeaderBox(VideoMediaHeaderBox box) {
		StringBuilder result = new StringBuilder();
		result.append("VideoMediaHeaderBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(box.toString().replace("VideoMediaHeaderBox[", ""));
		return result.toString();
	}
	
	protected String getDataReferenceBoxFields(DataReferenceBox box) {
		StringBuilder result = new StringBuilder();
		result.append("DataReferenceBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append("]");
		return result.toString();
	}
	
	protected String getSampleDescriptionBoxFields(SampleDescriptionBox box) {
		StringBuilder result = new StringBuilder();
		result.append("SampleDescriptionBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append("]");
		return result.toString();
	}
	
	protected String getVisualSampleEntryFields(VisualSampleEntry box) {
    	StringBuilder result = new StringBuilder();
        result.append("VisualSampleEntry[");
        result.append("data_reference_index=").append(box.getDataReferenceIndex());
        result.append(";");
        result.append("width=").append(box.getWidth());
        result.append(";");
        result.append("height=").append(box.getHeight());
        result.append(";");
        result.append("horizresolution=").append(box.getHorizresolution());
        result.append(";");
        result.append("vertresolution=").append(box.getVertresolution());
        result.append(";");
        result.append("frame_count=").append(box.getFrameCount());
        result.append(";");
        result.append("compressorname=").append(box.getCompressorname());
        result.append(";");
        result.append("depth=").append(box.getDepth());
        result.append("]");
        return result.toString();
	}
	
	protected String getAvcConfigurationBoxFields(AvcConfigurationBox box) {
		StringBuilder result = new StringBuilder();
		AvcDecoderConfigurationRecord avcd = box.getavcDecoderConfigurationRecord();
		result.append(avcd.toString().replace("{", "[").replace("}", "]").replace(",", ";"));
		return result.toString();
	}
	
	protected String getTimeToSampleBoxFields(TimeToSampleBox box) {
		StringBuilder result = new StringBuilder();
		result.append("TimeToSampleBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(box.toString().replace("TimeToSampleBox[", ""));
		return result.toString();
	}
	
	protected String getSyncSampleBoxFields(SyncSampleBox box) {
		StringBuilder result = new StringBuilder();
		result.append("SyncSampleBox[");
		result.append("version=").append(box.getVersion());
        result.append(";");
        result.append("flags=").append(box.getFlags());
        result.append(";");
        result.append(box.toString().replace("SyncSampleBox[", ""));
		return result.toString();
	}
	
	protected String getSampleDependencyTypeBoxFields(SampleDependencyTypeBox box) {
		StringBuilder result = new StringBuilder();
		List<SampleDependencyTypeBox.Entry> entries = box.getEntries();
		result.append("SampleDependencyTypeBox[");
		result.append("version=").append(box.getVersion());
		result.append(";");
		result.append("flags=").append(box.getFlags());
		result.append(";");
		result.append("entryCount=").append(entries.size());
		result.append("]");
		return result.toString();
	}
	
	protected String getSampleToChunkBoxFields(SampleToChunkBox box) {
		StringBuilder result = new StringBuilder();
		List<SampleToChunkBox.Entry> entries = box.getEntries();
		result.append("SampleToChunckBox[");
		result.append("version=").append(box.getVersion());
		result.append(";");
		result.append("flags=").append(box.getFlags());
		result.append(";");
		result.append("entryCount=").append(entries.size());
		result.append("]");
		return result.toString();
	}
	
	protected String getSampleSizeBoxFields(SampleSizeBox box) {
		StringBuilder result = new StringBuilder();
		result.append("SampleSizeBox[");
		result.append("version=").append(box.getVersion());
		result.append(";");
		result.append("flags=").append(box.getFlags());
		result.append(";");
		result.append(box.toString().replace("SampleSizeBox[", ""));
		return result.toString();
	}
	
	protected String getStaticChunkOffsetBoxFields(StaticChunkOffsetBox box) {
		StringBuilder result = new StringBuilder();
		result.append("StaticChunkOffsetBox[");
		result.append("version=").append(box.getVersion());
		result.append(";");
		result.append("flags=").append(box.getFlags());
		result.append(";");
		result.append(box.toString().replace("StaticChunkOffsetBox[", ""));
		return result.toString();
	}
	
	protected String getMetaBoxFields(MetaBox box) {
		StringBuilder result = new StringBuilder();
		result.append("MetaBox[");
		result.append("version=").append(box.getVersion());
		result.append(";");
		result.append("flags=").append(box.getFlags());
		result.append("]");
		return result.toString();
	}
	
}
