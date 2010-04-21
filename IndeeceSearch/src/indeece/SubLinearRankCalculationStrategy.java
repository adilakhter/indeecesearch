package indeece;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.BinaryHeap;


public class SubLinearRankCalculationStrategy  extends AbstractRankCalculationStrategy {

	@Override
	public BinaryHeap caculateCosineScore(
			HashMap<String, Integer> queryTermFrequencyMapping,
			Index indexObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculateVectorNorms(Index indexObject) {
		HashMap<String , PostingList > entries = indexObject.getIndexedTerms();
		HashSet<PostingList> indexSet =  new HashSet<PostingList>(entries.values());
		Iterator<PostingList> indexIt = indexSet.iterator();
		Iterator<PostingList.Item> plIter;
		PostingList postingList;
		PostingList.Item currentItem;
		float tf,idf,weight;
		
		while(indexIt.hasNext()) {
			postingList = indexIt.next();

			//Calculate term idf
			idf = (float) Math.log10(indexObject.getIndexedDocsNumber()/postingList.size());
			postingList.setTermIdf(idf);
			
			plIter = postingList.iterator();
			while(plIter.hasNext()) {
				currentItem = plIter.next();
				tf =  (float) (1 + Math.log10((double)currentItem.getFrequency()));
				weight = tf*idf;
				currentItem.getDoc().addToNorm(weight);
			}
		}
		
		//Finalize vector norm by setting it to its square root
		Iterator<Doc> docIt = Indeece.getCorpus().iterator();
		while(docIt.hasNext()) {
			docIt.next().finalizeVectorNorm();
		}
		
	}


}
