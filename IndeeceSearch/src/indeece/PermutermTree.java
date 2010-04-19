package indeece;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import termGenerator.ITermGenerator;
import termGenerator.PermutermGenerator;

public class PermutermTree extends TreeMap<String,String>{
	
	private static final long serialVersionUID = 1L;
	
	public PermutermTree(Index index){
		super();
		Set<String> terms = index.getTerms();
		ITermGenerator generator = new PermutermGenerator();
		
		Iterator<String> i = terms.iterator();
		String currentTerm = "";
		String[] perms;
		
		while(i.hasNext()){
			currentTerm = i.next();
			perms = generator.generate(currentTerm);
			for(int j =0;j<perms.length;j++){
				this.put(perms[j], currentTerm);
			}
		}
	}
	
	public Set<String> getTerms(String query){
		TreeSet<String> retTerms = new TreeSet<String>();
		SortedMap<String,String> subtree = this.tailMap(query);
		Set<Map.Entry<String,String>> entries = subtree.entrySet();
		Iterator<Map.Entry<String,String>> i = entries.iterator();
		
		while(i.hasNext())
		{
			Map.Entry<String,String> current = i.next();
			if( current.getKey().startsWith(query))
			{
				retTerms.add(current.getValue());
			}
		}
	
		return retTerms;
	}
}
