package server;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.ext.swagger.Swagger2SpecificationRestlet;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

// Contains main method to be run on the server.
// Sets up URL routes to different resources.
public class Server extends Application{
	
	// The "core" data structure of the BotBoard application
	// which stores each thread with all their posts.
	static public List<MessageThread> threadList = new LinkedList<>();
	
	public static void main(String [] args) throws Exception{	
		//Create a new restlet component and attach a HTTP server connector to it
		Component component = new Component();
		component.getServers().add(Protocol.HTTP,  9000);
		
		//Create a new application
		Application application = new Server();
		
		//Enable CORS for swagger
	    CorsService corsService = new CorsService();         
	    corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
	    corsService.setAllowedCredentials(true);
	    application.getServices().add(corsService);
		
	    //Set application information
	    application.setDescription("Todo: Description");
	    application.setName("BotBoard");
	    
        // Attach the application to local host and start it
        component.getDefaultHost().attachDefault(application);
        component.start();
	}
	
	
	// Method to receive all incoming calls
	@Override
    public Restlet createInboundRoot() {
        // Create a router
        Router router = new Router(getContext());
        
        //Create and attach a Swagger 2.0 Restlet to generate API description 
		 Swagger2SpecificationRestlet swagger2SpecificationRestlet = new Swagger2SpecificationRestlet(this); 
		 swagger2SpecificationRestlet.setBasePath("http://localhost:9000/");
		 
		 swagger2SpecificationRestlet.attach(router);
        
        // Attach the resources to the router
        router.attach("/", MultipleThreadsResource.class);
        router.attach("/{threadid}", ThreadResource.class);
        router.attach("/{threadid}/{postid}", PostResource.class);
        
        // Return the root router
        return router;
    }
	
	// Find the index of a Thread in the Thread List by it's id
	public static int findIndexById(int id) {
		int i = 0;
		
		for(MessageThread t : threadList){
			if(t.info.getId() == id)
				break;
			i++;
		}
		
		//If there is no thread with such an id, return -1
		if(i == threadList.size() && threadList.get(i-1).info.getId() != id)
			return -1;
		
		return i;
	}
}