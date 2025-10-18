package it.tino.blog.rssarticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.tino.blog.rssfeed.RssFeed;
import it.tino.blog.rssfeed.RssFeedRepository;
import it.tino.blog.util.Urls;

@Repository
class RssArticleDataSource implements RssArticleRepository {

    private static final Logger log = LoggerFactory.getLogger(RssArticleDataSource.class);

    private final RssFeedRepository rssFeedRepository;
    private final CachedRssArticleDownloader cachedRssArticleDownloader;

    public RssArticleDataSource(
        RssFeedRepository rssFeedRepository,
        CachedRssArticleDownloader cachedRssArticleDownloader
    ) {
        this.rssFeedRepository = rssFeedRepository;
        this.cachedRssArticleDownloader = cachedRssArticleDownloader;
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
            try {
                List<RssArticle> feed = findByFeed(rssFeed);
                rssArticles.addAll(feed);
            } catch (RssParsingException e) {
                log.error(
                    "Error fetching articles of feed '" + rssFeed.getUrl() + "': " + e.getMessage(),
                    e
                );
            }
        }

        return rssArticles;
    }

    @Override
    public List<RssArticle> findByFeed(RssFeed rssFeed) {
        return cachedRssArticleDownloader.readRssFeed(rssFeed);
    }
}
