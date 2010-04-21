package indeece;

import java.util.HashMap;

import util.BinaryHeap;

public interface IRankCalculationStrategy
{
	public BinaryHeap caculateCosineScore( HashMap<String , Integer> queryTermFrequencyMapping , Index indexObject);
	public void calculateVectorNorms(Index indexObject);
}
