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
	private String additionalPreprocessingString = "";
	public String getPostProcessingQueryString()
	{
		return additionalPreprocessingString;
	}
	
	@Override
	public String Filter(String query) 
	{
		additionalPreprocessingString = "";
		String retString = query.trim();
		boolean containsAsterik = query.contains("*");
		
		if ( containsAsterik)
		{
			String [] terms = query.split("[\\*\\s]");
			
			if ( terms.length >1)
			{
				if ( query.startsWith("*") && query.endsWith("*") )
				{
					//handling *x*
					retString =  terms[1];
				}
				else
				{
					// cor*ll >> ll$cor*
					// gib*lt*r >> gib*r >> r$gib*
					 retString = terms[terms.length -1] + "$" + terms[0];
					 if (terms.length -2>0)
					 {
						 additionalPreprocessingString = terms[terms.length -2];
					 }
				}
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
