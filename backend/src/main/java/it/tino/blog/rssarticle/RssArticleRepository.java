package it.tino.blog.rssarticle;

import java.util.List;
import java.util.Optional;

import it.tino.blog.rssfeed.RssFeed;

interface RssArticleRepository {

    Optional<RssArticle> findBySlug(String slug);

    List<RssArticle> findAll();

    List<RssArticle> findByFeed(RssFeed rssFeed);
}
