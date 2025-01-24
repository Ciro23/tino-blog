package it.tino.blog.rss;

import java.util.Optional;
import java.util.Set;

public interface RssArticleRepository {

    Optional<RssArticle> findBySlug(String slug);

    Set<RssArticle> findAll();
    Set<RssArticle> findByFeed(RssFeed rssFeed);
}
