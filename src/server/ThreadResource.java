package server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

public class ThreadResource extends ServerResource {
    int threadId;
    
    Gson gson = new Gson();
    
    @Override
    public void doInit() {
        this.threadId = Integer.parseInt((String) getRequestAttributes().get("threadid"));
    }

    @Get("json")
    public String getPosts() {
        return gson.toJson(Server.threadList.get(threadId-1).posts);
    }
    
    @org.restlet.resource.Post("json:json")
    public String addPost(String input){
    	
    	Post newPost = gson.fromJson(input, Post.class);
    	
    	int newPostIndex = Server.threadList.get(threadId-1).posts.size();
    	
    	//////Needs major rework when Delete REST requests are implemented/////
    	newPost.id = newPostIndex+1;
    	newPost.threadId = threadId;
    	///////////////////////////////////////////////////////////////
    	
    	Server.threadList.get(threadId-1).posts.add(newPostIndex, newPost);
    	
    	return gson.toJson(Server.threadList.get(threadId-1).posts);
    }
}
