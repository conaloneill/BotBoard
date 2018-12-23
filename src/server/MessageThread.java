package server;

import java.util.LinkedList;
import java.util.List;

public class MessageThread{
	List<Post> posts;
	MessageThreadInfo info;
	
	MessageThread(int threadId, String threadName){
		posts = new LinkedList<Post>();
		info = new MessageThreadInfo(threadId, threadName);
	}
	
	class MessageThreadInfo{
		int threadId;
		String threadName;
		
		MessageThreadInfo(int threadId, String threadName){
			this.threadId = threadId;
			this.threadName = threadName;
		}
	}
}

