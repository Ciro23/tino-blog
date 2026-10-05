package it.tino.blog.rssfeed;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.tino.blog.shared.PageRequest;
import it.tino.blog.shared.PageResult;

public interface RssFeedRepository {

    RssFeed save(RssFeed rssFeed);

    List<RssFeed> findAll();

    PageResult<RssFeed> findPage(PageRequest pageRequest);

    Optional<RssFeed> findById(UUID id);

    List<RssFeed> findByIdIn(Collection<UUID> ids);

    boolean deleteById(UUID id);
}
