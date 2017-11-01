package mtrovo.shorturl;

import mtrovo.shorturl.api.ShortUrlController;
import mtrovo.shorturl.model.ShortUrl;
import mtrovo.shorturl.model.ShortUrlRepository;
import mtrovo.shorturl.model.ShortUrlRepositoryImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Named;
import javax.ws.rs.core.Application;

@Configuration
public class CoreConfiguration {

    @Bean(name="short-url-length")
    public Integer shortUrlLength() {
        return 6;
    }

}
