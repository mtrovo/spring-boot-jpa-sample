package mtrovo.shorturl.api;

import mtrovo.shorturl.model.ShortUrlRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Component
@Path("/")
public class ShortUrlController {

    private final ShortUrlRepository dao;

    @Inject
    public ShortUrlController(ShortUrlRepository dao) {
        this.dao = dao;
    }

    @GET
    @Path("/{id}")
    public CompletionStage<Response> redirectToUrl(@PathParam("id") String id) {
            return this.dao.get(id).thenApplyAsync(optSurl ->
                optSurl
                        .map((surl) -> Response.seeOther(URI.create(surl.url)).build())
                        .orElse(Response.status(Response.Status.NOT_FOUND).build())
            );
    }

    @POST
    public CompletionStage<Response> create(@FormParam("url") String url) throws ExecutionException, InterruptedException {
        return this.dao.insert(url)
                .thenApplyAsync(optSurl ->
                    optSurl.map(obj -> Response.created(URI.create("/" + obj.id)).build())
                    .orElse(Response.serverError().entity("could not insert").build()));
    }
}
