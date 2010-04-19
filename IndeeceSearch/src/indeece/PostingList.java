package indeece;

import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;


public class PostingList extends TreeSet<PostingList.Item> {

	private static final long serialVersionUID = 1L;
	
	public PostingList(){
		super();
	}
	
	//Copy-constructor
	public PostingList(TreeSet copy){
		super(copy);
	}
	
	// Returns the intersection of this and the other set
	public PostingList and(PostingList other)
	{
		PostingList result = new PostingList();
		Iterator<Item> op1 = this.iterator();
		Iterator<Item> op2 = other.iterator();
		
		PostingList.Item current1 = op1.next();
		PostingList.Item current2 = op2.next();
		
		while(current1!=null && current2!=null) {
			
			//If is the same document
			if(current1.getDoc().getID() == current2.getDoc().getID()) {
				result.add(current1);
				current1=op1.next();
				current2=op2.next();
			}
			//If op2 is ahead of op1
			else if(current1.getDoc().getID() < current2.getDoc().getID()) 
				current1=op1.next();
			//If op1 is ahead of op2
			else
				current2=op2.next();
		}
		
		return result;
	}
	
	// Returns the union of this and the other set	
	public PostingList or(PostingList other)
	{
		PostingList result = new PostingList(this);

		Iterator<Item> op2 = other.iterator();
		Item current;
		while(op2.hasNext()) {
			current=op2.next();
			//If current item is not already contained in the result...
			if(!result.contains(current)) {
				result.add(current);
			}
		}

		return result;
	}
	
	//Returns the complement of the corpus
	public PostingList not(Set<Doc> other)
	{
		PostingList result = new PostingList();
		Iterator<Item> it = this.iterator();
		Item current;
		while(it.hasNext()) {
			current=it.next();
			if(!other.contains(current.getDoc()))
					result.add(current);
		}

		return result;
	}
	
	//Posting list item contains a doc and the specific frequency of the term for that doc
	public class Item implements Comparable<Item>
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
