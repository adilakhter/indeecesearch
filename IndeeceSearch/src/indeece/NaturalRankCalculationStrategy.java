package indeece;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.BinaryHeap;

// Natural TF , IDF and Cosine Ranking Strategy
public class NaturalRankCalculationStrategy extends AbstractRankCalculationStrategy {

	//calculates normal TFxIDF
	
	@Override
	protected float getDocumentTermWeight(float termFrequency, float termIdf) {

		return termFrequency * termIdf;
	}

	@Override
	protected float getQueryTermWeight(float termFrequency, float termIdf) {

		return getDocumentTermWeight( termFrequency , termIdf);
	}

}
