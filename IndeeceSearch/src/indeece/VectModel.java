package indeece;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.TreeSet;

public class VectModel extends Model {

	private boolean	highIdfOpt	= false;
	
	public VectModel(Index index, boolean highIdfOpt)
	{
		super(index);
		this.highIdfOpt = highIdfOpt;
	}
	
	@Override
	public Set<Result> search(String rawQuery) 
	{
		String query = index.preprocess(rawQuery);
		if(query == null)
			return new HashSet<Result>();

		// record query terms and their idf values
		// in the idf_q dictionary.
		// also, record relevant documents in docsSet.
		HashMap<String, Float>	idf_q	= new HashMap<String, Float>();
		HashSet<Doc> 			docSet 	= new HashSet<Doc>();
		
		String	words[] = query.split(" ");
		for(int i=0; i < words.length; i++) {
			PostingList plist = index.getPostingList(words[i]);
			if(plist == null)
				continue;
			// record idf value.
			idf_q.put(words[i], idfCalc(words[i]));
			// record relevant documents.
			Iterator<PostingList.Item>	plItemIter = plist.iterator();
			while(plItemIter.hasNext())
				docSet.add(plItemIter.next().getDoc());
		}

		// prune terms with low idf if necessary.
		if(highIdfOpt)
			highIdfOptPrune(idf_q);
		
		// calculate cosine score and make a Result entry for each relevant document.
		TreeSet<Result>	results		= new TreeSet<Result>();
		Iterator<Doc>	docSetIter	= docSet.iterator();
		float score;
		while(docSetIter.hasNext()) {
			Doc d = docSetIter.next();
			if((score = cosineScore(d, idf_q)) == 0) {
				// might get zero score if we've prunned idf_q.
				continue;
			}
			results.add(new Result(d, score));
		}

		return results;
	}
	
	private void highIdfOptPrune(HashMap<String, Float> idf_q)
	{
		// TODO
	}
	
	private float idfCalc(String term)
	{
		// TODO
		return 0;
	}
	
	private float cosineScore(Doc d, HashMap<String, Float> idf_q)
	{
		// TODO
		return 0;
	}

}
