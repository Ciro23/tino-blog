package it.tino.blog.rssarticle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import it.tino.blog.rss.RssEntry;
import it.tino.blog.rss.RssFeedFetcher;
import it.tino.blog.rssfeed.RssFeed;
import it.tino.blog.rssfeed.RssFeedRepository;
import it.tino.blog.util.Urls;

@Repository
class RssArticleDataSource implements RssArticleRepository {

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
        var rssFeedUrls = rssFeeds.stream()
                .map(RssFeed::getUrl)
                .collect(Collectors.toSet());
        var rssFeedsByUrl = rssFeeds.stream()
                .collect(Collectors.toMap(RssFeed::getUrl, Function.identity()));

        var fetchedFeeds = rssFeedFetcher.fetchFeeds(rssFeedUrls);
        List<RssArticle> rssArticles = new ArrayList<>();

        for (it.tino.blog.rss.RssFeed fetchedFeed : fetchedFeeds) {
            var rssFeed = rssFeedsByUrl.get(fetchedFeed.url());
            var rssArticle = mapRssEntries(rssFeed, fetchedFeed.entries());
            rssArticles.addAll(rssArticle);
        }

        return rssArticles;
    }

    @Override
    public List<RssArticle> findByFeed(RssFeed rssFeed) {
        var fetchedFeed = rssFeedFetcher.fetchFeed(rssFeed.getUrl());
        return mapRssEntries(rssFeed, fetchedFeed.entries());
    }

    private List<RssArticle> mapRssEntries(RssFeed rssFeed, Collection<RssEntry> rssEntries) {
        List<RssArticle> articles = new ArrayList<>();
        for (var entry : rssEntries) {
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
