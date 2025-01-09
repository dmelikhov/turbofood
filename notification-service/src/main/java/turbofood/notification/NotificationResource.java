package turbofood.notification;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/notifications")
public class NotificationResource {

    @Channel("notifications")
    Multi<byte[]> notifications;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> stream() {
        return notifications.onItem().transform(String::new);
    }

}
