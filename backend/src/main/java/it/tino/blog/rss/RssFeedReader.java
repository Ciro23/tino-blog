package it.tino.blog.rss;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Component
public class RssFeedReader {

    private static final Logger log = LoggerFactory.getLogger(RssFeedReader.class);

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
            // others in <description>. That's the reason of the following
            // conditions.

            if (
                !entry.getContents()
                        .isEmpty()
            ) {
                article.setContent(
                    entry.getContents()
                            .getFirst()
                            .getValue()
                );
            }

            if (
                !entry.getContents()
                        .isEmpty() && entry.getDescription() != null
                        && rssFeed.isShowArticlesDescription()
            ) {
                article.setShortDescription(
                    entry.getDescription()
                            .getValue()
                );
            }

            if (
                entry.getContents()
                        .isEmpty() && entry.getDescription() != null
            ) {
                article.setContent(
                    entry.getDescription()
                            .getValue()
                );
            }

            Instant articleDate = parseArticleDate(entry);
            if (articleDate == null) {
                log.warn(
                    "The RSS entry from feed '{}' and with title '{}' does not"
                            + " declare a creation date, so it's being skipped",
                    rssFeed.getUrl(),
                    article.getTitle()
                );
                continue;
            }

            article.setCreationDateTime(articleDate);

            articles.add(article);
        }

        return articles;
    }

    private URL parseUrl(String urlString) {
        try {
            return URI.create(urlString)
                    .toURL();
        } catch (NullPointerException e) {
            String message = "Cannot parse the RSS feed URL because it's null.";
            log.error(message, e);
            throw new RssParsingException(message, e);
        } catch (IllegalArgumentException e) {
            String message = "Cannot parse the RSS feed URL: '" + urlString
                    + "'. It violates RFC 2396.";
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

    @Nullable
    private Instant parseArticleDate(SyndEntry entry) {
        if (entry.getPublishedDate() != null) {
            return entry.getPublishedDate()
                    .toInstant();
        }

        if (entry.getUpdatedDate() != null) {
            return entry.getUpdatedDate()
                    .toInstant();
        }

        return null;
    }
}
