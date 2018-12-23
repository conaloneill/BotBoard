package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Post;

import static org.junit.Assert.*;

public class PostTest {
	
	Post post;
	
	@Before
	public void setUp() throws Exception {
		post = new Post(8, "new thread first post", "new thread", "author", 0);
	}
	
	@After
	public void tearDown() throws Exception {
		post = null;
	}
	
	@Test
	public void constructorTest() {
		assertEquals("author", post.author);
		assertEquals(8, post.id);
		assertEquals(0, post.threadId);
		assertEquals("new thread first post", post.body);
		assertEquals("new thread", post.title);
		
		
	}
}