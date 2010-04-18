package stemmer;

public class PorterStemmerIndexes
{
	public int stemmerBufferLength;
	public int finalLength;
	public int currentIndex;
	public int j;
	public PorterStemmerIndexes() {
		initStemmerIndexes();
	}

	// Setting the up the indexes 
	public void initStemmerIndexes()
	{
		stemmerBufferLength = 0;
		currentIndex = 0;
	}
	
	public synchronized void decrementCurrentIndex( int by)
	{
		currentIndex = currentIndex - by;
	}
	
	public synchronized void incrementCurrentIndex( int by)
	{
		currentIndex = currentIndex + by;
	}
}
