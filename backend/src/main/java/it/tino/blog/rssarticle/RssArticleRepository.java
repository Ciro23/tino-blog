package it.tino.blog.rssarticle;

import java.util.List;
import java.util.Optional;

import it.tino.blog.rssfeed.RssFeed;
import it.tino.blog.shared.PageRequest;
import it.tino.blog.shared.PageResult;

interface RssArticleRepository {

    Optional<RssArticle> findBySlug(String slug);

    List<RssArticle> findAll();

    PageResult<RssArticle> findPage(PageRequest pageRequest);

    List<RssArticle> findByFeed(RssFeed rssFeed);
}
