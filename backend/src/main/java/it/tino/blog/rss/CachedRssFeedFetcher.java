package it.tino.blog.rss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CachedRssFeedFetcher implements RssFeedFetcher {

    private final SimpleRssFeedFetcher rssFeedFetcher;
    private final CacheManager cacheManager;

    public CachedRssFeedFetcher(
        SimpleRssFeedFetcher rssArticleDownloader,
        CacheManager cacheManager
    ) {
        this.rssFeedFetcher = rssArticleDownloader;
        this.cacheManager = cacheManager;
    }

    @Override
    @Cacheable(value = "rss-feed", key = "#rssFeedUrl")
    public RssFeed fetchFeed(String rssFeedUrl) {
        return rssFeedFetcher.fetchFeed(rssFeedUrl);
    }

    @Override
    public List<RssFeed> fetchFeeds(Collection<String> rssFeedUrls) {
        var rssFeedUrlsUnique = new HashSet<>(rssFeedUrls);
        Cache cache = cacheManager.getCache("rss-feed");

        List<String> uncachedUrls = new ArrayList<>();
        List<RssFeed> cachedRssFeeds = new ArrayList<>();

        for (String url : rssFeedUrlsUnique) {
            var valueInCache = cache.get(url);
            if (valueInCache == null) {
                uncachedUrls.add(url);
            } else {
                cachedRssFeeds.add((RssFeed) valueInCache.get());
            }
        }

        List<RssFeed> freshRssFeeds = new ArrayList<>();
        if (!uncachedUrls.isEmpty()) {
            freshRssFeeds = rssFeedFetcher.fetchFeeds(uncachedUrls);
            freshRssFeeds.forEach(f -> cache.put(f.url(), f));
        }

        List<RssFeed> allRssFeeds = new ArrayList<>(cachedRssFeeds.size() + freshRssFeeds.size());
        allRssFeeds.addAll(cachedRssFeeds);
        allRssFeeds.addAll(freshRssFeeds);

        return allRssFeeds;
    }

    @CacheEvict(value = "rss-feed", allEntries = true)
    public void evictAllCache() {}
}
