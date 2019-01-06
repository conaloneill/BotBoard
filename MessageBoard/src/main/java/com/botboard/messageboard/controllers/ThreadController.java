package com.botboard.messageboard.controllers;

import com.botboard.messageboard.MessageBoardApplication;
import com.botboard.messageboard.models.Post;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.discovery.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ThreadController {
	
	// For a GET request, returns a json list of the Posts in the thread
	@ApiOperation(value = "Get a list of the posts in the thread in Json form.", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/board/{threadid}", method = RequestMethod.GET)
	
	public ResponseEntity<List<Post>> getPosts(@PathVariable(value = "threadid") int threadId) {
		int threadIndex = MessageBoardApplication.findIndexById(threadId);
		//Return if no such thread exists
		if (threadIndex < 0)
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity(MessageBoardApplication.threadList.get(threadIndex).posts,
				HttpStatus.OK);
	}
	
	// For a POST request, takes a json Post object,
	// then adds it to the end of the thread, with appropriate postId
	@ApiOperation(value = "Creates a new post in the thread.\n(takes a json Post object)", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/board/{threadid}", method = RequestMethod.POST)
	public ResponseEntity<List<Post>> addPost(@PathVariable(value = "threadid") int threadId,
	                                          @RequestBody Post httpPost) {
		
		Post newPost = new Post(httpPost.id, httpPost.body,httpPost.title,httpPost.author,
				httpPost.threadId);
		int threadIndex = MessageBoardApplication.findIndexById(threadId);
		
		//Return if no such thread exists
		if (threadIndex < 0)
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		
		int newPostIndex = MessageBoardApplication.threadList.get(threadIndex).posts.size();
		int newPostId;
		
		if (newPostIndex > 0)
			newPostId = MessageBoardApplication.threadList.get(threadIndex).posts.get(newPostIndex - 1).id + 1;
		else
			newPostId = 1;
		
		newPost.id = newPostId;
		newPost.threadId = threadId;
		
		MessageBoardApplication.threadList.get(threadIndex).posts.add(newPostIndex, newPost);
		
		if (checkForBotCallInBody(newPost)) {
			if (checkIfBotisActive(newPost)) {
				MessageBoardApplication.threadList.get(threadIndex).posts.add(newPostIndex + 1,
						getBotResult(newPost));
			}
		}
		
		return new ResponseEntity(MessageBoardApplication.threadList.get(threadIndex).posts,
				HttpStatus.OK);
	}
	
	
	// For DELETE requests, removes the thread at this url
	@ApiOperation(value = "Delete this thread", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/board{threadid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteThread(@PathVariable(value = "threadid") int threadId) {
		int threadIndex = MessageBoardApplication.findIndexById(threadId);
		
		//Return if no such thread exists
		if (threadIndex < 0)
			return new ResponseEntity("Error: No thread exists at this address",
					HttpStatus.BAD_REQUEST);
		
		MessageBoardApplication.threadList.remove(threadIndex);
		return new ResponseEntity("Succesfully deleted", HttpStatus.ACCEPTED);
	}
	
	
	// Private Methods - Bot checking
	
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	
	private Post getBotResult(Post newPost) {
		String body = newPost.body;
		
		Pattern p = Pattern.compile("@(\\w+)");
		Matcher m = p.matcher(body);
		
		if (m.find()) {
			String serviceName = m.group(1);
			
			List<String> list = discoveryClient.getServices();
			
			for (String appName : list
			     ) {
				if( appName.equals(serviceName)) {
					String url = "http://" + serviceName;
					String response = restTemplate.getForObject(url, String.class);
					return new Post(newPost.id + 1, response, "Bot Result", serviceName,
							newPost.threadId + 1);
				}
			}
			
			
			return newPost;
		}
		
		return null;
	}
	
	
	private boolean checkIfBotisActive(Post newPost) {
		
		String body = newPost.body;
		
		Pattern p = Pattern.compile("@(\\w+)");
		Matcher m = p.matcher(body);
		
		if (m.find()) {
			String serviceName = m.group(1);
			
			List<String> list = discoveryClient.getServices();
			
			for (String appName : list
			) {
				if(appName.equals(serviceName)) {
					return true;
				}
			}
			
			
			return false;
		}
		
		return false;
	}
	
	private Boolean checkForBotCallInBody(Post newPost) {
		String body = newPost.body;
		
		Pattern p = Pattern.compile("@(\\w+)");
		Matcher m = p.matcher(body);
		return m.find();
	}
}
