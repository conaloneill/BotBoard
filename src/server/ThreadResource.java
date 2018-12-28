package server;

import java.io.IOException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Get("json")
    public List<Post> getPosts() {
        return Server.threadList.get(threadId-1).posts;
    }
    
    // For a POST request, takes a json Post object,
    // then adds it to the end of the thread, with appropriate postId
    @org.restlet.resource.Post("json:json")
    public List<Post> addPost(String input) throws JsonParseException, JsonMappingException, IOException{

    	Post newPost = objectMapper.readValue(input, Post.class);
    	
    	int newPostIndex = Server.threadList.get(threadId-1).posts.size();
    	
    	//////Needs major rework when Delete REST requests are implemented/////
    	newPost.id = newPostIndex+1;
    	newPost.threadId = threadId;
    	///////////////////////////////////////////////////////////////////////
    	
    	Server.threadList.get(threadId-1).posts.add(newPostIndex, newPost);
    	
    	return Server.threadList.get(threadId-1).posts;
    }
}
