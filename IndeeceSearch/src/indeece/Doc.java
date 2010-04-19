package indeece;

public class Doc {
	private int ID;
	private String title;
	private String body;
	private int length;
	
	public Doc(int iD, String title, String body) {
		super();
		ID = iD;
		this.title = title;
		this.body = body;
		// set this.length (TODO)
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	public int getID(){
		return this.ID;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setBody(String body){
		this.body = body;
	}
	public String getBody(){
		return this.body;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String toString() {
		return this.title;
	}
}
