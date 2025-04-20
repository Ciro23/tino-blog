package it.tino.blog.rss;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RssFeedDao extends JpaRepository<SpringRssFeed, UUID> {}
