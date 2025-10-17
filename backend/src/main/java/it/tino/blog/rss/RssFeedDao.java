package it.tino.blog.rss;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface RssFeedDao extends JpaRepository<SpringRssFeed, UUID> {}
