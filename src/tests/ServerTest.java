package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.MessageThread;
import server.Server;
import server.Post;

public class ServerTest{
	
	@Test
	public void testFindIndex() {
		Server.threadList.add(0, new MessageThread(1, "Thread 1"));
		Server.threadList.add(1, new MessageThread(2, "Thread 2"));
		Server.threadList.add(2, new MessageThread(3, "Thread 3"));
		
		Server.threadList.get(0).posts.add(0, new Post(1, "Body 1", "Thread1.Post 1", "author", 1));
		Server.threadList.get(0).posts.add(1, new Post(2, "Body 2", "Thread1.Post 2", "author", 1));
		Server.threadList.get(0).posts.add(2, new Post(3, "Body 3", "Thread1.Post 3", "author", 1));
		Server.threadList.get(1).posts.add(0, new Post(1, "Body 1", "Thread2.Post 1", "author", 2));
		Server.threadList.get(1).posts.add(1, new Post(2, "Body 2", "Thread2.Post 2", "author", 2));
		Server.threadList.get(1).posts.add(2, new Post(3, "Body 3", "Thread2.Post 3", "author", 2));
		Server.threadList.get(2).posts.add(0, new Post(1, "Body 1", "Thread3.Post 1", "author", 3));
		Server.threadList.get(2).posts.add(1, new Post(2, "Body 2", "Thead3.Post 2", "author", 3));
		Server.threadList.get(2).posts.add(2, new Post(3, "Body 3", "Thread3.Post 3", "author", 3));
		
		assertEquals(0, Server.findIndexById(1));
		assertEquals(2, Server.findIndexById(3));
	}
}