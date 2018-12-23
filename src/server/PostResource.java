package server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

public class PostResource extends ServerResource{
    int postId;
    int threadId;
    
    Gson gson = new Gson();
    
    //Object user;

    @Override
    public void doInit() {
        this.postId = Integer.parseInt((String) getRequestAttributes().get("postid"));
        this.threadId = Integer.parseInt((String) getRequestAttributes().get("threadid"));
     //   this.user = null; // Could be a lookup to a domain object.
    }

    @Get("json")
    public String toString() {
    	return gson.toJson(Server.threadList.get(threadId-1).posts.get(postId-1));
    }
}