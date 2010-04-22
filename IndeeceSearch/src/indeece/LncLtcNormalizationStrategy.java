package indeece;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.BinaryHeap;

public class LncLtcNormalizationStrategy extends AbstractRankCalculationStrategy{

	@Override
	protected float getDocumentTermWeight(float termFrequency, float termIdf) {
		termFrequency = (float) ((double)1 + Math.log10((double)termFrequency));//Logarithmic term frequency
		termIdf = 1; // no term idf
		
		return termFrequency * termIdf;
	}

	@Override
	protected float getQueryTermWeight(float termFrequency, float termIdf) {
		termFrequency = (float) ((double)1 + Math.log10((double)termFrequency));//Logarithmic term frequency
		termIdf = termIdf; // term idf
		
		return termFrequency * termIdf;
	}
	

}
