package indeece;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.util.Set;
import java.util.HashSet;

public class CorpusBuilder {
	private Set<Doc> corpus;
	
	static Document dom;
	public int documentID = 0;
	
	public CorpusBuilder(String corpusFolder) {
 	    ArrayList<File> files = getFiles(corpusFolder);
 	    corpus = new HashSet<Doc>();
 	    
 	    for(int i = 0;i < files.size(); i++)
 	    {
 	    	System.out.println("Indexing file "+files.get(i).getName());
	     	parseXmlFile(files.get(i));
	     	corpus.addAll(parseDocuments());
 	    }
	}
	
	private  ArrayList<File> getFiles(String pathName){

	    	File indexDir = new File(pathName);
		    String[] fileNames = indexDir.list();
		    ArrayList<File> files = new ArrayList<File>();
		    char[] extension = new char[3];
		    
	    	if(fileNames.length > 0){
	    		for(int i = 0;i<fileNames.length;i++){
	    			fileNames[i] = indexDir.getAbsolutePath() + "/" + fileNames[i];
	    			fileNames[i].getChars(fileNames[i].length()-3,fileNames[i].length(), extension, 0);
	    			if(String.valueOf(extension).contentEquals("xml")){
	    				files.add(new File(fileNames[i]));
	    			}
	    		}
	    	}
	    	return files;
	    }
		
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
	    
	    private Set<Doc> parseDocuments(){
	    	Element docEle = dom.getDocumentElement();
	    	
	    	String title = "";
	    	String body = "";
	    	HashSet<Doc> corpus = new HashSet<Doc>();
	    	Doc temp;
	    	
	    	NodeList nl = docEle.getElementsByTagName("REUTERS");
	    	
	    	if(nl != null && nl.getLength() > 0){
	    		for(int i = 0;i < nl.getLength();i++){
	    			Element el = (Element)nl.item(i);    			
	    			title = getTextValue(el,"TITLE");
	    			body = getTextValue(el,"BODY");
	    			if(title==null)
	    				continue;
	    			
	    			
	    			temp = new Doc(documentID,title,body);
	    			corpus.add(temp);
	    			documentID++;
	    		}
	    	}
	    	return corpus;
	    }
	    
	    private String getTextValue(Element ele, String tagName) {
			String textVal = null;
			NodeList nl = ele.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				textVal = el.getFirstChild().getNodeValue();
			}

			return textVal;
		}
	    
		public  Set<Doc> getCorpus()
		{
			return corpus;
		}
	
	
}
