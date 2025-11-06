package it.tino.blog.rssarticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.tino.blog.rss.RssEntry;
import it.tino.blog.rss.RssFeedFetcher;
import it.tino.blog.rss.RssParsingException;
import it.tino.blog.rssfeed.RssFeed;
import it.tino.blog.rssfeed.RssFeedRepository;
import it.tino.blog.util.Urls;

@Repository
class RssArticleDataSource implements RssArticleRepository {

    private static final Logger log = LoggerFactory.getLogger(RssArticleDataSource.class);

    private final RssFeedRepository rssFeedRepository;
    private final RssFeedFetcher rssFeedFetcher;

    public RssArticleDataSource(
        RssFeedRepository rssFeedRepository,
        RssFeedFetcher rssFeedFetcher
    ) {
        this.rssFeedRepository = rssFeedRepository;
        this.rssFeedFetcher = rssFeedFetcher;
    }

    @Override
    public Optional<RssArticle> findBySlug(String slug) {
        List<RssArticle> rssArticles = findAll();
        return rssArticles.stream()
                .filter(
                    a -> Urls.makeStringUrlCompatible(a.getSlug())
                            .equals(slug)
                )
                .findFirst();
    }

    @Override
    public List<RssArticle> findAll() {
        List<RssFeed> rssFeeds = rssFeedRepository.findAll();
        ArrayList<RssArticle> rssArticles = new ArrayList<>();
        for (RssFeed rssFeed : rssFeeds) {
            List<RssArticle> feed = findByFeed(rssFeed);
            rssArticles.addAll(feed);
        }

        return rssArticles;
    }

    @Override
    public List<RssArticle> findByFeed(RssFeed rssFeed) {
        return fetchArticles(rssFeed);
    }

    private List<RssArticle> fetchArticles(RssFeed rssFeed) {
        it.tino.blog.rss.RssFeed fetchedFeed;
        try {
            fetchedFeed = rssFeedFetcher.fetch(rssFeed.getUrl());
        } catch (RssParsingException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }

        List<RssEntry> entries = fetchedFeed.entries();
        List<RssArticle> articles = new ArrayList<>();

        for (var entry : entries) {
            RssArticle article = new RssArticle();
            article.setTitle(entry.title());
            article.setSlug(entry.title());
            article.setCreationDateTime(entry.publishedDate());
            article.setCategory(rssFeed.getDescription());
            article.setCategoryUrl(rssFeed.getUrl());
            article.setContent(entry.content());

            if (rssFeed.isShowArticlesDescription()) {
                article.setShortDescription(entry.description());
            }

            articles.add(article);
        }

        return articles;
    }
}
