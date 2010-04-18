package indeece;
import grammar.ASTwalker;
import grammar.booleanGrammarLexer;
import grammar.booleanGrammarParser;
import grammar.booleanGrammarParser.prog_return;

import java.util.HashMap;
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
	
	public class Result implements Comparable<Result>
	{
		private Doc doc;
		private float score;
		public Result(Doc doc, float score)
		{
			this.doc = doc;
			this.score = score;
			// TODO
		}
		
		public Result(Doc doc)
		{
			this(doc, 0);
		}
		
		public int
		compareTo(Result other)
		{
			return (int)(this.score - other.score);
		}
		
		public String 
		toString()
		{
			// TODO
			return "result for: " + doc;
		}
	}
	private int kappa = 10;
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
	vectorSpaceSearch(Preprocessed query)
	{
		Index index = Indexer.getActiveIndex();
		HashSet<Doc> docs = new HashSet<Doc>();
		TreeSet<Result> results = new TreeSet<Result>();
		BinaryHeap heap = new BinaryHeap();
		HashMap<String, Float> tfidf_q = new HashMap<String, Float>();

		// gather set of documents that contain those terms
		for(Iterator<String> termit = query.iterator(); termit.hasNext(); ) {
			String t = termit.next();
			// save tf-idf for t,query
			tfidf_q.put(t, index.tfidfCalc(t, query));
			PostingList pl = index.getEntry(termit.next());
			for(Iterator<PostingList.Item> plit = pl.iterator(); plit.hasNext(); ) {
				docs.add(plit.next().getDoc());
			}
		}

		// for each document referenced by the given terms
		for(Iterator<Doc> docit = docs.iterator(); docit.hasNext(); ) {
			Doc d = docit.next();
			// for each of the given terms
			float score = 0;
			for(Iterator<String> termit = query.iterator(); termit.hasNext(); ) {
				String t = termit.next();
				score += index.tfidf(t, d) * tfidf_q.get(t);
			}
			score /= d.getLength();
			if(score != 0)
				heap.add(new Result(d, score));

		}
		
		for(int i=0;(i < kappa) && (!heap.isEmpty()); i++)
			results.add(heap.poll());
		return results;
	}
	
	public Set<Result>
	vectorSpaceSearchOpt(Preprocessed terms)
	{
		// TODO
		return vectorSpaceSearch(terms);
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
