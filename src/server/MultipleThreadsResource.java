package server;
import java.util.LinkedList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;

/*  (extending ServerResource allows for @Get, @Post annotations which execute based on
 *  whether the class receives a GET, POST request respectively)
 * 
 *  Class defines the methods to execute in case of REST requests for the  
 *  "localhost:9000/" URL pattern
 */
public class MultipleThreadsResource extends ServerResource{
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	// For GET requests, returns a json response of a list of threads(not including their posts)
	@ApiOperation(value = "Get a list of the message threads on the board in Json form.", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
    @Get
    public List<MessageThreadInfo> getThreads() {
        int lastThreadIndex = Server.threadList.size()-1;
        
    	List<MessageThreadInfo> threadList = new LinkedList<MessageThreadInfo>();
    	
    	//Loop through each thread extracting its id and name
    	for(int i = 0; i <= lastThreadIndex; i++) {	
    		MessageThreadInfo threadEntry = Server.threadList.get(i).info;
    		threadList.add(i, threadEntry);
    	}
    	
    	return threadList;
    }
    
    // For POST requests, takes a json input of a Post, 
    // If the threadId is 0, it creates a new thread with the title of the post,
    // and sets the post as it's first entry.
    // Returns a list of threads as in a GET request.
	@ApiOperation(value = "Create a new thread(if post thread Id is 0) or add a post to an existing thread.\n(takes a json Post object)", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK")
    @org.restlet.resource.Post
    public List<MessageThreadInfo> addPost(String input) throws Exception{
    	
    	Post newPost = objectMapper.readValue(input, Post.class);
    	
    	//If threadId is 0, create a new thread
    	if(newPost.threadId == 0) {
    		int newThreadIndex = Server.threadList.size();
    		int newThreadId = Server.threadList.get(newThreadIndex-1).info.getId() + 1;
    		
    		Server.threadList.add(new MessageThread(newThreadId, newPost.title));
    		newPost.id = 1;
    		Server.threadList.get(newThreadIndex).posts.add(0, newPost);
    	
    		// Return a thread list as in the GET case
    		List<MessageThreadInfo> threadList = new LinkedList<MessageThreadInfo>();
        	for(int i = 0; i <= newThreadIndex; i++) {	
        		MessageThreadInfo threadEntry = Server.threadList.get(i).info;
        		threadList.add(i, threadEntry);
        	}
    		return threadList;
    	}
    	
    	//If threadId matches an existing thread, ad the post to the end of that thread, return null.
    	//(Note: this is a copy paste of the method from ThreadResource. While redundant,
    	//the restlet api makes it very difficult to call a http method from within a http method.
    	//Possible future solutions might involve: 1. Router redirection 2. Running in a servlet
    	//3. RIAP(restlet internal access protocol)
    	else if(newPost.threadId > 0 && Server.threadList.size() > 0 
    			&& newPost.threadId <= Server.threadList.get(Server.threadList.size()-1).info.getId()) {
        	
        	int threadIndex = Server.findIndexById(newPost.threadId);
        	
    		//Return if no such thread exists
    		if(threadIndex < 0)
    			return null;
        	
        	int newPostIndex = Server.threadList.get(threadIndex).posts.size();
        	int newPostId = Server.threadList.get(threadIndex).posts.get(newPostIndex-1).id + 1;
        	
        	newPost.id = newPostId;
        	
        	Server.threadList.get(threadIndex).posts.add(newPostIndex, newPost);
        	
        	return null;
        	
    	}
    	else {
    		return null;
    	}
    	
    }
}