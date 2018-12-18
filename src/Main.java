import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class Main {
	
	public static void main(String args[]) throws Exception {
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 9000);
		component.getDefaultHost().
				attach("", new HelloApplication());
		component.start();
//
//		Server server = new Server(Protocol.HTTP, 9000);
//		server.start();
	}
}
