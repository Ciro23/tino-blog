package it.tino.blog.rssarticle;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.tino.blog.rssfeed.RssFeed;

@Service
public class RssArticleService {

    private final RssArticleRepository rssArticleRepository;
    private final CachedRssArticleDownloader cachedRssArticleDownloader;

    public RssArticleService(
        RssArticleRepository rssArticleRepository,
        CachedRssArticleDownloader cachedRssArticleDownloader
    ) {
        this.rssArticleRepository = rssArticleRepository;
        this.cachedRssArticleDownloader = cachedRssArticleDownloader;
    }

    public Optional<RssArticle> getBySlug(String slug) {
        return rssArticleRepository.findBySlug(slug);
    }

    public Set<RssArticle> getAll() {
        return rssArticleRepository.findAll();
    }

    public Set<RssArticle> getByFeed(RssFeed rssFeed) {
        return rssArticleRepository.findByFeed(rssFeed);
    }

    public void reloadCache() {
        cachedRssArticleDownloader.evictAllCache();
    }

    public void reloadFeedCache(RssFeed rssFeed) {
        cachedRssArticleDownloader.evictFeedCache(rssFeed.getUrl());
    }
}
