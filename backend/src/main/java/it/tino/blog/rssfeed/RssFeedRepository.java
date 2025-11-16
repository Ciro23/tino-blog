package it.tino.blog.rssfeed;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RssFeedRepository {

    RssFeed save(RssFeed rssFeed);

    List<RssFeed> findAll();

    Optional<RssFeed> findById(UUID id);

    List<RssFeed> findByIdIn(Collection<UUID> ids);

    boolean deleteById(UUID id);
}
