package indeece;

import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import java.io.*;


public class PostingList extends TreeSet<PostingList.Item> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private float termIdf;
	public PostingList(){
		super();
	}
	
	//Copy-constructor
	public PostingList(Set copy){
		super(copy);
	}
	
	public void setTermIdf(float idf) {
		termIdf = idf;
		
	}
	
	public float getTermIdf()
	{
		return termIdf;
	}
	
	
	//Posting list item contains a doc and the specific frequency of the term for that doc
	public class Item implements Comparable<Item>,Serializable
	{
		private Doc doc;
		private int frequency;
		
		public Item(Doc doc,int f)
		{
			super();
			this.doc = doc;
			this.frequency = f;
		}
		
		public void increaseFrequency(){
			this.frequency++;
		}
		
		public int getFrequency()
		{
			return this.frequency;
		}
		
		public Doc getDoc()
		{
			return this.doc;
		}

		@Override
		public int compareTo(Item arg0) {
			// TODO Auto-generated method stub
			return this.doc.getID()-arg0.doc.getID();
		}


		
	}
	
}
