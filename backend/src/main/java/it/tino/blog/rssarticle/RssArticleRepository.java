package it.tino.blog.rssarticle;

import java.util.Optional;
import java.util.Set;

import it.tino.blog.rssfeed.RssFeed;

interface RssArticleRepository {

    Optional<RssArticle> findBySlug(String slug);

    Set<RssArticle> findAll();

    Set<RssArticle> findByFeed(RssFeed rssFeed);
}
