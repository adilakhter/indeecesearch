package indeece;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import termGenerator.ITermGenerator;
import termGenerator.PermutermGenerator;

public class PermutermTree extends TreeMap<String,String>{
	
	private static final long serialVersionUID = 1L;
	
	//Creates the tree containing all the permutations of each term of the 'index'
	public PermutermTree(Index index){
		super();
		Set<String> terms = index.getTerms();
		ITermGenerator generator = new PermutermGenerator();
		
		Iterator<String> i = terms.iterator();
		String currentTerm = "";
		String[] perms;
		int k=1;
		while(i.hasNext()){
			currentTerm = i.next();
			perms = generator.generate(currentTerm);
			if(perms!=null) {
				for(int j =0;j<perms.length;j++){
					this.put(perms[j], currentTerm);				
				}
			}
		}
	}
	
	//Returns a set with the original terms of that (permutated) query
	public Vector<String> getTerms(String query){
		Vector<String> retTerms = new Vector<String>();
		Map.Entry<String, String> rootEntry = this.ceilingEntry(query);
		
		while(rootEntry.getKey().startsWith(query))
		{
			if ( !retTerms.contains(rootEntry.getValue()))
		    {
		        //  i.e. we found the wild card terms that we were looking for
		        retTerms.add(rootEntry.getValue());
		    }
			rootEntry = this.higherEntry(rootEntry.getKey());
		}
	
		return retTerms;
	}
}
