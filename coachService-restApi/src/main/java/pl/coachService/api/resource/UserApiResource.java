package pl.coachService.api.resource;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Component
@Path("/user")
public class UserApiResource {
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseData test(@Context HttpServletRequest request) {

        return new ResponseData(200, "Logout success");
    }

    class ResponseData {
        private int statusCode;
        private String message;
        private String description;

        public ResponseData(int statusCode, String name) {
            this.statusCode = statusCode;
            this.message = name;
            description = "";
        }

        public int getStatusCode() {
            return statusCode;
        }

        public int getResponseCode() {
            return Math.abs(message.hashCode() % Integer.MAX_VALUE);
        }

        public String getMessage() {
            return message;
        }

        public String getDescription() {
            return description;
        }

    }
}
