package it.tino.blog.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Component
@RequiredArgsConstructor
public class RssAggregator {

    public Set<RssArticle> readRssFeeds(Collection<URL> urls) {
        Set<RssArticle> feeds = new TreeSet<>();
        for (URL url : urls) {
            Set<RssArticle> feed = readRssFeed(url);
            feeds.addAll(feed);
        }

        return feeds;
    }

    @SuppressWarnings("deprecation") // I don't really care.
    public Set<RssArticle> readRssFeed(URL url) {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed;
        try {
            feed = input.build(new XmlReader(url));
        } catch (FeedException | IOException e) {
            throw new RuntimeException(e);
        }

        List<SyndEntry> entries = feed.getEntries();
        Set<RssArticle> articles = new TreeSet<>();
        for (SyndEntry entry : entries) {
            RssArticle article = new RssArticle();
            article.setTitle(entry.getTitle());

            // Some RSS feeds put the content inside the <content> tag, while
            // others in <description>, that's the reason of the following conditions.

            if (!entry.getContents().isEmpty()) {
                article.setContent(entry.getContents().getFirst().getValue());
            }

            if (!entry.getContents().isEmpty() && entry.getDescription() != null) {
                article.setShortDescription(entry.getDescription().getValue());
            }

            if (entry.getContents().isEmpty() && entry.getDescription() != null) {
                article.setContent(entry.getDescription().getValue());
            }

            article.setCreationDateTime(entry.getPublishedDate().toInstant());

            articles.add(article);
        }

        return articles;
    }
}
