package indeece;

import java.io.*;
import java.util.TreeSet;
import java.util.Set;

public class Indeece {
	private static Index index;
	private static Set<Doc> corpus;
	
	public static void createIndex(Set<Doc> crp) {
		corpus=crp;
		index = new Index(corpus);
	}
	
	public static Index getActiveIndex() {
		return index;
	}
	
	public static Set<Doc> getCorpus() {
		return corpus;
	}
	
	public static void storeIndeece(Index index,Set<Doc> corpus,String fileName){
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
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Index class not found");
			c.printStackTrace();
			return;
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
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Index class not found");
			c.printStackTrace();
			return;
		}
		Indeece.index = index;
		Indeece.corpus = corpus;
		System.out.println("Loaded successfully index and corresponding corpus from " +fileName);
	}

}
