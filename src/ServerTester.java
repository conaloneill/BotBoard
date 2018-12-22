import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

public class ServerTester{
    public static void main(String[] args) throws Exception {
        
    	Gson gson = new Gson();
    	
    	System.out.println("GET request to thread id 2");
    	
    	new ClientResource("http://localhost:9000/2").get().write(System.out);
    	
    	System.out.println("\n\nPOST request to thread id 2");
    	
        new ClientResource("http://localhost:9000/2").post(gson.toJson(
        		new Post(0, "new post body", "new post title", "author", 2))
        		).write(System.out);
        
    	System.out.println("\n\nPOST request to root URL to create new thread");
        
        new ClientResource("http://localhost:9000").post(gson.toJson(
        		new Post(8, "new thread first post", "new thread", "author", 0))
        		).write(System.out);
        
//    	System.out.println("\n\nPOST request to root URL to create new post in thread 1");
    	
//        new ClientResource("http://localhost:8080").post(gson.toJson(
//        		new Post(8, "new thread first post", "new thread", "author", 1))
//        		).write(System.out);
    }
}