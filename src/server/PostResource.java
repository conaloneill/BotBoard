package server;

import java.io.IOException;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;

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
	@ApiOperation(value = "Get the post at this Url", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
    @Get
    public Post getPost() {
		int threadIndex = Server.findIndexById(threadId);
		
		//Return if no such thread exists
		if(threadIndex < 0)
			return null;
		
		MessageThread thread = Server.threadList.get(threadIndex);
		
		int postIndex = thread.findIndexById(postId);
		
		//Return if no such post exists
		if(postIndex < 0)
			return null;
		
		return thread.posts.get(postIndex);
    }
    
    // For PUT requests, takes a json Post and updates the Post at the Url pattern
	@ApiOperation(value = "Update this post if the input post Id matches. \n (Takes a json Post object)", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
    @Put
    public Post updatePost(String input) throws JsonParseException, JsonMappingException, IOException {
    	
    	Post newPost = objectMapper.readValue(input, Post.class);
    	
    	// Return if the post's id or thread id don't match the URL pattern
    	if(newPost.id != postId || newPost.threadId != threadId)
    		return null;
    	
    	// Update post and return it
    	else {
    		int threadIndex = Server.findIndexById(threadId);
    		
    		//Return if no such thread exists
    		if(threadIndex < 0)
    			return null;
    		
    		MessageThread thread = Server.threadList.get(threadIndex);
    		
    		int postIndex = thread.findIndexById(postId);
    		
    		//Return if no such post exists
    		if(postIndex < 0)
    			return null;
    		
    		thread.posts.set(postIndex, newPost);

    		return newPost;
    	}
    }
	
	// For DELETE requests, removes the post at this url from the thread unless it's the first post
	@ApiOperation(value = "Delete this post unless it is the first post in the thread.", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@Delete
	public String deletePost() {		
		if(postId > 1) {
    		int threadIndex = Server.findIndexById(threadId);
    		
    		//Return if no such thread exists
    		if(threadIndex < 0)
    			return null;
    		
    		MessageThread thread = Server.threadList.get(threadIndex);
    		
    		int postIndex = thread.findIndexById(postId);
    		
    		//Return if no such post exists
    		if(postIndex < 0)
    			return null;
    		
    		thread.posts.remove(postIndex);
    		
			return "Succesfully deleted";
		}
		else if(postId == 1)
			return "Cannot delete first post, must delete whole thread";
		else
			return "Error: No post exists at this address";
	}
	
}