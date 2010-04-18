package stemmer;

public class ReplaceICorFULLorNESSStemmerCommand extends StemmerCommandBase{

    private static final String[][] SUFFIXES = 
    	  			{{"icate", "ic"}, //0
    		        {"ative", ""},	//1
    		        {"alize", "al"},//2
    		        {"alise", "al"},//3
    		        {"iciti", "ic"},//4
    		        {"ical", "ic"},//5
    		        {"ful", ""},//6
    		        {"ness", ""}//7
		        };

	
	public ReplaceICorFULLorNESSStemmerCommand() {
		super();
	}

	@Override
	public void Execute(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		if( !isCurrentBufferNotEmpty( currentBuffer , psIndexes)) return;
		
		int suffixIndex = 0;
		switch( currentBuffer[psIndexes.currentIndex])
		{
			case 'e':
				suffixIndex = 0;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'i':
				suffixIndex = 4;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'l':
				suffixIndex = 5;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 's':
				suffixIndex = 7;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			default :
				break;
		
		}
		
	}

	private void VerifyMeasureAndReplace(char[] currentBuffer,
			PorterStemmerIndexes psIndexes, int suffixIndex) {
		if ( hasSuffix( SUFFIXES[suffixIndex][0] , currentBuffer, psIndexes))
		{
			if ( measure( currentBuffer , psIndexes ) > 0)
			{
				ReplaceSuffixTo(SUFFIXES[suffixIndex][1], currentBuffer, psIndexes);
			}
			else
			{
				psIndexes.j = psIndexes.currentIndex; // updating the current index as we are not replacing anything
			}
		}
	}

}
