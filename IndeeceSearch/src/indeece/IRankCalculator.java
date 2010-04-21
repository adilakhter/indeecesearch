package indeece;

import java.util.HashMap;

import util.BinaryHeap;

public interface IRankCalculator {

	public BinaryHeap CaculateRank( HashMap<String , Integer> queryTermFrequencyMapping , Index indexObject);
}
