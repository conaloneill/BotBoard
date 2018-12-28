package server;

/* Had to put this into it's own public class so that Jackson can automatically
 serialize The messageThread class. There is probably a nicer way of doing it,
 and I hope to re-factor to it in the future */
public 	class MessageThreadInfo{
	public int threadId;
	public String threadName;
	
	MessageThreadInfo(int threadId, String threadName){
		this.threadId = threadId;
		this.threadName = threadName;
	}
}