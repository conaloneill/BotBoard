package com.botboard.messageboard.models;

import io.swagger.annotations.ApiModel;

/* Had to put this into it's own public class so that Jackson can automatically
 serialize The messageThread class. There is probably a nicer way of doing it,
 and I hope to re-factor to it in the future */
@ApiModel(description = "Consists of the id and name of the message thread.")
public class MessageThreadInfo {
	
	private int threadId;
	private String threadName;
	
	public int getId() {
		return threadId;
	}
	
	public String getName() {
		return threadName;
	}
	
	MessageThreadInfo(int threadId, String threadName) {
		this.threadId = threadId;
		this.threadName = threadName;
	}
}