package server;

import java.util.Date;

public class Post {
	
	public int id;
	public String body;
	public String title;
	public String author;
	public Date date;
	
	public int threadId;
	
	public Post(int id, String body, String title, String author, int threadId) {
		this.id = id;
		this.body = body;
		this.title = title;
		this.author = author;
		this.date = new Date();
		this.threadId = threadId;
	}
	
}
