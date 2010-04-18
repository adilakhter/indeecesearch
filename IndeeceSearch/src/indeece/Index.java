package indeece;

import grammar.SimpleTokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;

public class Index
{
	//A list of entries forms the Index
	//private ArrayList<Entry> entries;
	private HashMap<String,PostingList> entries = new HashMap<String,PostingList>();
	
	//Create the index of the given corpus
	public Index(ArrayList<Doc> corpus){
		super();
	
		for(int i = 0; i < corpus.size();i++)
		{			
			this.addDoc(corpus.get(i));		
		}
	}
	
	public void addDoc(Doc doc){
		CharStream charStream;
		SimpleTokenizer tokenizer;
		String next;
		int frequency = 1;
		String wholeDoc = doc.getTitle() + " " + doc.getBody();
		
		//Index 		
		charStream = new ANTLRStringStream(wholeDoc);
		tokenizer = new SimpleTokenizer(charStream);
		while (tokenizer.nextToken() != null)
		{
			next = tokenizer.nextToken().getText();
			if ( !entries.containsKey(next))
			{
				PostingList postingList = new PostingList();
				postingList.add(postingList.new Item(doc, 1));
				
				//postingList.add(new PostingList.Item(doc, 1));
				//this.entries.put(next, postingList);
			
			} else{
				
				
			}
		}
		
		
	}
	
	public void addTerm(String term)
	{
		
	}
	
	
	
}
