package indeece;
import java.io.*;
import java.util.Iterator;

import org.antlr.runtime.RecognitionException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class MainCmdLine {

	public static void
	usage()
	{
		System.err.println("[-r [-o]] <-i <indexFile> | -d <docsDir> [-s]>");
		System.exit(1);
	}
	
	public static void 
	main(String[] args) throws IOException, RecognitionException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		OptionSet opts = (new OptionParser("i:d:r:os")).parse(args);
		Searcher searcher = null;
		// searcher options
		boolean stem	= false;
		boolean rank	= false;
		boolean rankopt = false;
		
		// set searcher options from the command line
		if(opts.has("o")) {
			if(!opts.has("r")) usage();
			rankopt = true;
		}
		if(opts.has("r"))
			rank = true;
		if(opts.has("s"))
			stem = true;

		if(opts.has("i")) {
			if(opts.has("d") || opts.has("s")) usage();
			if(!opts.hasArgument("f")) usage();
			// load index file from disk
			searcher = new Searcher(rank, rankopt, (String)opts.valueOf("i"));
		} else if(opts.has("d")) {
			if(opts.has("i")) usage();
			// create index file from directory
			searcher = new Searcher(rank, rankopt, stem, (String)opts.valueOf("d"));
		}
		if(searcher == null) usage();
		// catch Searcher() errors (TODO)
		
		// start user interaction
	/*	while(true)
		{
			// display prompt
			System.out.print("Query: ");
			// display results
			for(Iterator<Searcher.Result> it=searcher.search(input.readLine()).iterator(); it.hasNext(); )
				System.out.println(it.next());
		}
		*/
	}

}
