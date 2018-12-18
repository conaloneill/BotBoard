import java.util.Date;

public class Post {
	
	public int id;
	public String body;
	public String title;
	public String author;
	public Date date;
	
	public Post(int id, String body, String title, String author) {
		this.id = id;
		this.body = body;
		this.title = title;
		this.author = author;
		this.date = new Date();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
