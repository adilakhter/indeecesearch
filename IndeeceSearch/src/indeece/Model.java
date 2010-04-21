package indeece;

import java.util.Collection;
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
	public abstract Collection<Result> search(String query) throws Exception;
	
	// inner class: abstraction for a search result.
	public class Result implements Comparable<Result>
	{
		private Doc		doc = null;
		private float	score = 0;
		

		public Result(Doc d, float score)
		{
			this.doc	= d;
			this.score	= score;
		}
		
		public Result(Doc d)
		{
			this(d, 0);
		}
		
		public float getScore()
		{
			return this.score;
		}

		@Override
		public int compareTo(Result other) 
		{
			//System.out.println("Here"+ this.score +"  "+ (double)-((this.score - other.score)));
			return (int)-(100*(this.score - other.score));
		}
		
		public String toString()
		{
			return this.doc.getTitle()+ ((Indeece.isActiveModel("Vector"))?" (score: "+this.score+")":"");
		}

		private Model getOuterType() {
			return Model.this;
		}
	}
}
