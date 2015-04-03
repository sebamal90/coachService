package pl.coachService.api.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
    private ObjectMapper objectMapper;

    public JacksonContextResolver() {
        this.objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}
