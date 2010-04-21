package indeece;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.BinaryHeap;

//Sub-linear TF , IDF , Cosine Ranking Strategy

public class SubLinearRankCalculationStrategy  extends AbstractRankCalculationStrategy {

	//CALCULATED SUBLINEAR TFxIDF. Equation 6.13 in Chapter 6 

	@Override
	protected float getDocumentTermWeight(float termFrequency, float termIdf) {
		return (float) (1 + Math.log10((double)termFrequency)) * termIdf;
	}

	@Override
	protected float getQueryTermWeight(float termFrequency, float termIdf) {
		return getDocumentTermWeight(termFrequency , termIdf );
	}


}
