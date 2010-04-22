package indeece;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class Indeece 
{
	private static Index index;
	private static Set<Doc> corpus;
	private static PermutermTree permutermTree;
	private static ICosineRankCalculationStrategy consineCalculationStrategy = null;
	private static Model activeModel;
	private static Model BooleanModel;
	private static Model VectModel;
	//Get K results
	private static int K = 10;
	public static HashMap<String , ICosineRankCalculationStrategy>  rankingStrategymappings = new HashMap<String , ICosineRankCalculationStrategy>();
	
	private static final String Natural_Ranking =  "Natural Tf";
	private static final String Sublinear_Ranking =  "Sublinear Tf";
	private static final String LNC_LTC_Ranking  = "Lnc.Ltc";
	
	static 
	{
		rankingStrategymappings.put(Natural_Ranking, new NaturalRankCalculationStrategy());
		rankingStrategymappings.put(Sublinear_Ranking, new SubLinearRankCalculationStrategy());
		rankingStrategymappings.put(LNC_LTC_Ranking, new LncLtcNormalizationStrategy());
	}
	
	
	public static void createIndex(CorpusBuilder cBuilder, boolean stemming, boolean permuterm) {
		corpus = cBuilder.getCorpus();
		index  = new Index(corpus, stemming, permuterm);
		permutermTree = new PermutermTree(index);
	}
	
	public static Index getActiveIndex() {
		return index;
	}
	
	public static Set<Doc> getCorpus() {
		return corpus;
	}
	
	public static PermutermTree getPermutermTree(){
		return permutermTree;
	}

	public static void storeIndeece(String fileName){
		//Serialize Index under the name "fileName.dat"
		try{
			FileOutputStream fileout = new FileOutputStream(fileName + ".dat");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(index);
			out.close();
			fileout.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}
		//Serialize Corpus under the name "fileNameCorpus.dat"
		try{
			FileOutputStream fileout = new FileOutputStream(fileName + "Corpus.dat");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(corpus);
			out.close();
			fileout.close();
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	public static void loadIndeece(String fileName)
	{
		System.err.println("Deserializing index from "+fileName);
		//Deserialize index with fileName
		Index index = null;
		try{
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			index = (Index) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			System.out.println("File '" +fileName + "' does not exist.");
			i.printStackTrace();
			System.exit(1);
			
		}catch(ClassNotFoundException c)
		{
			System.out.println("Index class not found");
			c.printStackTrace();
			System.exit(0);
		}
		
		//Deserialize corpus with that corresponds to fileName
		TreeSet<Doc> corpus = new TreeSet<Doc>();
		try{
			FileInputStream fileIn = new FileInputStream(fileName.substring(0, fileName.length()-4) + "Corpus.dat");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			corpus = (TreeSet<Doc>) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			System.out.println("File '" + fileName.substring(0, fileName.length()-4) + "Corpus.dat" + "' does not exist.");
			//i.printStackTrace();
			System.exit(0);
		}catch(ClassNotFoundException c)
		{
			System.out.println("Index class not found");
			c.printStackTrace();
			System.exit(0);
		}
		Indeece.index = index;
		Indeece.corpus = corpus;
		//create the corresponding permutermTree
		Indeece.permutermTree = new PermutermTree(index);
		System.out.println("Loaded successfully index and corresponding corpus from " +fileName);
	}

	public static void setActive(String activateModel) {
		if(activateModel.compareTo("Boolean")==0)
			activeModel = BooleanModel; 
		else
			activeModel = VectModel;
	}

	public static void initModels(boolean optEnabled) {
		VectModel    = new VectModel(index,optEnabled);
		BooleanModel = new BoolModel(index);
		activeModel  = BooleanModel;
	}

	public static Model activeModel() {
		return activeModel;
	}
	
	public static boolean isActiveModel(String model) {
		String active = activeModel == BooleanModel? "Boolean" : "Vector";
		return active==model?true:false;
	}
	
	public static int getK() {
		return K;
	}

	public static void setK(int k) {
		K = k;
	}
	
	
	public static String[] getCosineRankingStrategiesString()
	{
		return new String[]{Natural_Ranking , Sublinear_Ranking , LNC_LTC_Ranking };
	}
	public static void setCosineRankingStrategyFromName(
			String strategyName) {
		
		setCosineRankingStrategy(rankingStrategymappings.get(strategyName));
	}
	
	public static void setCosineRankingStrategy(
			ICosineRankCalculationStrategy strategy) {
		
		consineCalculationStrategy = strategy;
	}
	
	public static ICosineRankCalculationStrategy  getCosineStrategy()
	{
		return consineCalculationStrategy;
	}
	
}
