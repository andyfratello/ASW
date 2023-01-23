package wallOfTweets;


public class Tweet {

	private long twid;
	private String author;
	private String text;
	private java.util.Date date;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public Tweet() {
	}
	public long getTwid() {
		return twid;
	}
	public void setTwid(long twid) {
		this.twid = twid;
	}
	
	
	
}
