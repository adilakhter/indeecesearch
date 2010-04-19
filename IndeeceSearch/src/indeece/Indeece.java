package indeece;

import java.util.Set;

public class Indeece {
	private static Index index;
	private static CorpusBuilder corpus;
	
	public static void createIndex(CorpusBuilder cBuilder) {
		corpus=cBuilder;
		index = new Index(corpus.getCorpus());
	}
	
	public static Index getActiveIndex() {
		return index;
	}
	
	public static Set<Doc> getCorpus() {
		return corpus.getCorpus();
	}

}
