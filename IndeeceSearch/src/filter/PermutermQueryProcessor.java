package filter;
public class PermutermQueryProcessor implements IFilter {

	@Override
	public String[] Filter(String[] terms) {
		String [] retStrings = new String[terms.length];
		int index =0 ;
		for( String str : terms)
		{
			retStrings[index++] = Filter( str);
		}
		return retStrings;
	}
	
	@Override
	public String Filter(String query) 
	{
		String retString = query.trim();
		boolean containsAsterik = query.contains("*");
		
		if ( containsAsterik)
		{
			String [] terms = query.split("\\*");
			
			if ( terms.length >1)
			{
				// cor*ll >> ll$cor*
				// gib*lt*r >> gib*r >> r$gib*
				 retString = terms[terms.length -1] + "$" + terms[0];
			}
			else
			{
				//handling x* => $x*
				retString = "$" + terms[0];
			}	
		}
		else
		{
			retString = retString + "$";
		}
		return retString;
	}

}
