package stemmer;
import java.util.Iterator;
import java.util.LinkedList;
/*
 * Inspired by the Idea of Martin Porter's Stemming Algorithm. 
 * Description of the Algorithm: http://tartarus.org/~martin/PorterStemmer/def.txt 
 * Detail is available in http://tartarus.org/~martin/PorterStemmer/.
 * 
 * 
 * */


public class PorterStemmer implements IStemmer, java.io.Serializable {

	private char[] stemmerBuffer;
	private PorterStemmerIndexes stemmerIndexes = new PorterStemmerIndexes();
	private LinkedList<IStemmerCommand> commands = new LinkedList<IStemmerCommand>();
	
	public static int INITIAL_TERM_SIZE = 200;
	
	public PorterStemmer()
	{
		stemmerBuffer = new char[INITIAL_TERM_SIZE];
	
		setupStemmingCommands();
		
	}

	private String stripPrefixes(String str) {

		String[] prefixes = {"kilo", "micro", "milli", "intra", "ultra", "mega", "nano", "pico", "pseudo"};

		int last = prefixes.length;
		for (int i = 0; i < last; i++) {
			if (str.startsWith(prefixes[i])) {
				String temp = "";
				for (int j = 0; j < str.length() - prefixes[i].length(); j++)
					temp += str.charAt(j + prefixes[i].length());
				return temp;
			}
		}

		return str;
	}
	  
	private void setupStemmingCommands() {
		// setup commands 
		commands.add(new ReplacePluralAndEdOrINGStemmerCommand());
		commands.add(new ReplaceYwithIStemmerCommand());
		commands.add(new ReplaceDoubleSuffixToSingleStemmerCommand());
		commands.add(new ReplaceICorFULLorNESSStemmerCommand());
		commands.add(new ReplaceALorANTorENCEStemmerCommand());
		commands.add(new ReplaceFinalEStemmerCommand());
	}
	
	/*
	 * Stem the term passed in as parameter word
	 * */
	public String stemTerm(String word) {
		String stemmedWordToReturn = word;
		if( word  == null) return word ; // if the word is null , return immediately
		try
		{
			stemmedWordToReturn = cleanTerm(stemmedWordToReturn); // cleaning up the term as necessary
			setupBuffer(stemmedWordToReturn); // setting up the buffer 
			
			stemTerm(); // runs all the stemming command
			
			stemmedWordToReturn = new String(stemmerBuffer , 0 , stemmerIndexes.finalLength);
			stemmerIndexes.initStemmerIndexes(); // resetting the indexes to the initial stage 
		}
		catch ( Exception ex)
		{
			System.err.println("Failed to stem a word :" + stemmedWordToReturn + " Skipping it. Error: " + ex.getMessage() );
		}
		
		return stemmedWordToReturn;
	}
	
	
	private String cleanTerm(String word) {
		// remove the unnecessary prefixes
	    word = word.trim().toLowerCase();

	    String retString = "";
	    
	    // stripping unnecessary letter or digits
	    for (int i = 0; i <  word.length(); i++) {
	      if (Character.isLetterOrDigit(word.charAt(i)))
	      {
	    	  retString += word.charAt(i);
	      }
	    }
		
		retString = stripPrefixes(retString); 
		return 	retString;	   
	}

	/*
	 * Setting the word in the buffer and also setting up the _stemmerBufferLength
	 * 
	 * */	
	private void setupBuffer(String word)
	{
		stemmerIndexes.stemmerBufferLength = word.length();
		stemmerBuffer = new char[stemmerIndexes.stemmerBufferLength];
		
		for ( int i  = 0 ; i < stemmerIndexes.stemmerBufferLength  ; i++)
		{
			stemmerBuffer[i] = word.charAt(i); 
		}
	}
	
	
	private void stemTerm()
	{
		stemmerIndexes.currentIndex = stemmerIndexes.stemmerBufferLength - 1; // setting the index to the last index of the word
		stemmerIndexes.j = stemmerIndexes.currentIndex;
		
		if( stemmerIndexes.currentIndex  > 0)
		{
			Iterator<IStemmerCommand> i = commands.iterator();
		    while(i.hasNext()) {
		    	i.next().Execute(stemmerBuffer, stemmerIndexes);
		    }
		      
		}
		stemmerIndexes.finalLength = stemmerIndexes.currentIndex + 1;
	    stemmerIndexes.initStemmerIndexes();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Test Porter Stemmer
		
		try
		{
			String testTerm = "caresses";
			String expected = "caress";
			
			IStemmer stemmer = new PorterStemmer();
			String actual = stemmer.stemTerm(testTerm);
			
			System.out.println(actual + " \t " + expected);
		}catch ( Exception ex)
		{
			ex.printStackTrace();
		}

	}

}
