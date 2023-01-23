package wallOfTweets;


public class Tweet {

	private long id;
    private String author;
    private String text;
    private long date; // number of milliseconds since January 1, 1970, 00:00:00 GMT
    private Integer likes;
    
	public Tweet() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	public long getDate() {
		return date;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	
	
    
}
