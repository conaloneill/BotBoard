package server;

import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

// Contains main method to be run on the server.
// Sets up URL routes to different resources.
public class Server extends Application{
	
	// The "core" data structure of the BotBoard application
	// which stores each thread with all their posts.
	static List<MessageThread> threadList;
	
	public static void main(String [] args) throws Exception{	
		//Instantiate aforementioned data structure
		threadList = new LinkedList<>();
		
		//// Fill board with some data for test purposes////
		// Can be commented out if empty "Board" desired
		threadList.add(0, new MessageThread(1, "server.Post 1"));
		threadList.add(1, new MessageThread(2, "server.Post 2"));
		threadList.add(2, new MessageThread(3, "server.Post 3"));
		
		threadList.get(0).posts.add(0, new Post(1, "Body 1", "server.Post 1", "author", 1));
		threadList.get(0).posts.add(1, new Post(2, "Body 2", "server.Post 2", "author", 1));
		threadList.get(0).posts.add(2, new Post(3, "Body 3", "server.Post 3", "author", 1));
		threadList.get(1).posts.add(0, new Post(1, "Body 1", "server.Post 1", "author", 2));
		threadList.get(1).posts.add(1, new Post(2, "Body 2", "server.Post 2", "author", 2));
		threadList.get(1).posts.add(2, new Post(3, "Body 3", "server.Post 3", "author", 2));
		threadList.get(2).posts.add(0, new Post(1, "Body 1", "server.Post 1", "author", 3));
		threadList.get(2).posts.add(1, new Post(2, "Body 2", "server.Post 2", "author", 3));
		threadList.get(2).posts.add(2, new Post(3, "Body 3", "server.Post 3", "author", 3));
		/////////////////////////////////////////////////////
		
		//Create a new restlet component and attach a HTTP server connector to it
		Component component = new Component();
		component.getServers().add(Protocol.HTTP,  9000);
		
		//Create a new application
		Application application = new Server();
		
        // Attach the application to local host and start it
        component.getDefaultHost().attachDefault(application);
        component.start();
	}
	
	
	// Method to receive all incoming calls
	@Override
    public Restlet createInboundRoot() {
        // Create a router
        Router router = new Router(getContext());

        // Attach the resources to the router
        router.attach("/", MultipleThreadsResource.class);
        router.attach("/{threadid}", ThreadResource.class);
        router.attach("/{threadid}/{postid}", PostResource.class);

        // Return the root router
        return router;
    }
}