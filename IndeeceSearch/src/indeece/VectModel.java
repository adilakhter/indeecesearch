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
		System.err.println("query: " + query);
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
			System.err.println("plist("+words[i]+"): ");
			while(plItemIter.hasNext()) {
				Doc d = plItemIter.next().getDoc();
				System.err.println("-> "+d);
				docSet.add(d);
			}
		}
		
		// prune terms with low idf if necessary.
		if(highIdfOpt) {
			// TODO
		}
		
		// calculate cosine score and make a Result entry for each relevant document.
		TreeSet<Result>	results		= new TreeSet<Result>();
		Iterator<Doc>	docSetIter	= docSet.iterator();
		while(docSetIter.hasNext()) {
			Doc d = docSetIter.next();
			results.add(new Result(d, cosineScore(d, idf_q)));
		}
		
		return results;
	}
	
	private float idfCalc(String term)
	{
		return 0;
	}
	
	private float cosineScore(Doc d, HashMap<String, Float> idf_q)
	{
		return 0;
	}

}
