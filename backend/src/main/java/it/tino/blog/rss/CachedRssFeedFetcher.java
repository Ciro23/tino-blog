package it.tino.blog.rss;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CachedRssFeedFetcher implements RssFeedFetcher {

    private final SimpleRssFeedFetcher rssFeedFetcher;

    public CachedRssFeedFetcher(SimpleRssFeedFetcher rssArticleDownloader) {
        this.rssFeedFetcher = rssArticleDownloader;
    }

    @Override
    @Cacheable(value = "rss-feed", key = "#rssFeedUrl")
    public RssFeed fetch(String rssFeedUrl) {
        return rssFeedFetcher.fetch(rssFeedUrl);
    }

    @CacheEvict(value = "rss-feed", allEntries = true)
    public void evictAllCache() {}
}
