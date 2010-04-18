package indeece;
import grammar.ASTwalker;
import grammar.booleanGrammarLexer;
import grammar.booleanGrammarParser;
import grammar.booleanGrammarParser.prog_return;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Iterator;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;


public class Searcher {
	
	public class Result implements Comparable<Searcher.Result>
	{
		private Doc doc;
		
		public Result(Doc doc)
		{
			this.doc = doc;
			// TODO

		}
		
		public String 
		toString()
		{
			// TODO
			return "result for: " + doc;
		}
		
		public int 
		compareTo(Searcher.Result res)
		{
			// TODO
			return 0;
		}
	}
	private boolean rank;
	private boolean rankopt;
	public 
	Searcher(boolean rank, boolean rankopt, boolean stem, String docsDir)
	{
		this.rank = rank;
		this.rankopt = rankopt;
		// construct indexer from directory (TODO)
	}
	
	public
	Searcher(boolean rank, boolean rankopt, String indexFile)
	{
		this.rank = rank;
		this.rankopt = rankopt;
		// unmarshall indexer from file (TODO)
	}
	
	public Set<Searcher.Result> 
	search(String query) throws RecognitionException
	{
		CharStream charStream = new ANTLRStringStream(query);
	    booleanGrammarLexer lexer = new booleanGrammarLexer(charStream);
	    TokenStream tokenStream = new CommonTokenStream(lexer);
		booleanGrammarParser parser = new booleanGrammarParser(tokenStream);
		
		//Build AST
		prog_return AST = parser.prog();
		
		//Get AST nodes
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(AST.getTree());
		
		//Create an AST traversal tool
		ASTwalker walker = new ASTwalker(nodeStream);
		
		Set<Doc> docs = walker.prog();
		Set<Searcher.Result> results;
		if(rank)
			results = new TreeSet<Searcher.Result>();
		else
			results = new HashSet<Searcher.Result>();
		
		// ranking .. 
		for(Iterator<Doc> it=docs.iterator(); it.hasNext(); )
			results.add(new Searcher.Result(it.next()));
			
		return results;
	}
	
	public TreeSet<Searcher.Result>
	rankResults(Set<Searcher.Result> results)
	{
		return new TreeSet<Searcher.Result>(results);
	}
	
}
