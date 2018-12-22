import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class Server extends Application{
	
	static List<MessageThread> threadList;
	
	public static void main(String [] args) throws Exception{
		/////
		threadList = new LinkedList<MessageThread>();
		threadList.add(0, new MessageThread(1, "Post 1"));
		threadList.add(1, new MessageThread(2, "Post 2"));
		threadList.add(2, new MessageThread(3, "Post 3"));
		
		threadList.get(0).posts.add(0, new Post(1, "Body 1", "Post 1", "author", 1));
		threadList.get(0).posts.add(1, new Post(2, "Body 2", "Post 2", "author", 1));
		threadList.get(0).posts.add(2, new Post(3, "Body 3", "Post 3", "author", 1));
		threadList.get(1).posts.add(0, new Post(1, "Body 1", "Post 1", "author", 2));
		threadList.get(1).posts.add(1, new Post(2, "Body 2", "Post 2", "author", 2));
		threadList.get(1).posts.add(2, new Post(3, "Body 3", "Post 3", "author", 2));
		threadList.get(2).posts.add(0, new Post(1, "Body 1", "Post 1", "author", 3));
		threadList.get(2).posts.add(1, new Post(2, "Body 2", "Post 2", "author", 3));
		threadList.get(2).posts.add(2, new Post(3, "Body 3", "Post 3", "author", 3));
		/////
		
		//Create a new restlet component and attach a HTTP server connector to it
		Component component = new Component();
		component.getServers().add(Protocol.HTTP,  9000);
		
		//Create a new application
		Application application = new Server();
		
        // Attach the application to the component and start it
        component.getDefaultHost().attachDefault(application);
        component.start();
	}
	
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