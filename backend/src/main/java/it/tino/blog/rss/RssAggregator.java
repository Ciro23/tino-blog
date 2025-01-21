package it.tino.blog.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class RssAggregator {

    public Set<RssArticle> readRssFeeds(Collection<RssFeed> rssFeeds) {
        Set<RssArticle> rssArticles = new TreeSet<>();
        for (RssFeed rssFeed : rssFeeds) {
            try {
                Set<RssArticle> feed = readRssFeed(rssFeed);
                rssArticles.addAll(feed);
            } catch (RssParsingException e) {
                log.error("Error fetching articles of feed '" + rssFeed.getUrl() + "'");
            }
        }

        return rssArticles;
    }

    public Set<RssArticle> readRssFeed(RssFeed rssFeed) {
        URL url = parseUrl(rssFeed.getUrl());
        SyndFeed feed = parseFeed(url);

        List<SyndEntry> entries = feed.getEntries();
        Set<RssArticle> articles = new TreeSet<>();

        for (SyndEntry entry : entries) {
            RssArticle article = new RssArticle();
            article.setTitle(entry.getTitle());
            article.setCategory(rssFeed.getDescription());
            article.setCategoryUrl(rssFeed.getUrl());

            // Some RSS feeds put the content inside the <content> tag, while
            // others in <description>. That's the reason of the following conditions.

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

    private URL parseUrl(String urlString) {
        try {
            return URI.create(urlString).toURL();
        } catch (NullPointerException e) {
            String message = "Cannot parse the RSS feed URL because it's null.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        } catch (IllegalArgumentException e) {
            String message = "Cannot parse the RSS feed URL: '" + urlString + "'. It violates RFC 2396.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        } catch (MalformedURLException e) {
            String message = "Cannot parse the malformed RSS feed URL: '" + urlString + "'.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        }
    }

    @SuppressWarnings("deprecation") // I don't really care.
    private SyndFeed parseFeed(URL feedUrl) {
        SyndFeedInput input = new SyndFeedInput();
        try {
            HttpURLConnection connection = (HttpURLConnection) feedUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            XmlReader xmlReader = new XmlReader(connection);
            return input.build(xmlReader);
        } catch (ConnectException e) {
            String message = "Cannot connect to remote URL: '" + feedUrl + "'.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        } catch (FeedException e) {
            String message = "Cannot parse the RSS feed from the URL: '" + feedUrl + "'.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        } catch (IOException e) {
            String message = "Cannot read the stream of the URL: '" + feedUrl + "'.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        } catch (Exception e) {
            String message = "Unknown error: '" + feedUrl + "'.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        }
    }

}
