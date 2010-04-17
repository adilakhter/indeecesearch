package indeece;
import java.util.Set;

public class Searcher {
	
	public class Result implements Comparable<Searcher.Result>
	{
		private float rank;
		
		public Result(float rank)
		{
			// TODO
			this.rank = rank;
		}
		
		public String 
		toString()
		{
			// TODO
			return "result with rank: "+this.rank;
		}
		
		public int 
		compareTo(Searcher.Result res)
		{
			// TODO
			return (int)(this.rank - res.rank);
		}
	}
	
	public 
	Searcher(boolean rank, boolean rankopt, boolean stem, String docsDir)
	{
		// construct indexer from directory (TODO)
	}
	
	public
	Searcher(boolean rank, boolean rankopt, String indexFile)
	{
		// unmarshall indexer from file (TODO)
	}
	
	public Set<Searcher.Result> 
	search(String query)
	{
		return null;
	}
	
	public Object 
	makeAST(String query)
	{
		// TODO
		return null;
	}
	
	public Object 
	parseAST(Object ast)
	{
		// TODO
		return null;
	}
	
	public Set<Searcher.Result> 
	rankResults(Object results)
	{
		// TODO
		return null;
	}
}
