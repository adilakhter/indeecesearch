package indeece;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.BinaryHeap;
import util.PriorityQueue.Position;

public abstract class AbstractRankCalculationStrategy implements ICosineRankCalculationStrategy {

	@Override
	public void calculateVectorNorms(Index indexObject) {
		HashMap<String , PostingList > entries = indexObject.getIndexedTerms();
		HashSet<PostingList> indexSet =  new HashSet<PostingList>(entries.values());
		float idf,weight;

		for (PostingList postingList: indexSet)
		{	
			idf = getIdf(indexObject.getIndexedDocsNumber(), postingList.size());
			
			postingList.setTermIdf(idf);
			for ( PostingList.Item currentItem : postingList)
			{	
				weight = getDocumentTermWeight( currentItem.getFrequency(), idf); // calculating the weight component for the current item
				currentItem.getDoc().addToNorm(weight);
			}
		}
		for ( Doc docIt: Indeece.getCorpus())
		{
			// finalizing the vector norm
			docIt.finalizeVectorNorm();
		}
	}

	public BinaryHeap caculateCosineScore(
			Model model , 
			HashMap<String, Integer> queryTermFrequencyMapping
	)
	{
		HashMap<Doc,Float> scoresMap = new HashMap<Doc,Float>();
		BinaryHeap resultHeap = new BinaryHeap();
		float termWeight = 0, queryWeightNorm = 0, docWeightNorm = 0;
		Iterator<String> termIter = queryTermFrequencyMapping.keySet().iterator();

		String currentTerm;
		//Iterate over all terms in the query
		while(termIter.hasNext()) {
			currentTerm = termIter.next();

			//Calculate the dot product of the query and the documents, and return the term's weight
			termWeight = updateDotProduct(model.index, scoresMap,currentTerm, queryTermFrequencyMapping.get(currentTerm));

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
			{
				System.out.println("ZERO WEIGHT NORM");
			}

			if(termWeight!=0)
			{	
				score = scoresMap.get(currentDoc) / (queryWeightNorm*docWeightNorm);
			}

			//Insert result into heap
			resultHeap.insert(model.CreateResult(currentDoc, score));
		}		
		return resultHeap;
	}

	private float updateDotProduct(
			Index  index, 
			HashMap<Doc, Float> scoresMap,
			String currentTerm, 
			int termFrequency ) {
		//Get the posting list of the current term
		PostingList currentTermList = index.getPostingList(currentTerm);   
		if(currentTermList==null)
		{
			//Term is not found in the Indexes
			return 0;
		}

		//Get the term Idf from the posting list
		float termIdf = currentTermList.getTermIdf();

		//Calculate the term component of the query 
		//float termWeight = (1 +  (float) Math.log10((double)termFrequency)) * termIdf ;
		float termWeight = getQueryTermWeight(termFrequency, termIdf);

		//float termWeight = (float)termFrequency * termIdf ;

		Iterator<PostingList.Item> docIter = currentTermList.iterator();

		Doc currentDoc;
		PostingList.Item item;
		float documentWeight, currentScore = 0;

		//Iterate over all documents that are in the query
		while(docIter.hasNext()) {
			item = docIter.next();
			currentDoc = item.getDoc();
			//documentWeight = (float) (1 + Math.log10((double)item.getFrequency())) * termIdf;
			//documentWeight = (float) item.getFrequency() * termIdf;

			documentWeight = getDocumentTermWeight (item.getFrequency() , termIdf);
			if(scoresMap.containsKey(currentDoc))
			{	
				currentScore = scoresMap.get(currentDoc);
			}

			//Update the score of each document that has the currentTerm
			scoresMap.put(currentDoc, currentScore + (documentWeight * termWeight) );
		}

		return termWeight; 
	}

	protected float getIdf(int totalNoOfDocuments, int documentFrequency) {
		try
		{
			return (float) Math.log10(totalNoOfDocuments/documentFrequency); //Calculate term idf
		}
		catch( Exception ex)
		{
			return 0f;
		}
	}

	protected  abstract float getQueryTermWeight( float termFrequency ,float termIdf);

	protected  abstract float getDocumentTermWeight( float termFrequency ,float termIdf);
}
