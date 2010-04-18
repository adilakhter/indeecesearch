package stemmer;

import java.util.Hashtable;


public class ReplacePluralAndEdOrINGStemmerCommand extends StemmerCommandBase
{
	Hashtable<String ,String> mappings = new Hashtable<String , String>();
	static final String IES = "ies";
	static final String AT = "at";
	static final String BL = "bl";
	static final String IZ = "iz";
	static final String E = "e";
	
	/* This command gets rid of plural and -ed or -ing. For example,
	 * 
		case - 1
		    caresses  ->  caress
		    ponies    ->  poni
		    ties      ->  ti
		    caress    ->  caress
		    cats      ->  cat
		----------------------------------- 
		case - 2 
		    feed      ->  feed
		    agreed    ->  agree
		    disabled  ->  disable
		--------------------------------
		case - 3
		    matting   ->  mat
		    mating    ->  mate
		    meeting   ->  meet
		    milling   ->  mill
		    messing   ->  mess
		----------------------------------
		case - 4
		    meetings  ->  meet

	 */
	public ReplacePluralAndEdOrINGStemmerCommand() {
		super();
		
		mappings.put(IES, "i");
		mappings.put(AT, "ate");
		mappings.put(BL, "ble");
		mappings.put(IZ, "ize");
	}

	@Override
	public void Execute(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		
		if( !isCurrentBufferNotEmpty( currentBuffer , psIndexes)) return;
		
		//case -1 
		if( currentBuffer[psIndexes.currentIndex] == 's')
		{
			if(hasSuffix("sses", currentBuffer, psIndexes)) 
			{
				psIndexes.decrementCurrentIndex(2); 
			}
			else if(hasSuffix(IES, currentBuffer, psIndexes)) 
			{
				ReplaceSuffixTo(mappings.get(IES), currentBuffer, psIndexes);
			} 
			else if (!hasSuffix("ss", currentBuffer, psIndexes))
			{
				psIndexes.decrementCurrentIndex(1);
			}
		}
		
		// case - 2
		if ( hasSuffix("eed", currentBuffer, psIndexes) )
		{
			if ( measure ( currentBuffer, psIndexes) > 0)
			{
				psIndexes.decrementCurrentIndex(1);
			}
		}
		else if( (hasSuffix("ed", currentBuffer, psIndexes)  || hasSuffix("ing", currentBuffer, psIndexes)) && hasVowel( currentBuffer , psIndexes))
		{
			psIndexes.currentIndex = psIndexes.j; // updating the current index to further check for suffixes. works like a lookahead. 
			
			if( hasSuffix( AT , currentBuffer, psIndexes))
				ReplaceSuffixTo(mappings.get(AT), currentBuffer, psIndexes);
			else if ( hasSuffix( BL , currentBuffer, psIndexes) )
				ReplaceSuffixTo(mappings.get(BL), currentBuffer, psIndexes);
			else if ( hasSuffix( IZ , currentBuffer, psIndexes) )
				ReplaceSuffixTo(mappings.get(IZ), currentBuffer, psIndexes);
			else if ( hasDoubleConsonants(currentBuffer , psIndexes.currentIndex))
			{
				//handling the stripping of  matting   ->  mat
				char theOtherConsonants = currentBuffer[psIndexes.currentIndex - 1];
				boolean isCharecterEqualsLorSorZ = isEqual(theOtherConsonants , 'l')|| isEqual(theOtherConsonants , 's')|| isEqual(theOtherConsonants , 'z');  
				if( !isCharecterEqualsLorSorZ )
				{
					psIndexes.decrementCurrentIndex(1); // stripping double consonants
				}
			}
			else if ( measure(currentBuffer, psIndexes) ==1  && isConstantVowelConstantSequence(psIndexes.currentIndex , currentBuffer))
			{
				// Handle string such as hoping => hope
				ReplaceSuffixTo(E, currentBuffer, psIndexes);
			}
		}
		
		
	}

	//TODO : Refactor to separte commands
	//TODO : make the method signature consistent
	//TODO : remove all the literals + use reusable constants
	
		
}