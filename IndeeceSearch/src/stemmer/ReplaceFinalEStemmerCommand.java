package stemmer;

public class ReplaceFinalEStemmerCommand extends StemmerCommandBase{
    
	@Override
	public void Execute(char[] currentBuffer, PorterStemmerIndexes psIndexes) {
		
		if( !isCurrentBufferNotEmpty( currentBuffer , psIndexes)) return;
		
		if( currentBuffer[psIndexes.currentIndex] == 'e')
		{
			psIndexes.j = psIndexes.currentIndex; // updating the index
			int m = this.measure(currentBuffer, psIndexes);
			if( m > 1 || (!this.isConstantVowelConstantSequence(psIndexes.currentIndex -1, currentBuffer) && m ==1))
			{	
				psIndexes.decrementCurrentIndex(1);
			}	
		}
		
		if( (currentBuffer[psIndexes.currentIndex]  == 'l' || currentBuffer[psIndexes.currentIndex]  == 'd') && 
				this.hasDoubleConsonants(currentBuffer, psIndexes.currentIndex) && 
				this.measure(currentBuffer, psIndexes)>1
			)
			{
				psIndexes.decrementCurrentIndex(1);
			}
	}	
}
