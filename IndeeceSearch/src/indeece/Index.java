package indeece;

import grammar.SimpleTokenizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import java.io.*;

public class Index implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
		Preprocessed preprocessor = new Preprocessed();
		
		//Index 		
		charStream = new ANTLRStringStream(wholeDoc);
		tokenizer = new SimpleTokenizer(charStream);
		nextToken = tokenizer.nextToken();
		while (nextToken != Token.EOF_TOKEN)
		{
			next = nextToken.getText();
			
			//Preprocessor is called to convert the string
			next = preprocessor.makeTerm(next);
			//Get next token for the next iteration
			nextToken = tokenizer.nextToken();
			//if next = empty (stopword) continue to the next token
			if(next == ""){
				continue;
			}			
			
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
	
	//Returns the set of documents (if any) found 
	// in the posting list of term 'term'
	public Set<Doc> getDocumentSet(String term){
		if(this.entries.containsKey(term)) {
			Iterator<PostingList.Item> listIt = this.entries.get(term).iterator();
			HashSet<Doc> docList = new HashSet<Doc>();
			while(listIt.hasNext()) {
				docList.add(listIt.next().getDoc());
			}
			return docList;
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
