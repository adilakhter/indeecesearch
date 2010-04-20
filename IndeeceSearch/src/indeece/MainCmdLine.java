package indeece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class MainCmdLine
{
	public static void main(String args[])
	throws IOException
	{
		String pathName = null;
		// parse command line arguments.
		OptionSet opts = (new OptionParser("rb:v:os")).parse(args);
		if(!(opts.has("v") ^ opts.has("b")))
			usage();
		if(opts.has("r") && opts.has("w"))
			usage();
		
		if(opts.has("v")) 
			pathName = (String)opts.valueOf("v");
		else if(opts.has("b")) 
			pathName = (String)opts.valueOf("b");
		
		if(!opts.has("r")) {
			// create inverted index from corpusDir
			try {
				Indeece.createIndex(new CorpusBuilder(pathName), opts.has("s"), opts.has("p"));
				if(opts.has("w"))
					Indeece.storeIndeece(pathName);
			} catch(IOException e) {
				System.err.println("Error indexing "+pathName);
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			// reload inverted index from pathName
			Indeece.loadIndeece(pathName);
		}
		
		// create search model.
		Model model = null;
		if(opts.has("v"))
			model = new VectModel(Indeece.getActiveIndex(), opts.has("o"));
		else if(opts.has("b"))
			model = new BoolModel(Indeece.getActiveIndex());
		
		// start user interaction.
		BufferedReader 		input 	= new BufferedReader(new InputStreamReader(System.in));
		Set<Model.Result>	results	= null;
		
		while(true)
		{
			System.out.print("Enter query: ");
			
			// perform search
			try {
				results = model.search(input.readLine());
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			// print results
			Iterator<Model.Result> resultsIter = results.iterator();
			for(int i=1; resultsIter.hasNext(); i++)
			{
				System.out.println(i+"."+resultsIter.next());
			}
		}
	}
	
	private static void usage()
	{
		System.err.println("[-s] [-r|-w] <-b | -v [-o]> corpusDir");
		System.exit(1);
	}
}
