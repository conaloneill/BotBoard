package server;

import java.util.LinkedList;
import java.util.List;

public class MessageThread{
	public List<Post> posts;
	public MessageThreadInfo info;
	
	MessageThread(int threadId, String threadName){
		posts = new LinkedList<Post>();
		info = new MessageThreadInfo(threadId, threadName);
	}
	
	// Find the index of a Post in a Message Thread by it's id
	public int findIndexById(int id) {
		int i = 0;
		
		for(Post p : posts){
			if(p.id == id)
				break;
			i++;
		}
		
		//If there is no post with such an id, return -1
		if(i == posts.size() && posts.get(i-1).id != id)
			return -1;
		
		return i;
	}

}

