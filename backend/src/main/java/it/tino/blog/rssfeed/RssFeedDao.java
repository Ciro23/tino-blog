package it.tino.blog.rssfeed;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

interface RssFeedDao extends JpaRepository<SpringRssFeed, UUID> {

    List<SpringRssFeed> findAllByIdIn(Collection<UUID> ids, Sort sort);
}
