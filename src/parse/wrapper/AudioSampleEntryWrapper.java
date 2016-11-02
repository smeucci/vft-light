package parse.wrapper;

import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;

/**
 * <h1>Wrapper for the AudioSampleEntry class of mp4parser</h1>
 * <p>AudioSampleEntryWrapper implements the Wrapper interface 
 * and it is used to extend the Box classes
 * of the mp4parser with a formatted toString method which return
 * the attributes of the Box as a String.</p>
 * 
 * @author Saverio Meucci
 *
 */
public class AudioSampleEntryWrapper implements Wrapper {

	private AudioSampleEntry mp4a;
	
	/**
	 * The constructor of the AudioSampleEntryWrapper class.
	 * @param box A box of type AudioSampleEntry.
	 */
	public AudioSampleEntryWrapper(AudioSampleEntry box) {
		this.mp4a = box;
	}
	
	/**
	 * Get the attributes of the Box as a String.
	 * @return The attributes of the Box as String.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("bytesPerSample=").append(this.mp4a.getBytesPerSample());
        result.append(";");
        result.append("bytesPerFrame=").append(this.mp4a.getBytesPerFrame());
        result.append(";");
        result.append("bytesPerPacket=").append(this.mp4a.getBytesPerPacket());
        result.append(";");
        result.append("samplesPerPacket=").append(this.mp4a.getSamplesPerPacket());
        result.append(";");
        result.append("packetSize=").append(this.mp4a.getPacketSize());
        result.append(";");
        result.append("compressionId=").append(this.mp4a.getCompressionId());
        result.append(";");
        result.append("soundVersion=").append(this.mp4a.getSoundVersion());
        result.append(";");
        result.append("sampleRate=").append(this.mp4a.getSampleRate());
        result.append(";");
        result.append("sampleSize=").append(this.mp4a.getSampleSize());
        result.append(";");
        result.append("channelCount=").append(this.mp4a.getChannelCount());
        result.append(";");
		result.append("count=0");
        result.append("}");
        return result.toString();
	}
	
}
