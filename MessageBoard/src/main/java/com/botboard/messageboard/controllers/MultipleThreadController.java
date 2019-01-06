package com.botboard.messageboard.controllers;

import com.botboard.messageboard.MessageBoardApplication;
import com.botboard.messageboard.models.MessageThread;
import com.botboard.messageboard.models.MessageThreadInfo;
import com.botboard.messageboard.models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MultipleThreadController {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	// For GET requests, returns a json response of a list of threads(not including their posts)
	@ApiOperation(value = "Get a list of the message threads on the board in Json form.", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public List<MessageThreadInfo> getThreads() {
		int lastThreadIndex = MessageBoardApplication.threadList.size() - 1;
		
		List<MessageThreadInfo> threadList = new LinkedList<MessageThreadInfo>();
		
		//Loop through each thread extracting its id and name
		for (int i = 0; i <= lastThreadIndex; i++) {
			MessageThreadInfo threadEntry = MessageBoardApplication.threadList.get(i).info;
			threadList.add(i, threadEntry);
		}
		
		return threadList;
	}
	
	// For POST requests, takes a json input of a Post,
	// If the threadId is 0, it creates a new thread with the title of the post,
	// and sets the post as it's first entry.
	// Returns a list of threads as in a GET request.
	@ApiOperation(value = "Create a new thread(if post thread Id is 0) or add a post to an existing thread.\n(takes a json Post object)", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK")
	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public ResponseEntity<List<MessageThreadInfo>> addPost(@RequestBody Post newPost) {
		
		//If threadId is 0, create a new thread
		if (newPost.threadId == 0) {
			int newThreadIndex = MessageBoardApplication.threadList.size();
			int newThreadId;
			
			if (newThreadIndex > 0)
				newThreadId = MessageBoardApplication.threadList.get(newThreadIndex - 1).info.getId() + 1;
			else
				newThreadId = 1;
			
			MessageBoardApplication.threadList.add(new MessageThread(newThreadId, newPost.title));
			newPost.id = 1;
			MessageBoardApplication.threadList.get(newThreadIndex).posts.add(0, newPost);
			
			// Return a thread list as in the GET case
			List<MessageThreadInfo> threadList = new LinkedList<MessageThreadInfo>();
			for (int i = 0; i <= newThreadIndex; i++) {
				MessageThreadInfo threadEntry = MessageBoardApplication.threadList.get(i).info;
				threadList.add(i, threadEntry);
			}
			return new ResponseEntity(threadList, HttpStatus.OK);
		}
		
		//If threadId matches an existing thread, ad the post to the end of that thread, return null.
		//(Note: this is a copy paste of the method from ThreadResource. While redundant,
		//the restlet api makes it very difficult to call a http method from within a http method.
		//Possible future solutions might involve: 1. Router redirection 2. Running in a servlet
		//3. RIAP(restlet internal access protocol)
		else if (newPost.threadId > 0 && MessageBoardApplication.threadList.size() > 0
				&& newPost.threadId <= MessageBoardApplication.threadList.get(MessageBoardApplication.threadList.size() - 1).info.getId()) {
			
			int threadIndex = MessageBoardApplication.findIndexById(newPost.threadId);
			
			//Return if no such thread exists
			if (threadIndex < 0)
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			
			int newPostIndex = MessageBoardApplication.threadList.get(threadIndex).posts.size();
			int newPostId = MessageBoardApplication.threadList.get(threadIndex).posts.get(newPostIndex - 1).id + 1;
			
			newPost.id = newPostId;
			
			MessageBoardApplication.threadList.get(threadIndex).posts.add(newPostIndex, newPost);
			
			return new ResponseEntity(HttpStatus.ACCEPTED);
			
		} else {
			
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
	}
}
