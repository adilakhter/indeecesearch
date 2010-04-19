package indeece;

import grammar.SimpleTokenizer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;

public class Index
{
	//A list of entries forms the Index
	//private ArrayList<Entry> entries;
	private HashMap<String,PostingList> entries = new HashMap<String,PostingList>();
	
	//Create the index of the given corpus
	public Index(Set<Doc> corpus){
		super();
		Iterator<Doc> i = corpus.iterator();
		int current=1;
		int docNumber=corpus.size();
		while(i.hasNext())
		{			
			if(current%50==0)
			System.out.println("Indexing "+ current +" out of " + docNumber);
			current++;
			this.addDoc(i.next());		
		}
	}
	
	public void addDoc(Doc doc){
		CharStream charStream;
		SimpleTokenizer tokenizer;
		String next;
		String wholeDoc = doc.getTitle() + " " + doc.getBody();
		Token nextToken;
		
		//Index 		
		charStream = new ANTLRStringStream(wholeDoc);
		tokenizer = new SimpleTokenizer(charStream);
		nextToken = tokenizer.nextToken();
		while (nextToken != Token.EOF_TOKEN)
		{
			next = nextToken.getText();
			nextToken = tokenizer.nextToken();
			if ( !this.entries.containsKey(next))
			{
				PostingList postingList = new PostingList();
				postingList.add(postingList.new Item(doc, 1));
				this.entries.put(next, postingList);
			
			} else{
				
				PostingList postingList = this.entries.get(next);
			
				PostingList.Item currentItem ;
				
				if(postingList.contains(postingList.new Item(doc,0))){
					currentItem = postingList.floor(postingList.new Item(doc,0));
					currentItem.increaseFrequency();
				} else{
					postingList.add(postingList.new Item(doc,1));
				}
				/*
				Iterator<PostingList.Item> i = postingList.iterator(); 
				
				while(i.hasNext()) {
					currentItem=i.next();
					
					if(currentItem.getDoc().getID()==doc.getID()) 
						currentItem.increaseFrequency();
					else {
						postingList.add(postingList.new Item(doc,1));
						break;
					}
						
				}		*/				
			}
		}
		
		
	}
	
	//Returns the posting-list (if it exists) for term 'term'
	public PostingList getPostingList(String term){
		if(this.entries.containsKey(term)) {
			return this.entries.get(term);
		}
		return null;
	}
	
	public float tfidfCalc(String term, Preprocessed docBody)
	{
		// TODO
		return 0;
	}
	
	public float tfidf(String term, Doc doc)
	{
		// TODO
		return 0;
	}
}
