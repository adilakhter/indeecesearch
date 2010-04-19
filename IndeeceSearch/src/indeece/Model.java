package indeece;

import java.util.Set;

// abstraction for a search model (i.e. Probabilistic, Vector Space, Boolean)
public abstract class Model
{
	Index index = null;
	
	// constructor.
	public Model(Index index)
	{
		this.index = index;
	}
	
	// abstract search method.
	public abstract Set<Result> search(String query) throws Exception;
	
	// inner class: abstraction for a search result.
	public class Result
	{
		private Doc doc = null;
		
		public Result(Doc d)
		{
			this.doc = d;
		}
		
		public String toString()
		{
			return this.doc.getTitle();
		}
	}
}
