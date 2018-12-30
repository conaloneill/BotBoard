package server;

import java.io.IOException;
import java.util.List;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
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
 *  "localhost:9000/{threadid}" URL pattern
 */
public class ThreadResource extends ServerResource {
    int threadId;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    //Extract "threadid" from the URL pattern "localhost:9000/{threadid}"
    @Override
    public void doInit() {
        this.threadId = Integer.parseInt((String) getRequestAttributes().get("threadid"));
    }

    // For a GET request, returns a json list of the Posts in the thread
	@ApiOperation(value = "Get a list of the posts in the thread in Json form.", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
    @Get
    public List<Post> getPosts() {
		int threadIndex = Server.findIndexById(threadId);
		
		//Return if no such thread exists
		if(threadIndex < 0)
			return null;
		
        return Server.threadList.get(threadIndex).posts;
    }
    
    // For a POST request, takes a json Post object,
    // then adds it to the end of the thread, with appropriate postId
	@ApiOperation(value = "Creates a new post in the thread.\n(takes a json Post object)", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
    @org.restlet.resource.Post
    public List<Post> addPost(String input) throws JsonParseException, JsonMappingException, IOException{

    	Post newPost = objectMapper.readValue(input, Post.class);
    	
    	int threadIndex = Server.findIndexById(threadId);
    	
		//Return if no such thread exists
		if(threadIndex < 0)
			return null;
    	
    	int newPostIndex = Server.threadList.get(threadIndex).posts.size();
    	int newPostId;
    	
    	if(newPostIndex > 0)
    		newPostId = Server.threadList.get(threadIndex).posts.get(newPostIndex-1).id + 1;
    	else
    		newPostId = 1;
    	
    	newPost.id = newPostId;
    	newPost.threadId = threadId;
    	
    	Server.threadList.get(threadIndex).posts.add(newPostIndex, newPost);
    	
    	return Server.threadList.get(threadIndex).posts;
    }
	
	// For DELETE requests, removes the thread at this url
	@ApiOperation(value = "Delete this thread", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@Delete
	public String deleteThread() {
		int threadIndex = Server.findIndexById(threadId);
		
		//Return if no such thread exists
		if(threadIndex < 0)
			return "Error: No thread exists at this address";
		
		Server.threadList.remove(threadIndex);
		return "Succesfully deleted";
	}
}
