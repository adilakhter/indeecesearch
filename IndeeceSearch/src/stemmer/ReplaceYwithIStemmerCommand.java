package stemmer;

public class ReplaceYwithIStemmerCommand extends StemmerCommandBase {

	final static String Y = "y";
	final static String I = "i";
	
	
	@Override
	public void Execute(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		if( !isCurrentBufferNotEmpty( currentBuffer , psIndexes)) return;
		if ( hasSuffix(Y, currentBuffer, psIndexes) && hasVowel(currentBuffer, psIndexes))
		{
			ReplaceSuffixTo(I, currentBuffer, psIndexes);
		}
	}

}
