package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.MessageThread;
import server.Server;

public class MessageThreadTest{
	
	@Test
	public void constructorTest() {
		MessageThread thread = new MessageThread(1, "testname");
		
		assertEquals(1, thread.info.getId());
		assertEquals("testname", thread.info.getName());
		assertEquals(0, thread.posts.size());
	}
	
	@Test
	public void testFindIndex() {
		//Provided ServerTest is run first(to fill board with some data)
		
		assertEquals(1, Server.threadList.get(1).findIndexById(2));
		assertEquals(-1, Server.threadList.get(1).findIndexById(4));
		assertEquals(-1, Server.threadList.get(1).findIndexById(0));
	}
}