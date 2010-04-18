package stemmer;

public interface IStemmerCommand
{
	public void Execute(char [] currentBuffer, PorterStemmerIndexes psIndexes);
	
}