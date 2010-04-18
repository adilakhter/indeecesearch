package indeece;
import java.util.HashSet;
import java.util.Iterator;

public class Tokenizer extends HashSet<String> {

	public Tokenizer(String body)
	{
		super();
		String words[] = body.split("\\s");
		for(int i=0; i < words.length; i++) {		
			String newTerm = makeTerm(words[i]);
			if(newTerm != null)
				this.add(newTerm);
		}
	}
	
	private String makeTerm(String word)
	{
		// Implement stemming, permuterm, rank optimization, etc ..
		return word;
	}
	
	public static void main(String args[])
	{
		Tokenizer t = new Tokenizer("hello world hello to you to!");
		
		for(Iterator<String> it = t.iterator(); it.hasNext(); ) {
			System.out.println("term: " + it.next());
		}
	}

}
