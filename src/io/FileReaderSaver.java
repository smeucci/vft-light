package io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.coremedia.iso.IsoFile;

public class FileReaderSaver {
	
	//attributes filenameand sourcePath is derived from attribute url
	private String url;
	private String filename;
	private String destinationPath;
	
	public FileReaderSaver(String url, String xmlDestinationPath) {
		this.url = url;
		fillFilenamePath();
		if (xmlDestinationPath.endsWith("/")) {
			this.destinationPath = xmlDestinationPath + this.filename + ".xml";
		} else {
			this.destinationPath = xmlDestinationPath + "/" + this.filename + ".xml";
		}
	}
	
	public FileReaderSaver(String url) {
		this.url = url;
		this.filename = this.destinationPath = null;
	}

	private void fillFilenamePath() {
		String init = this.url;
		String[] splits = init.split("\\/");
		this.filename = splits[splits.length - 1];
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDestinationPath() {
		return this.destinationPath;
	}
	
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	
	public IsoFile getIsoFile() throws IOException {
		//return an IsoFile, it contains all boxes of the container
		File file = new File(this.url);
		if (!file.exists()) {
			System.err.println("Could not find " + this.url + ".");
			System.exit(0);
			return null;
		}
		return new IsoFile(file.getAbsolutePath());
	}
	
	public void saveOnFile(Document document) throws Exception {
		//save document on a xml file
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat());
		File save = new File(this.destinationPath);
		outputter.output(document, new FileOutputStream(save));
	}
	
	public Document getDocumentFromXMLFile() throws JDOMException, IOException {
		//create a SAXBuilder and a document
		SAXBuilder builder = new SAXBuilder();
		File file = new File(this.url);
		if (!file.exists()) {
			System.err.println("Could not find " + this.url + ".");
			System.exit(0);
			return null;
		}
		return builder.build(file);
	}
	
}