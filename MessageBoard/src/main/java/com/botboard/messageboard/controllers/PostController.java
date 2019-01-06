package com.botboard.messageboard.controllers;

import com.botboard.messageboard.MessageBoardApplication;
import com.botboard.messageboard.models.MessageThread;
import com.botboard.messageboard.models.Post;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
	
	
	// For GET requests, returns a json Post with the postid and threadid requested
	@ApiOperation(value = "Get the post at this Url", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/{threadid}/{postid}", method = RequestMethod.GET)
	public ResponseEntity<Post> getPost(@PathVariable(value = "threadid") int threadId,
	                                    @PathVariable(value =
			                                    "postid") int postId) {
		int threadIndex = MessageBoardApplication.findIndexById(threadId);
		
		//Return if no such thread exists
		if (threadIndex < 0)
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		
		MessageThread thread = MessageBoardApplication.threadList.get(threadIndex);
		
		int postIndex = thread.findIndexById(postId);
		
		//Return if no such post exists
		if (postIndex < 0)
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity(thread.posts.get(postIndex), HttpStatus.OK);
	}
	
	// For PUT requests, takes a json Post and updates the Post at the Url pattern
	@ApiOperation(value = "Update this post if the input post Id matches. \n (Takes a json Post object)", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/{threadid}/{postid}", method = RequestMethod.PUT)
	public ResponseEntity<Post> updatePost(@PathVariable(value = "threadid") int threadId,
	                                       @PathVariable(value =
			                                       "postid") int postId,
	                                       @RequestBody Post httpPost) {
		
		// Return if the post's id or thread id don't match the URL
		Post newPost = new Post(httpPost.id, httpPost.body,httpPost.title,httpPost.author,
				httpPost.threadId);
		if (newPost.id != postId || newPost.threadId != threadId)
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
			
			// Update post and return it
		else {
			int threadIndex = MessageBoardApplication.findIndexById(threadId);
			
			//Return if no such thread exists
			if (threadIndex < 0)
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
			
			MessageThread thread = MessageBoardApplication.threadList.get(threadIndex);
			
			int postIndex = thread.findIndexById(postId);
			
			//Return if no such post exists
			if (postIndex < 0)
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
			
			thread.posts.set(postIndex, newPost);
			
			return new ResponseEntity(newPost, HttpStatus.ACCEPTED);
		}
	}
	
	// For DELETE requests, removes the post at this url from the thread unless it's the first post
	@ApiOperation(value = "Delete this post unless it is the first post in the thread.", tags = "API")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/{threadid}/{postid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePost(@PathVariable(value = "threadid") int threadId,
	                                         @PathVariable(value =
			                                         "postid") int postId) {
		if (postId > 1) {
			int threadIndex = MessageBoardApplication.findIndexById(threadId);
			
			//Return if no such thread exists
			if (threadIndex < 0)
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
			
			MessageThread thread = MessageBoardApplication.threadList.get(threadIndex);
			
			int postIndex = thread.findIndexById(postId);
			
			//Return if no such post exists
			if (postIndex < 0)
				return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
			
			thread.posts.remove(postIndex);
			
			return new ResponseEntity("Succesfully deleted", HttpStatus.ACCEPTED);
		} else if (postId == 1)
			return new ResponseEntity("Cannot delete first post, must delete whole thread",
					HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity("Error: No post exists at this address",
					HttpStatus.BAD_REQUEST);
	}
	
}
