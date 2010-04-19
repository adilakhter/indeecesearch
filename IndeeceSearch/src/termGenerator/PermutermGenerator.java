package termGenerator;

import java.util.Vector;

public class PermutermGenerator implements ITermGenerator {

	@Override
	public String[] generate(String term) {
	
		String [] retPermuterms = null;
		if ( term.length() > 0)
		{
			term = term+ "$";
			Vector< String>  permuterms = new Vector<String>();
			permuterms.add(term);
			
			StringBuilder buffer = new StringBuilder (term);
			
			char currentCharecter; 
			while( (currentCharecter = buffer.charAt(0)) != '$')
			{
				buffer = buffer.deleteCharAt(0);
				buffer.append(currentCharecter);
				permuterms.add(buffer.toString());
			}
			retPermuterms = new String[permuterms.size()];
			permuterms.toArray(retPermuterms);
		}
		
		return retPermuterms;
	}
	
}
