package stemmer;

public class ReplaceDoubleSuffixToSingleStemmerCommand extends StemmerCommandBase{

    private static final String[][] SUFFIXES = {
    		{"ational", "ate"}, //0
            {"tional", "tion"}, //1
            {"enci", "ence"},//2
            {"anci", "ance"},//3
            {"izer", "ize"},//4
            {"iser", "ize"},//5
            {"abli", "able"},//6
            {"alli", "al"},//7
            {"entli", "ent"},//8
            {"eli", "e"},//9
            {"ousli", "ous"},//10
            {"ization", "ize"},//11
            {"isation", "ize"},//12
            {"ation", "ate"},//13
            {"ator", "ate"},//14
            {"alism", "al"},//15
            {"iveness", "ive"},//16
            {"fulness", "ful"},//17
            {"ousness", "ous"},//18
            {"aliti", "al"},//19
            {"iviti", "ive"},//20
            {"biliti", "ble"}};//21

	
	public ReplaceDoubleSuffixToSingleStemmerCommand() {
		super();
	}

	@Override
	public void Execute(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		if( !isCurrentBufferNotEmpty( currentBuffer , psIndexes)) return;
		
		int suffixIndex = 0;
		switch( currentBuffer[psIndexes.currentIndex - 1])
		{
			case 'a':
				suffixIndex = 0;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'c':
				suffixIndex = 2;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'e':
				suffixIndex = 4;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'l':
				suffixIndex = 6;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'o':
				suffixIndex = 11;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				
				break;
			case 's':
				suffixIndex = 15;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 't':
				suffixIndex = 19;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
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
