package server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;

public class MultipleThreadsResource extends ServerResource{
	
	Gson gson = new Gson();
	
	
    @Get("json")
    public String toString() {
        int lastThreadIndex = Server.threadList.size()-1;
        
    	List<MessageThread.MessageThreadInfo> threadList = new LinkedList<MessageThread.MessageThreadInfo>();
    	
    	for(int i = 0; i <= lastThreadIndex; i++) {	
    		MessageThread.MessageThreadInfo threadEntry = Server.threadList.get(i).info;
    		threadList.add(i, threadEntry);
    	}
    	
    	return gson.toJson(threadList);
    }
    
    @org.restlet.resource.Post("json:json")
    public String addThread(String input) throws ResourceException, IOException{
    	
    	Post newPost = gson.fromJson(input, Post.class);
    	
    	if(newPost.threadId == 0) {
    		int newThreadIndex = Server.threadList.size();
    		Server.threadList.add(new MessageThread(newThreadIndex+1, newPost.title));
    		newPost.id = 1;
    		Server.threadList.get(newThreadIndex).posts.add(0, newPost);
    	
    		List<MessageThread.MessageThreadInfo> threadList = new LinkedList<MessageThread.MessageThreadInfo>();
        	for(int i = 0; i <= newThreadIndex; i++) {	
        		MessageThread.MessageThreadInfo threadEntry = Server.threadList.get(i).info;
        		threadList.add(i, threadEntry);
        	}
    		return gson.toJson(threadList);
    	}
    /*	else if(newPost.threadId > 0 && newPost.threadId < server.Server.threadList.size()) {
    		new ClientResource("http://localhost:8080/" + newPost.threadId)
    				.get().write(System.out);
    	return null;	
    	//	Representation rep = new ClientResource("http://localhost:8080/" + newPost.threadId)
    	//			.post(gson.toJson(newPost));
    		////?????////
    	//	return rep.toString();
    		/////////////
    	}*/
    	else {
    		return null;
    	}
    	
    }
}