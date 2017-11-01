package mtrovo.shorturl.model;


import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ShortUrlRepository {
    CompletableFuture<Optional<ShortUrl>> insert(String url);
    CompletableFuture<Optional<ShortUrl>> get(String id);
}
