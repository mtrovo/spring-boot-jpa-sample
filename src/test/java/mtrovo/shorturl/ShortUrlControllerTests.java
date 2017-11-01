package mtrovo.shorturl;

import mtrovo.shorturl.api.ShortUrlController;
import mtrovo.shorturl.model.ShortUrl;
import mtrovo.shorturl.model.ShortUrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlControllerTests {

	@Autowired
	private ShortUrlController controller;

	@Autowired
	private ShortUrlRepository dao;

	@Test
	public void postUrl() throws ExecutionException, InterruptedException {
        Response resp = controller.create("http://example.com").toCompletableFuture().get();
        String generatedId = getGeneratedId(resp);
        assertTrue(dao.get(generatedId).get().isPresent());
	}

    private String getGeneratedId(Response resp) {
        String[] locationTokens = resp.getLocation().toString().split("/");
        return locationTokens[locationTokens.length - 1];
    }

    @Test
    public void getFound() throws ExecutionException, InterruptedException {
        Response resp = controller.create("http://example.com").toCompletableFuture().get();
        String generatedId = getGeneratedId(resp);

        Response respGet = controller.redirectToUrl(generatedId).toCompletableFuture().get();
        assertEquals("http://example.com", respGet.getLocation().toString());
    }

    @Test
    public void getNotFound() throws ExecutionException, InterruptedException {
        Response respGet = controller.redirectToUrl("42").toCompletableFuture().get();
        assertEquals(404, respGet.getStatus());
    }

}
