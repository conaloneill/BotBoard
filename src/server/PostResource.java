package server;

import java.io.IOException;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*  (extending ServerResource allows for @Get, @Post annotations which execute based on
 *  whether the class receives a GET, POST request respectively)
 * 
 *  Class defines the methods to execute in case of REST requests for the  
 *  "localhost:9000/{threadid}/{postid}" URL pattern
 */
public class PostResource extends ServerResource{
    int postId;
    int threadId;
    
    ObjectMapper objectMapper = new ObjectMapper();

    // Extract "postid", and "threadid" from the URL pattern
    // "localhost:900/{threadid}/{postid}"
    @Override
    public void doInit() {
        this.postId = Integer.parseInt((String) getRequestAttributes().get("postid"));
        this.threadId = Integer.parseInt((String) getRequestAttributes().get("threadid"));
    }

    // For GET requests, returns a json Post with the postid and threadid requested
    @Get("json")
    public Post getPost() {
    	return Server.threadList.get(threadId-1).posts.get(postId-1);
    }
    
    // For PUT requests, takes a json Post and updates the Post at the Url pattern
    @Put("json")
    public Post updatePost(String input) throws JsonParseException, JsonMappingException, IOException {
    	
    	Post newPost = objectMapper.readValue(input, Post.class);
    	
    	//If the post's id or thread id don't match the URL pattern
    	if(newPost.id != postId || newPost.threadId != threadId)
    		return null;
    	
    	//Update post and return it
    	else {
    		Server.threadList.get(threadId-1).posts.set(postId-1, newPost);
    		return newPost;
    	}
    }
}