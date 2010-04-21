package indeece;

import java.util.HashMap;

import util.BinaryHeap;

public abstract class AbstractRankCalculationStrategy implements ICosineRankCalculationStrategy {

	
	public abstract BinaryHeap caculateCosineScore(
			HashMap<String, Integer> queryTermFrequencyMapping,
			Index indexObject); 

	public abstract void calculateVectorNorms(Index indexObject);

	protected float getQueryTermIdf(String term , Index indexObject)
	{
		HashMap < String , PostingList> termDictionary = indexObject.getIndexedTerms();
		if ( termDictionary.containsKey(term) )
		{
			return termDictionary.get(term).getTermIdf(); 
		}
		else
		{
			return 0;
		}
	}
	
}
