package indeece;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

public class CorpusBuilder {
	private Set<Doc> corpus;
	
	static Document dom;
	public int documentID = 0;

	//Builds the corpus from the directory "corpusFolder"
	public CorpusBuilder(String corpusFolder) throws IOException {
		//Get the files in the directory
 	    ArrayList<File> files = getFiles(corpusFolder);
 	    corpus = new TreeSet<Doc>();
 	    
 	    //For every file parse it create corresponding Documents
 	    for(int i = 0;i < files.size(); i++)
 	    {
 	    	System.err.println("Indexing file "+files.get(i).getName());
	     	parseXmlFile(files.get(i));
	     	corpus.addAll(parseDocuments());
 	    }
	}
	
	//Creates an ArrayList containing the files of the directory "pathName"
	private  ArrayList<File> getFiles(String pathName) throws IOException {

	    	File indexDir = new File(pathName);
	    	if(!indexDir.isDirectory())
	    		throw new IOException("Cannot read corpus directory "+pathName);
		    String[] fileNames = indexDir.list();
		    ArrayList<File> files = new ArrayList<File>();
		    char[] extension = new char[3];
		    
	    	if(fileNames.length > 0){
	    		for(int i = 0;i<fileNames.length;i++){
	    			//fileNames do not contain the rest of the path at this moment, so it is added
	    			fileNames[i] = indexDir.getAbsolutePath() + "/" + fileNames[i];
	    			//only .xml files
	    			fileNames[i].getChars(fileNames[i].length()-3,fileNames[i].length(), extension, 0);
	    			if(String.valueOf(extension).contentEquals("xml")){
	    				files.add(new File(fileNames[i]));
	    			}
	    		}
	    	}
	    	return files;
	    }
	 
	 //Connects the Dom XmlParser with the file "file"
	 private void parseXmlFile(File file){
	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	
	    	try{
	    		DocumentBuilder db = dbf.newDocumentBuilder();
	    		dom = db.parse(file.getAbsolutePath());
	    	}catch(ParserConfigurationException pce) {
				pce.printStackTrace();
			}catch(SAXException se) {
				se.printStackTrace();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
	    }
	    
	 //Retrieves a Set of Documents - the corpus- from the file that the XmlParser is connected to.
	 private Set<Doc> parseDocuments(){
	    	Element docEle = dom.getDocumentElement();
	    	
	    	String title = "";
	    	String body = "";
	    	HashSet<Doc> corpus = new HashSet<Doc>();
	    	Doc temp;
	    	
	    	//Get a NodeList with content of REUTERS tags
	    	//Then, we are ready to retrieve the content of any tag within REUTERS tag
	    	NodeList nl = docEle.getElementsByTagName("REUTERS");
	    	
	    	if(nl != null && nl.getLength() > 0){
	    		for(int i = 0;i < nl.getLength();i++){
	    			Element el = (Element)nl.item(i);
	    			//Get text contained in TITLE tag 
	    			title = getTextValue(el,"TITLE");
	    			//Get text contained in BODY tag
	    			body = getTextValue(el,"BODY");
	    			if(title==null)
	    				continue;
	    			
	    			//Create a Doc with the retrieved title and body and the unique ID
	    			temp = new Doc(documentID,title,body);
	    			corpus.add(temp);
	    			//Increment documentID to be ready for the next Doc
	    			documentID++;
	    		}
	    	}
	    	return corpus;
	    }
	    //Gets the String value within a specific tag "tagName"
	    private String getTextValue(Element ele, String tagName) {
			String textVal = null;
			NodeList nl = ele.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				textVal = el.getFirstChild().getNodeValue();
			}

			return textVal;
		}
	    
	    //Returns the corpus
		public  Set<Doc> getCorpus()
		{
			return corpus;
		}
	
	
}
