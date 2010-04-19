package stemmer;


public abstract class StemmerCommandBase implements IStemmerCommand
{
	/* isConstantVowelConstantSequence is true when index  i-2,i-1,i has the form consonant - vowel - consonant
	   and also if the second c is not w,x or y. this is used when trying to
	   restore an e at the end of a short word. e.g. cav(e), lov(e), hop(e), crim(e), but  snow, box, tray.
	*/
	protected boolean isConstantVowelConstantSequence(int index , char [] currentBuffer) 
	{
		if (index < 2 || !isConsonant(index, currentBuffer)|| isConsonant(index-1, currentBuffer) || !isConsonant(index-2,currentBuffer))
			return false;
		int ch = currentBuffer[index];
		// handling exception
		if (ch == 'w' || ch == 'x' || ch == 'y')
			return false;
		return true;
	}

	protected boolean isEqual ( char a , char b )
	{
		return a == b;
		
	}
	// Replace suffix to the following string "replaceString".
	// It does not bother about array indexes
	protected void ReplaceSuffixTo( String replaceString , char [] currentBuffer, PorterStemmerIndexes psIndexes)
	{
		int suffixStartingIndex= psIndexes.j + 1;
		
		for ( int i = 0 ;  i< replaceString.length() ;  i++)
		{
			currentBuffer[i + suffixStartingIndex] = replaceString.charAt(i);
		}
		psIndexes.currentIndex = psIndexes.j;	
		psIndexes.incrementCurrentIndex(replaceString.length());
	}
	/*
	 * Returns true if currentBuffer[index] is a consonant
	 * */
	private boolean isConsonant(int index , char [] currentBuffer ) 
	{
		switch (currentBuffer[index]) 
		{
			case 'a': case 'e': case 'i': case 'o': case 'u': return false;
			//The letter y is a consonant when it is the first letter of a syllable that has more than one letter. If y is anywhere else in the syllable, it is a vowel.
			case 'y': return (index==0) ? true : !isConsonant(index-1, currentBuffer);    
			default: return true;
		}
	}
	
	/*
	 * Verifies whether there is a vowel in the buffer
	 * */
	protected boolean hasVowel(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		
		for ( int index = 0 ; index<= psIndexes.j ; index++)
		{
			if( !isConsonant(index, currentBuffer))
				return true;
		}
		
		return false;
	}
	
	
	protected boolean hasDoubleConsonants( char[] currentBuffer, int indexToCheckForDoubleConstants)
	{
		if (indexToCheckForDoubleConstants < 1)
		{
			return false;
		}
		
		if ( isConsonant(indexToCheckForDoubleConstants , currentBuffer))
		{
			return currentBuffer[indexToCheckForDoubleConstants] == currentBuffer[indexToCheckForDoubleConstants-1];
		}
		
		return false;
	}
	
	/*measure() determines the number of consonant sequences between 0 and j. if c is
	   a consonant sequence and v a vowel sequence, and <..> indicates arbitrary
	   presence,

		  <c><v>       gives 0
		  <c>vc<v>     gives 1
		  <c>vcvc<v>   gives 2
		  <c>vcvcvc<v> gives 3
		  ....
	*/
	public int measure( char [] currentBuffer, PorterStemmerIndexes psIndexes)
	{
		int n = 0;
		int index = 0;
		// all the vowels  are tracked
		while(true) 
		{
			
			if (index > psIndexes.j) 
				return n;
			
			if (! isConsonant(index , currentBuffer)) 
				break; 
			index++;
		}
		index++;
		
		while(true) 
		{
			
			while(true) 
			{
				if (index > psIndexes.j) 
					return n;
				if (isConsonant(index, currentBuffer)) 
					break;
				index++;
			}
			index++;
			n++;
			while(true) 
			{
				if (index > psIndexes.j) 
					return n;
				if (!isConsonant(index, currentBuffer)) 
					break;
				index++;
			}
			index++;
		}
	}
	
	/*
	 * Verify whether the suffix is there in the word. If yes, then returns true.
	 * psIndexes.j  refers to the word without prefix.
	 * 
	 * */
	protected boolean hasSuffix(String suffix , char [] currentBuffer, PorterStemmerIndexes psIndexes)
	{
		int startingIndexOfSuffix = psIndexes.currentIndex - suffix.length() + 1;
		if( startingIndexOfSuffix < 0)
		{
			return false; // suffix is not found 
		}
		
		for ( int i = 0 ; i <suffix.length() ; i++ )
		{
			if( currentBuffer[i+startingIndexOfSuffix] != suffix.charAt(i))
			{
				return false;
			}
		}
		psIndexes.j = psIndexes.currentIndex - suffix.length(); // setting the current index to j after stripping the suffix from the buffer , theoritically
		
		return true;
	}
	
	protected boolean isCurrentBufferNotEmpty(char [] currentBuffer, PorterStemmerIndexes psIndexes)
	{
		return (currentBuffer.length  > 1) &&  (psIndexes.currentIndex >0) ;
	}
	
	public abstract void Execute(char [] currentBuffer, PorterStemmerIndexes psIndexes);
	
}
