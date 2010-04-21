package indeece;

import java.util.HashMap;

import util.BinaryHeap;

public interface ICosineRankCalculationStrategy
{
	public BinaryHeap caculateCosineScore( Model model , HashMap<String , Integer> queryTermFrequencyMapping);
	public void calculateVectorNorms(Index indexObject);
}
