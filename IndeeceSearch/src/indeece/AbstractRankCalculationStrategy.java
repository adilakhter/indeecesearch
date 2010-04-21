package indeece;

import java.util.HashMap;

import util.BinaryHeap;

public abstract class AbstractRankCalculationStrategy implements IRankCalculationStrategy {

	public abstract BinaryHeap caculateCosineScore(
			HashMap<String, Integer> queryTermFrequencyMapping,
			Index indexObject); 

	public abstract void calculateVectorNorms(Index indexObject);

}
