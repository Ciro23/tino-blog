package it.tino.blog.rssarticle;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.tino.blog.rss.CachedRssFeedFetcher;
import it.tino.blog.rssfeed.RssFeed;

@Service
public class RssArticleService {

    private final RssArticleRepository rssArticleRepository;
    private final CachedRssFeedFetcher cachedRssFeedFetcher;

    public RssArticleService(
        RssArticleRepository rssArticleRepository,
        CachedRssFeedFetcher cachedRssFeedFetcher
    ) {
        this.rssArticleRepository = rssArticleRepository;
        this.cachedRssFeedFetcher = cachedRssFeedFetcher;
    }

    public Optional<RssArticle> getBySlug(String slug) {
        return rssArticleRepository.findBySlug(slug);
    }

    public List<RssArticle> getAll() {
        return rssArticleRepository.findAll();
    }

    public List<RssArticle> getByFeed(RssFeed rssFeed) {
        return rssArticleRepository.findByFeed(rssFeed);
    }

    public void reloadCache() {
        cachedRssFeedFetcher.evictAllCache();
    }
}
