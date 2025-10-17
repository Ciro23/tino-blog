package it.tino.blog.rss;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.tino.blog.util.Urls;

@Repository
public class RssArticleDataSource implements RssArticleRepository {

    private static final Logger log = LoggerFactory.getLogger(RssArticleDataSource.class);

    private final RssFeedRepository rssFeedRepository;
    private final CachedRssFeedReader cachedRssFeedReader;

    public RssArticleDataSource(
        RssFeedRepository rssFeedRepository,
        CachedRssFeedReader cachedRssFeedReader
    ) {
        this.rssFeedRepository = rssFeedRepository;
        this.cachedRssFeedReader = cachedRssFeedReader;
    }

    @Override
    public Optional<RssArticle> findBySlug(String slug) {
        Set<RssArticle> rssArticles = findAll();
        return rssArticles.stream()
                .filter(
                    a -> Urls.makeStringUrlCompatible(a.getSlug())
                            .equals(slug)
                )
                .findFirst();
    }

    @Override
    public Set<RssArticle> findAll() {
        List<RssFeed> rssFeeds = rssFeedRepository.findAll();
        Set<RssArticle> rssArticles = new TreeSet<>();
        for (RssFeed rssFeed : rssFeeds) {
            try {
                Set<RssArticle> feed = findByFeed(rssFeed);
                rssArticles.addAll(feed);
            } catch (RssParsingException e) {
                log.error("Error fetching articles of feed '" + rssFeed.getUrl() + "'");
            }
        }

        return rssArticles;
    }

    @Override
    public Set<RssArticle> findByFeed(RssFeed rssFeed) {
        return cachedRssFeedReader.readRssFeed(rssFeed);
    }
}
