package indeece;

import filter.IFilter;
import filter.StopwordFilter;
import grammar.SimpleTokenizer;
import indeece.PostingList.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;

import stemmer.IStemmer;
import stemmer.PorterStemmer;

public class Index implements java.io.Serializable
{
	//A list of entries forms the Index
	//private ArrayList<Entry> entries;
	private HashMap<String,PostingList> entries = new HashMap<String,PostingList>();
	
	// stemmer
	private static IStemmer stemmer = new PorterStemmer();
	private static IFilter stopwordFilter = new StopwordFilter();
	private boolean stemming;
	
	//Create the index of the given corpus
	public Index(Set<Doc> corpus, boolean stemming, boolean permuterm)
	{
		this.stemming = stemming;
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
		
		System.err.println("Entries:");
		System.err.println(this.entries);
	}
	
	public void addDoc(Doc doc)
	{
		// index document's title & body.
		String content = doc.getTitle() + " " + doc.getBody();
		String terms[] = preprocess(content).split(" ");
		for(int i=0; i < terms.length; i++)
		{	
			if ( !this.entries.containsKey(terms[i]))
			{
				PostingList postingList = new PostingList();
				postingList.add(postingList.new Item(doc, 1));
				this.entries.put(terms[i], postingList);
			} else{
				
				PostingList postingList = this.entries.get(terms[i]);
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
	public PostingList getPostingList(String term)
	{
		if(this.entries.containsKey(term)) {
			return this.entries.get(term);
		}
		return null;
	}
	
	// convert content to a string of space separated terms.
	public String preprocess(String content)
	{
		String	ret = new String("");
		String	words[]	= content.split("[\\s,.]");
		
		for(int i=0; i < words.length; i++) {
			String term = preprocessWord(words[i]);
			if(term != null)
				ret = ret + " " + preprocessWord(words[i]);
		}

		return ret;
	}
	
	public String preprocessWord(String word)
	{
		String term = word.toLowerCase();
		
		// remove stopwords
		if(stopwordFilter.Filter(term) == "")
			return null;
		
		// perform stemming
		if(this.stemming)
			term = stemmer.stemTerm(term);
		// permuterm (TODO)
		
		return term;
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

}
