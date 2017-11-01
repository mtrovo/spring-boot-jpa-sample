package mtrovo.shorturl.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(ShortUrlController.class);
    }
}
