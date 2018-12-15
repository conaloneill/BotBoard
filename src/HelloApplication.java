import com.google.gson.Gson;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.routing.Router;

public class HelloApplication extends Application {
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// router URL for retrieving a quote URL link from the reference
		router.attach("/", new Restlet() {
			public void handle(Request request, Response response) {
				response.setStatus(Status.SUCCESS_OK);
				Gson gson = new Gson();
				response.setEntity(gson.toJson("Hello World"), MediaType.APPLICATION_JSON);
			}
		});
		return router;
	}
}
