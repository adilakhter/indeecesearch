package indeece;
import java.util.HashSet;
import java.util.Iterator;

import stemmer.IStemmer;
import stemmer.PorterStemmer;


public class Preprocessed extends HashSet<String> {
	
	IStemmer stemmer = new PorterStemmer(); 
	
	public Preprocessed(String body)
	{
		super();
		// comma, dot, semicolon, etc..
		String words[] = body.split("[\\s;,.;]");
		for(int i=0; i < words.length; i++) {		
			String newTerm = makeTerm(words[i]);
			if(newTerm != null)
				this.add(newTerm);
		}
	}
	
	private String makeTerm(String word)
	{
		// Implement stemming, permuterm (TODO)
		return word;
	}
	
	public String stemmedTerm(String word)
	{
		return stemmer.stemTerm(word);
	}
	
	public String toString()
	{
		String ret = new String("");
		for(Iterator<String> termit=iterator(); termit.hasNext(); )
			ret = ret + " " + termit.next();
		
		return ret;
	}
	
	public static void main(String args[])
	{
		Preprocessed t = new Preprocessed("hello world hello.to,you;to!");
		
		for(Iterator<String> it = t.iterator(); it.hasNext(); ) {
			System.out.println("term: " + it.next());
		}
	}

}
