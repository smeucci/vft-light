package parse.wrappers;

import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;

public class AudioSampleEntryWrapper implements Wrapper {

	private AudioSampleEntry mp4a;
	
	public AudioSampleEntryWrapper(AudioSampleEntry box) {
		this.mp4a = box;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
        result.append("AudioSampleEntry[");
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
        result.append("]");
        return result.toString();
	}
	
}
