package indeece;

import filter.IFilter;
import filter.PermutermQueryProcessor;
import filter.StopwordFilter;

import java.util.HashMap;
import java.util.HashSet;
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
	
	//Query transformation for wildcard searching
	private static PermutermQueryProcessor queryPr = new PermutermQueryProcessor();

	//A list of entries forms the Index
	//private ArrayList<Entry> entries;
	private HashMap<String,PostingList> entries = new HashMap<String,PostingList>();
	
	// stemmer
	private static IStemmer stemmer = new PorterStemmer();
	//private static IFilter stopwordFilter = new StopwordFilter();
	private static IFilter stopwordFilter = null;
	private boolean stemming;
	
	private int indexedDocsNumber;
	
	//Create the index of the given corpus
	public Index(Set<Doc> corpus, boolean stemming, boolean permuterm)
	{
		this.stemming = stemming;
		indexedDocsNumber = corpus.size();
		Iterator<Doc> i = corpus.iterator();
		int current=1;
		while(i.hasNext())
		{			
			if(current%50==0)
			System.out.println("Indexing "+ current +" out of " + indexedDocsNumber);
			current++;
			this.addDoc(i.next());		
		}
		if ( Indeece.getCosineStrategy() == null)
			calculateVectorNorms();
		else
			Indeece.getCosineStrategy().calculateVectorNorms(this); 
	}
	
	private void calculateVectorNorms() {
		System.out.print("Calculating vector norms...");
		HashSet<PostingList> indexSet =  new HashSet<PostingList>(entries.values());
		Iterator<PostingList> indexIt = indexSet.iterator();
		Iterator<PostingList.Item> plIter;
		PostingList postingList;
		PostingList.Item currentItem;
		float tf,idf,weight;
		
		while(indexIt.hasNext()) {
			postingList = indexIt.next();

			//Calculate term idf
			int plSize = postingList.size();
			idf = (float) Math.log10((double)indexedDocsNumber/postingList.size());

			postingList.setTermIdf(idf);
			
			plIter = postingList.iterator();
			while(plIter.hasNext()) {
				currentItem = plIter.next();
				tf =  (float) (1 + Math.log10((double)currentItem.getFrequency()));
				weight = tf*idf;
				currentItem.getDoc().addToNorm(weight);
			}
		}
		
		//Finalize vector norm by setting it to its square root
		Iterator<Doc> docIt = Indeece.getCorpus().iterator();
		while(docIt.hasNext()) {
			docIt.next().finalizeVectorNorm();
		}
		System.out.println("DONE");
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
		if(word.length() <1)
			return preprocessed;
		
		// remove stopwords
		if ( stopwordFilter != null)
		{
			if(stopwordFilter.Filter(term) == null)
				return preprocessed;
		}
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
			
			//Check if a middle term has been returned, which means we have a query  -->  A*B*C
			if (queryPr.getPostProcessingQueryString() != ""){
				String middleFix = queryPr.getPostProcessingQueryString();
				/*Prefix is stored in preAndpostFixes[1] and postfix in preAndpostFixes[0]
				  because from query  A*B*C we get from filter C$A */
				String preAndpostFixes[] = term.split("[$]");
				Vector<String> toBeRemoved = new Vector<String>();
				preprocessed = Indeece.getPermutermTree().getTerms(term);
				String current,stripped;
				Iterator<String> it = preprocessed.iterator();
				
				while(it.hasNext())
				{
					current = it.next();
					stripped = "";
					//strip prefix
					if (current.startsWith(preAndpostFixes[1])){
						stripped = current.replace(preAndpostFixes[1], "");
					}
					//strip postfix
					if (current.endsWith(preAndpostFixes[0])){
						stripped = current.replace(preAndpostFixes[0], "");
					}
					//Check if it contains middleFix and if yes, remove current from retTerms
					if (!stripped.matches(middleFix)){
						toBeRemoved.add(current);						
					}
				}
				if (toBeRemoved.size() > 0)
					preprocessed.removeAll(toBeRemoved);
			}else{
				//Otherwise we have a normal query that does not require special handling
				preprocessed = Indeece.getPermutermTree().getTerms(term);
			}
			
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
	
	public int getIndexedDocsNumber() {
		return indexedDocsNumber;
	}
	
	public int getDocFrequency(String term) {
		return getPostingList(term).size();
	}

	public HashMap<String,PostingList> getIndexedTerms()
	{
		return entries;
		
	}
}
