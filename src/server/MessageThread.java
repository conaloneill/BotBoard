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
	

}

