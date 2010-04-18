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
	
	public class Result
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
	}
	private boolean rank;
	private boolean rankopt;
	public 
	Searcher(boolean rank, boolean rankopt, boolean stemming, String docsDir)
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
	
	// Returns results from boolean space search if ranking is disabled.
	// If ranking is enabled, return vector space search results.
	// If ranking optimization is enabled, return vector space resutls,
	// by only considering query terms with the highest idf(t) weight.
	public Set<Searcher.Result> 
	search(String query) throws RecognitionException
	{
		Preprocessed terms = new Preprocessed(query);
		if(rank && rankopt)
			return vectorSpaceSearchOpt(terms);
		else if(rank)
			return vectorSpaceSearch(terms);
		
		return booleanSpaceSearch(terms);
	}
	
	// Return the first kappa most relevant documents.
	// Each document is represented as a normalized vector
	public Set<Result>
	vectorSpaceSearch(Preprocessed terms)
	{
		Index index = Indexer.getActiveIndex();
		// gather set of documents that contain those terms
		HashSet<Doc> docs = new HashSet<Doc>();
		for(Iterator<String> termit = terms.iterator(); termit.hasNext(); ) {
			PostingList pl = index.getEntry(termit.next());
			for(Iterator<PostingList.Item> plit = pl.iterator(); plit.hasNext(); ) {
				docs.add(plit.next().getDoc());
			}
		}
			
		// TODO
		return null;
	}
	
	public Set<Result>
	vectorSpaceSearchOpt(Preprocessed terms)
	{
		// TODO
		return null;
	}
	
	public Set<Result>
	booleanSpaceSearch(Preprocessed terms)
	{
		/*
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
		*/
		return null;
	}
	
}
