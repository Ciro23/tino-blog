package it.tino.blog.rss;

import it.tino.blog.util.Urls;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Repository
@RequiredArgsConstructor
@Log4j2
public class RssArticleDataSource implements RssArticleRepository {

    private final RssFeedRepository rssFeedRepository;
    private final CachedRssFeedReader cachedRssFeedReader;

    @Override
    public Optional<RssArticle> findBySlug(String slug) {
        Set<RssArticle> rssArticles = findAll();
        return rssArticles
                .stream()
                .filter(a -> Urls.makeStringUrlCompatible(a.getSlug()).equals(slug))
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
