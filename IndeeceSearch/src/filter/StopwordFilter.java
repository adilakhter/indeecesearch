package filter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

import common.NullString;

public class StopwordFilter implements IFilter {
	private static  String STOP_WORD_FILE  = "bin\\filter\\Stopword.txt"; 
	public static HashSet<String> stopWords = null;
	
	public static void loadStopWords()
	{
		if(stopWords == null)
		{	
			stopWords = new HashSet<String>();
		    String line;
		    try {
		      BufferedReader in = new BufferedReader(new FileReader(STOP_WORD_FILE));
		      while ((line = in.readLine()) != null) {
		        stopWords.add(line);
		      }
		      in.close();
		    }
		    catch (IOException e) {
		      System.out.println("\nCould not load stopwords file: " + STOP_WORD_FILE);
		      System.exit(1);
		    }
		}
	    
	}
	
	public StopwordFilter() {
		loadStopWords();
	}

	@Override
	public String[] Filter(String [] terms) 
	{
		Vector<String> retVector = new Vector<String>();
		for( String token : terms)
		{
			String result = this.Filter(token);

			if ( result != NullString.Value)
			{
				retVector.add(result);
			}
		}
		String [] termsToReturn = new String[retVector.size()]; 
		return retVector.toArray(termsToReturn);
	}
	
	
	@Override
	public String Filter(String term) {
		if( stopWords.contains(term.toLowerCase()) )
		{
			return NullString.Value;
		}
		return term;
	}

}
