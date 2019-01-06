package com.botboard.messageboard;

import com.botboard.messageboard.models.MessageThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class MessageBoardApplication {
	
	// The "core" data structure of the BotBoard application
	// which stores each thread with all their posts.
	static public List<MessageThread> threadList = new LinkedList<>();
	
	public static void main(String[] args) {
		SpringApplication.run(MessageBoardApplication.class, args);
	}
	
	
	// Find the index of a Thread in the Thread List by it's id
	public static int findIndexById(int id) {
		int i = 0;
		
		for (MessageThread t : threadList) {
			if (t.info.getId() == id)
				break;
			i++;
		}
		
		//If there is no thread with such an id, return -1
		if (i == threadList.size() && threadList.get(i - 1).info.getId() != id)
			return -1;
		
		return i;
	}
}

