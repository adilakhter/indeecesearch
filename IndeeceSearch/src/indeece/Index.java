package indeece;

import filter.IFilter;
import filter.PermutermQueryProcessor;
import filter.StopwordFilter;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import stemmer.IStemmer;
import stemmer.PorterStemmer;
import termGenerator.ITermGenerator;

public class Index implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	public static final int MAX_PERMUTATIONS = 30;
	
	private static PermutermQueryProcessor queryPr = new PermutermQueryProcessor();

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
	}
	
	public void addDoc(Doc doc)
	{
		// index document's title & body.
		String content = preprocess(doc.getTitle() + " " + doc.getBody());
		if(content == null)
			return;
		
		content=content.trim();
		String terms[] = content.split(" ");
		for(int i=0; i < terms.length; i++)
		{	if ( terms[i] == "")
			{
				System.out.print("term isnull");
			
			}
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
		String	ret = "";
		String	words[]	= content.split("[\\s\t\n\r\f\'\"\\!@#$%^&\\*()_\\+\\-=\\{\\}\\|\\[\\]/`~,>.\\?:;<]");

		for(int i=0; i < words.length; i++) {
			String term = new String();
			try {
				term = preprocessWord(words[i]).firstElement();
				if(ret == "") 
					ret= term;
				else
					ret = ret + " " +term;
			}
			catch(NoSuchElementException e) {
				continue;
			}
		}
		
		return ret;
	}
	
	public Vector<String> preprocessWord(String word)
	{
		
		String term = word.toLowerCase().trim();
		
		Vector<String>  preprocessed=new Vector<String>();
		if(word.length() <= 1)
			return preprocessed;
		
		// remove stopwords
		if(stopwordFilter.Filter(term) == "")
			return preprocessed;
		
		// perform stemming
		if(this.stemming) {
			System.out.print("Term:" +term);
			term = stemmer.stemTerm(term);
			System.out.println("\tStemmed term:"+ term);
		}
		if(word.contains("*")) {
			System.out.println("Term with star:"+ term);
			term=queryPr.Filter(term);
			System.out.println("New term:"+ term);
			preprocessed = Indeece.getPermutermTree().getTerms(term);
			Iterator<String> it = preprocessed.iterator();
			System.out.println("Derived Terms:");
			while(it.hasNext())
				System.out.println(it.next());
		}
		else {
			preprocessed.add(term);
		}
		
		return preprocessed;
	}
	
	//Returns the set of documents (if any) found 
	// in the posting list of term 'term'
	public Set<Doc> getDocumentSet(String term){
		if(this.entries.containsKey(term)) {
			Iterator<PostingList.Item> listIt = this.entries.get(term).iterator();
			TreeSet<Doc> docList = new TreeSet<Doc>();
			while(listIt.hasNext()) {
				docList.add(listIt.next().getDoc());
			}
			return docList;
		}
		return null;
	}
	
	public Set<String> getTerms(){	
		return this.entries.keySet();
	}
}
