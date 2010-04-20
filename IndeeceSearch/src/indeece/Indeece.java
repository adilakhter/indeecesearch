package indeece;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class Indeece 
{
	private static Index index;
	private static Set<Doc> corpus;
	private static PermutermTree permutermTree;
	
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
}
