package server;

import java.util.Date;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Describes a Post object.")
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
	
	//Dummy constructor required for Jackson library
	public Post(){}
	
	//Getter methods for Swagger introspection
	public int getId() {
		return id;
	}
	public String getBody() {
		return body;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public Date getDate() {
		return date;
	}
}
