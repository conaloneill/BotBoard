package server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

/*  (extending ServerResource allows for @Get, @Post annotations which execute based on
 *  whether the class receives a GET, POST request respectively)
 * 
 *  Class defines the methods to execute in case of REST requests for the  
 *  "localhost:9000/{threadid}" URL pattern
 */
public class ThreadResource extends ServerResource {
    int threadId;
    
    Gson gson = new Gson();
    
    //Extract "threadid" from the URL pattern "localhost:9000/{threadid}"
    @Override
    public void doInit() {
        this.threadId = Integer.parseInt((String) getRequestAttributes().get("threadid"));
    }

    // For a GET request, returns a json list of the Posts in the thread
    @Get("json")
    public String getPosts() {
        return gson.toJson(Server.threadList.get(threadId-1).posts);
    }
    
    // For a POST request, takes a json Post object,
    // then adds it to the end of the thread, with appropriate postId
    @org.restlet.resource.Post("json:json")
    public String addPost(String input){
    	
    	Post newPost = gson.fromJson(input, Post.class);
    	
    	int newPostIndex = Server.threadList.get(threadId-1).posts.size();
    	
    	//////Needs major rework when Delete REST requests are implemented/////
    	newPost.id = newPostIndex+1;
    	newPost.threadId = threadId;
    	///////////////////////////////////////////////////////////////////////
    	
    	Server.threadList.get(threadId-1).posts.add(newPostIndex, newPost);
    	
    	return gson.toJson(Server.threadList.get(threadId-1).posts);
    }
}
