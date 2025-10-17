package it.tino.blog.rss;

import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * I would have preferred to declare {@link #readRssFeed(RssFeed)} inside
 * {@link RssArticleDataSource}, but unfortunately a non-cached method cannot
 * call a cached method if defined in the same bean.<br>
 * Declaring the cached method in a separate bean was the simplest solution.
 */
@Component
public class CachedRssFeedReader {

    private final RssFeedReader rssFeedReader;

    public CachedRssFeedReader(RssFeedReader rssFeedReader) {
        this.rssFeedReader = rssFeedReader;
    }

    @Cacheable(value = "rss-feed", key = "#rssFeed.url")
    public Set<RssArticle> readRssFeed(RssFeed rssFeed) {
        return rssFeedReader.readRssFeed(rssFeed);
    }

    @CacheEvict(value = "rss-feed", key = "#rssFeedUrl")
    public void evictCache(String rssFeedUrl) {}

    @CacheEvict(value = "rss-feed", allEntries = true)
    public void evictAllCache() {}
}
