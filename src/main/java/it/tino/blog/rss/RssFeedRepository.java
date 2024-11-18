package it.tino.blog.rss;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RssFeedRepository {

    RssFeed save(RssFeed rssFeed);

    List<RssFeed> findAll();
    Optional<RssFeed> findById(UUID id);

    boolean deleteById(UUID id);
}
