package it.tino.blog.rssarticle;

import java.util.Set;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.tino.blog.rssfeed.RssFeed;

@Service
class CachedRssArticleDownloader {

    private final RssArticleDownloader rssArticleDownloader;

    public CachedRssArticleDownloader(RssArticleDownloader rssArticleDownloader) {
        this.rssArticleDownloader = rssArticleDownloader;
    }

    @Cacheable(value = "rss-feed", key = "#rssFeed.id")
    public Set<RssArticle> readRssFeed(RssFeed rssFeed) {
        return rssArticleDownloader.fetchArticles(rssFeed);
    }

    @CacheEvict(value = "rss-feed", key = "#rssFeedId")
    public void evictFeedCache(UUID rssFeedId) {}

    @CacheEvict(value = "rss-feed", allEntries = true)
    public void evictAllCache() {}
}
