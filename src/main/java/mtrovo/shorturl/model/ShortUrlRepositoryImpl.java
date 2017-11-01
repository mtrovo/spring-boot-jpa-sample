package mtrovo.shorturl.model;

import mtrovo.shorturl.Base58RandomStringSupplier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Repository
public class ShortUrlRepositoryImpl implements ShortUrlRepository {

    private final Supplier<String> sup;

    @PersistenceContext
    private EntityManager em;

    private Lock lock = new ReentrantLock();

    public ShortUrlRepositoryImpl(@Named("short-url-length") Integer length) {
        this.sup = new Base58RandomStringSupplier(length);
    }

    @Override
    @Transactional
    public CompletableFuture<Optional<ShortUrl>> insert(String url) {

        return CompletableFuture.completedFuture(insertSync(url)).thenApply(Optional::of);
    }

    @Transactional
    private ShortUrl insertSync(String url) {
        lock.lock();
        try {
            String id;
            boolean exists;

            do {
                id = sup.get();
                exists = this.em.createQuery("SELECT count(s) > 0 FROM short_url s WHERE s.id = :id", Boolean.class).setParameter("id", id).getSingleResult();
            } while (exists);

            final ShortUrl surl = new ShortUrl(id, url);
            this.em.persist(surl.toEntity());
            return surl;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public CompletableFuture<Optional<ShortUrl>> get(final String id) {
        return CompletableFuture
                .supplyAsync(() -> this.em.find(ShortUrlDB.class, id))
                .thenApply((surl) -> Optional.ofNullable(surl).map(ShortUrlDB::toShortUrl));
    }
}
