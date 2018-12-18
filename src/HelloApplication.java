import com.google.gson.Gson;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.routing.Router;

import java.util.LinkedList;
import java.util.List;

public class HelloApplication extends Application {
	Gson gson = new Gson();
	
	static List<Post> posts = new LinkedList<>();
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// router URL for retrieving a quote URL link from the reference
		router.attach("/post", new Restlet() {
			public void handle(Request request, Response response) {
				if (request.getMethod().equals(Method.GET)) {
					response.setStatus(Status.SUCCESS_OK);
					response.setEntity(gson.toJson("Hello World"), MediaType.APPLICATION_JSON);
					
				} else if (request.getMethod().equals(Method.POST)) {
					String json = request.getEntityAsText();
					Post post = gson.fromJson(json, Post.class);
					posts.add(post);
					response.setEntity(gson.toJson(posts), MediaType.APPLICATION_ALL_JSON);
					response.setStatus(Status.SUCCESS_OK);
				}
			}
		});
		return router;
	}
}
