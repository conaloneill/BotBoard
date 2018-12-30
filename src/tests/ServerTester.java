package tests;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import server.Post;

public class ServerTester{
	
	@Test
    public void apiTest() throws Exception{
	//public static void main(String [] args) throws Exception{
		
		// Fill board with test data
		
		new ClientResource("http://localhost:9000").post(
        		new Post(1, "Body 1", "Thread1(.Post 1)", "author", 0)
        		).write(System.out);
		new ClientResource("http://localhost:9000").post(
        		new Post(1, "Body 1", "Thread2(.Post 1)", "author", 0)
        		).write(System.out);
		new ClientResource("http://localhost:9000").post(
        		new Post(1, "Body 1", "Thread2(.Post 1)", "author", 0)
        		).write(System.out);
		
		new ClientResource("http://localhost:9000/1").post(
        		new Post(2, "Body 2", "Thread1.Post 2", "author", 1)
        		).write(System.out);
		new ClientResource("http://localhost:9000/1").post(
        		new Post(3, "Body 3", "Thread1.Post 3", "author", 1)
        		).write(System.out);
		
		new ClientResource("http://localhost:9000/2").post(
        		new Post(2, "Body 2", "Thread2.Post 2", "author", 2)
        		).write(System.out);
		new ClientResource("http://localhost:9000/2").post(
        		new Post(3, "Body 3", "Thread2.Post 3", "author", 2)
        		).write(System.out);
		
		new ClientResource("http://localhost:9000/3").post(
        		new Post(2, "Body 2", "Thread3.Post 2", "author", 3)
        		).write(System.out);
		new ClientResource("http://localhost:9000/3").post(
        		new Post(3, "Body 3", "Thread3.Post 3", "author", 3)
        		).write(System.out);
		
    	// Run a series tests on the data
		
    	System.out.println("GET request to thread id 2");
    	
    	new ClientResource("http://localhost:9000/2").get().write(System.out);
    	
    	System.out.println("\n\nPOST request to thread id 2");
    	
    	Post testPost = new Post(0, "new " +
		        "post body", "new post title", "author", 2);
    	
    	new ClientResource("http://localhost:9000/2").post(testPost).write(System.out);
		        
    	System.out.println("\n\nPOST request to root URL to create new thread");
        
        new ClientResource("http://localhost:9000").post(
        		new Post(8, "new thread first post", "new thread", "author", 0)
        		).write(System.out);
        
    	System.out.println("\n\nPOST request to root URL to create new post in thread 1");
    	
        new ClientResource("http://localhost:9000").post(
        		new server.Post(8, "Test4", "Test4", "author", 1)
        		).write(System.out);
        
    	System.out.println("\n\nPUT request to /1/2");
    	
        new ClientResource("http://localhost:9000/1/2").put(
        		new server.Post(2, "Put5", "Put5", "author", 1)
        		).write(System.out);
        
    	System.out.println("\n\nDelete request to /3/2");
    	
        new ClientResource("http://localhost:9000/3/2").delete().write(System.out);
        
    	System.out.println("\n\nDelete request to /4/1");
    	
        new ClientResource("http://localhost:9000/4/1").delete().write(System.out);
        
    	System.out.println("\n\nPOST request to thread id 3");
    	
    	Post testPost2 = new Post(0, "new " +
		        "post body", "new post title", "author", 2);
    	
    	new ClientResource("http://localhost:9000/3").post(testPost2).write(System.out);
    	
    	System.out.println("\n\nPOST request to root URL to create new thread");
        
        new ClientResource("http://localhost:9000").post(
        		new Post(8, "new thread first post", "new thread", "author", 0)
        		).write(System.out);
        
        new ClientResource("http://localhost:9000/4").delete().write(System.out);
        
    }
    
    
}