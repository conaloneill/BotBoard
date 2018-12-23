package server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

/*  (extending ServerResource allows for @Get, @Post annotations which execute based on
 *  whether the class receives a GET, POST request respectively)
 * 
 *  Class defines the methods to execute in case of REST requests for the  
 *  "localhost:9000/{threadid}/{postid}" URL pattern
 */
public class PostResource extends ServerResource{
    int postId;
    int threadId;
    
    Gson gson = new Gson();

    // Extract "postid", and "threadid" from the URL pattern
    // "localhost:900/{threadid}/{postid}"
    @Override
    public void doInit() {
        this.postId = Integer.parseInt((String) getRequestAttributes().get("postid"));
        this.threadId = Integer.parseInt((String) getRequestAttributes().get("threadid"));
    }

    // For GET requests, returns a json Post with the postid and threadid requested
    @Get("json")
    public String getPost() {
    	return gson.toJson(Server.threadList.get(threadId-1).posts.get(postId-1));
    }
}