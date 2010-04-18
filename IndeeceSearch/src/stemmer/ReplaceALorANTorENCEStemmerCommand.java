package stemmer;

public class ReplaceALorANTorENCEStemmerCommand extends StemmerCommandBase{

    private static final String[][] SUFFIXES = 
    	  			{{"al", ""}, //0
    		        {"ance", ""},	//1
    		        {"ence", ""},//2
    		        {"er", ""},//3
    		        {"ic", ""},//4
    		        {"able", ""},//5
    		        {"ible", ""},//6
    		        {"ant", ""},//7
    		        {"ement", ""},//8
    		        {"ment", ""},//9
    		        {"ent", ""}//10
    		        ,{"ion", ""}//11
    		        ,{"ou", ""}//12
    		        ,{"ism", ""}//13
    		        ,{"ate", ""}//14
    		        ,{"iti", ""}//15
    		        ,{"ous", ""}//16
    		        ,{"ive", ""}//17
    		        ,{"ize", ""}//18
		        };

	
	public ReplaceALorANTorENCEStemmerCommand() {
		super();
	}

	@Override
	public void Execute(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		if( !isCurrentBufferNotEmpty( currentBuffer , psIndexes)) return;
		
		if( psIndexes.currentIndex ==0)
		{
			return;
		}
		
		int suffixIndex = 0;
		switch( currentBuffer[psIndexes.currentIndex-1])
		{
			case 'a':
				suffixIndex = 0;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'c':
				suffixIndex = 1;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'e':
				suffixIndex = 3;
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
			case 'n':
				suffixIndex = 7;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			
			case 'o':
				suffixIndex = 11;
				/*special handling */
				if( hasSuffix(SUFFIXES[suffixIndex][0], currentBuffer, psIndexes) && 
					(psIndexes.j>=0) &&
					( (currentBuffer[psIndexes.j] == 's') ||(currentBuffer[psIndexes.j] == 't'))
				
				)
				{
					if ( measure( currentBuffer , psIndexes ) > 1)
					{
						ReplaceSuffixTo(SUFFIXES[suffixIndex][1], currentBuffer, psIndexes);
					}
					else
					{
						psIndexes.j = psIndexes.currentIndex; // updating the current index as we are not replacing anything
					}
				}

				suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 's':
				suffixIndex = 13;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			
			case 't':
				suffixIndex = 14;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			
			case 'u':
				suffixIndex = 16;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			
			case 'v':
				suffixIndex = 17;
				VerifyMeasureAndReplace(currentBuffer, psIndexes, suffixIndex); suffixIndex++;
				break;
			case 'z':
				suffixIndex = 18;
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
			if ( measure( currentBuffer , psIndexes ) > 1)
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
