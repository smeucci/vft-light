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

/**
* <h1>Save and read files.</h1>
* The FileReaderSaver implements a class that can be used to
* save jdom document into xml files and read xml files or video
* files. 
* <p>
*
* @author  Saverio Meucci
*/

public class FileReaderSaver {
	
	private String url;
	private String filename;
	private String destinationPath;
	
    /**
    * This constructor is used to create an object when the save
    * feature is needed.
    * @param url This is the first parameter and represents the 
    * name of the file to save.
    * @param xmlDestinationPath This is the second parameter and represents
    * the output folder, where the file will be saved.
    */
	public FileReaderSaver(String url, String xmlDestinationPath) {
		this.url = url;
		fillFilenamePath();
		if (xmlDestinationPath.endsWith("/")) {
			this.destinationPath = xmlDestinationPath + this.filename + ".xml";
		} else {
			this.destinationPath = xmlDestinationPath + "/" + this.filename + ".xml";
		}
	}
	
	/**
	* This constructor is used to create an object when the read
	* feature is needed.
	* @param url This is the only parameter and represents the 
	* absolute path of the file to read.
	*/
	public FileReaderSaver(String url) {
		this.url = url;
		this.filename = this.destinationPath = null;
	}

	/**
	* This method is used to extract the name of the file
	* from its absolute path.
	*/
	private void fillFilenamePath() {
		String init = this.url;
		String[] splits = init.split("\\/");
		this.filename = splits[splits.length - 1];
	}
	
	/**
	* Get method for the filename attribute.
	* @return String It returns the filename.
	*/
	public String getFilename() {
		return this.filename;
	}
	
	/**
	* Set method for the filename attribute.
	* @param filename The new filename of the file.
	*/
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	* Get method for the url attribute.
	* @return String The absolute path of the file.
	*/
	public String getUrl() {
		return this.url;
	}
	
	/**
	* Set method for the url attribute.
	* @param url The new absolute path of the file.
	*/
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	* Get method for the destinationPath attribute.
	* @return String The absolute path of the saved file.
	*/
	public String getDestinationPath() {
		return this.destinationPath;
	}
	
	/**
	* Set method for the destinationPath attribute.
	* @param destinationPath The new absolute path of the saved file.
	*/
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	
	/**
	* This method is used to extract the Iso file from a video file (mp4-like).
	* @return IsoFile The Iso file of the video file represented by the url
	* attribute.
	* @throws IOException
	*/
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
	
	/**
	* This method is used to save a jdom document into a xml file
	* specified by the destinationPath attribute.
	* @param document A jdom Document.
	* @throws Exception
	*/
	public void saveOnFile(Document document) throws Exception {
		//save document on a xml file
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(Format.getPrettyFormat());
		File save = new File(this.destinationPath);
		outputter.output(document, new FileOutputStream(save));
	}
	
	/**
	* This method is used to create a jdom document from a xml file specified
	* by the url attribute.
	* @return Document A jdom Document.
	* @throws JDOMException
	* @throws IOException
	*/
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