package indeece;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import util.BinaryHeap;

public class VectModel extends Model {

	private boolean	highIdfOpt	= false;
	
	public VectModel(Index index, boolean highIdfOpt)
	{
		super(index);
		this.highIdfOpt = highIdfOpt;
	}
	
	@Override
	public BinaryHeap search(String rawQuery) 
	{
		//HashMap holding the terms and their frequencies in the queries
		HashMap<String,Integer> queryTerms = preprocess(rawQuery);
		// prune terms with low idf if necessary.
		//Currently,low-idf terms are pruned completely but maybe we should just not accumulate scores for those terms but still get their posting lists. ???  (TODO)
		if(highIdfOpt)
			queryTerms = pruneLowIdfTerms(queryTerms);
		//Binary Heap to hold results
		BinaryHeap  resultsHeap = new BinaryHeap();
		
		if(queryTerms == null)
			return new BinaryHeap();
	
		resultsHeap = calculateRank(queryTerms);
		
		return resultsHeap;
	}
	
	private HashMap<String,Integer> preprocess(String rawQuery) {
		
		HashMap<String,Integer> termFrequencyMap = new HashMap<String,Integer>();
		String query = index.preprocess(rawQuery);
		
		String	words[] = query.split(" ");
		
		for(int i=0; i<words.length; i++) {
			if( termFrequencyMap.containsKey(words[i]))
				termFrequencyMap.put(words[i],termFrequencyMap.get(words[i])+1);
			else
				termFrequencyMap.put(words[i],1);
		}
		return termFrequencyMap;
	}

	private BinaryHeap calculateRank(HashMap<String,Integer> queryTerms)
	{
		if ( Indeece.getCosineStrategy() == null)
		{
			HashMap<Doc,Float> scoresMap = new HashMap<Doc,Float>();
			BinaryHeap resultHeap = new BinaryHeap();
			float termWeight = 0,
			queryWeightNorm = 0,
			docWeightNorm = 0;
			String currentTerm;

			Iterator<String> termIter = queryTerms.keySet().iterator();

			//Iterate over all terms in the query
			while(termIter.hasNext()) {
				currentTerm = termIter.next();

				//Calculate the dot product of the query and the documents, and return the term's weight
				termWeight = updateDotProduct(scoresMap,currentTerm,queryTerms.get(currentTerm));

				//Update the query weight norm
				queryWeightNorm += (float) Math.pow(termWeight,2.0);
			}

			//This will be the final query Weight norm
			queryWeightNorm = (float) Math.sqrt(queryWeightNorm);


			Iterator<Doc> relevantDocIter = scoresMap.keySet().iterator();
			Doc currentDoc;
			float score = 0;

			while(relevantDocIter.hasNext()) {
				currentDoc = relevantDocIter.next();
				docWeightNorm = currentDoc.getVectorNorm();
				if(docWeightNorm==0)
					System.out.println("ZERO WEIGHT NORM");
				if(termWeight!=0)
					score = scoresMap.get(currentDoc) / (queryWeightNorm*docWeightNorm);

				//Insert result into heap
				resultHeap.insert(new Model.Result(currentDoc,score));

			}		
			return resultHeap;
		}else
		{
			return Indeece.getCosineStrategy().caculateCosineScore(this, queryTerms);
		}
	}
	
	private float updateDotProduct(HashMap<Doc, Float> scoresMap,
			String currentTerm, int termFrequency) {
		//Get the posting list of the current term
		PostingList currentTermList = Indeece.getActiveIndex().getPostingList(currentTerm);   
		if(currentTermList==null)
			return 0;

		//Get the term Idf from the posting list
		float termIdf = currentTermList.getTermIdf();
		
		
		//Calculate the term component of the query 
		float termWeight = (1 +  (float) Math.log10((double)termFrequency)) * termIdf ;
		//float termWeight = (float)termFrequency * termIdf ;
		
		Iterator<PostingList.Item> docIter = currentTermList.iterator();
		Doc currentDoc;
		PostingList.Item item;
		float documentWeight,
		      currentScore = 0;
		
		//Iterate over all documents that are in the query
		while(docIter.hasNext()) {
			item = docIter.next();
			currentDoc = item.getDoc();
			documentWeight = (float) (1 + Math.log10((double)item.getFrequency())) * termIdf;
			//documentWeight = (float) item.getFrequency() * termIdf;
			if(scoresMap.containsKey(currentDoc))	
				currentScore = scoresMap.get(currentDoc);
			
			//Update the score of each document that has the currentTerm
			scoresMap.put(currentDoc, currentScore + (documentWeight * termWeight) );
		}
		
		return termWeight; 
	}
	
	//Returns only the high idf terms based on the idfThreshold 
    private  HashMap<String,Integer> pruneLowIdfTerms(HashMap<String,Integer> queryTerms)
    {
	     //The threshold on which low-idf terms in the query are pruned
	     double idfThreshold = 2.0;
	     //Number of retries with lower idfTrheshold in case of pruning all terms of the query
	     int retryEfforts = 3;
	     
	     Set<String> terms =  queryTerms.keySet();
	     Iterator<String> i;
	     HashMap<String,Integer> retTerms = new HashMap<String,Integer>();
	     
	     while(retTerms.isEmpty())
	     {
	    	 System.out.println("Threshold is: "+idfThreshold);
	    	//Clone is needed to avoid concurrent modification error
		     retTerms = (HashMap<String,Integer>) queryTerms.clone();
		     String current = "";
		     i = terms.iterator();
		     while(i.hasNext())
		     {
		         current = i.next();
		
		         if (Indeece.getActiveIndex().getPostingList(current).getTermIdf() < idfThreshold)
		         {
		              retTerms.remove(current);
		         }
		     }
		     //While all terms are pruned and there are remaining retryEfforts
		     if(retTerms.isEmpty() && retryEfforts > 0)
		     {
		    	 //If there are more than 1 remaining retry efforts cut threshold in half and repeat else (last try) make threshold zero
		    	 if(retryEfforts > 1)
		    	 {
		    		 idfThreshold = idfThreshold/2;
		    		 retryEfforts--;
		    	 }else{
		    		 idfThreshold = 0.0;
		    	 }				    	 
		     }
		     
	     }
	     return retTerms;
    }
}
