package mtrovo.shorturl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;


public class ShortUrl {

    @JsonProperty
    public final String id;

    @JsonProperty
    public final String url;

    @JsonCreator
    public ShortUrl(@JsonProperty("id") String id, @JsonProperty("url") String url) {
        this.id = id;
        this.url = url;
    }

    public ShortUrlDB toEntity() {
        return new ShortUrlDB(this.id, this.url);
    }
}

@Entity(name="short_url")
class ShortUrlDB {
    @Id
    private String id;

    private String url;

    public ShortUrlDB() {
    }

    public ShortUrlDB(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public ShortUrl toShortUrl() {
        return new ShortUrl(this.id, this.url);
    }
}