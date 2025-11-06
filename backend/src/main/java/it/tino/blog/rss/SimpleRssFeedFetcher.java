package it.tino.blog.rss;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class SimpleRssFeedFetcher implements RssFeedFetcher {

    private static final Logger log = LoggerFactory.getLogger(SimpleRssFeedFetcher.class);

    private final RssMapper rssMapper;

    public SimpleRssFeedFetcher(RssMapper rssMapper) {
        this.rssMapper = rssMapper;
    }

    @Override
    public RssFeed fetch(String rssFeedUrl) {
        URL url = parseUrl(rssFeedUrl);
        SyndFeed syndFeed = parseFeed(url);
        validateEntries(syndFeed.getEntries(), url);

        return rssMapper.feedToDto(syndFeed);
    }

    private URL parseUrl(String urlString) {
        try {
            return URI.create(urlString)
                    .toURL();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);

            String message = "Cannot parse the RSS feed URL because it's null.";
            throw new RssParsingException(message, e);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);

            String message = "Cannot parse the RSS feed URL: '" + urlString
                    + "'. It violates RFC 2396.";
            throw new RssParsingException(message, e);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);

            String message = "Cannot parse the malformed RSS feed URL: '" + urlString + "'.";
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
            log.error(e.getMessage(), e);

            String message = "Cannot connect to remote URL: '" + feedUrl + "'.";
            throw new RssParsingException(message, e);
        } catch (FeedException e) {
            log.error(e.getMessage(), e);

            String message = "Cannot parse the RSS feed from the URL: '" + feedUrl + "'.";
            throw new RssParsingException(message, e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);

            String message = "Cannot read the stream of the URL: '" + feedUrl + "'.";
            throw new RssParsingException(message, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            String message = "Unknown error: '" + feedUrl + "'.";
            throw new RssParsingException(message, e);
        }
    }

    private void validateEntries(Collection<SyndEntry> entries, URL rssFeedUrl) {
        for (var entry : entries) {
            try {
                validateEntry(entry);
            } catch (IllegalArgumentException e) {
                log.warn(
                    "The RSS entry from feed '{}' and with title '{}' does not"
                            + " declare a creation date, so it's being skipped",
                    rssFeedUrl,
                    entry.getTitle()
                );
                continue;
            }
        }
    }

    private void validateEntry(SyndEntry entry) {
        Date date = parseArticleDate(entry);
        if (date == null) {
            throw new IllegalArgumentException("Entry does not declare a creation date");
        }

        entry.setPublishedDate(date);
    }

    private @Nullable Date parseArticleDate(SyndEntry entry) {
        if (entry.getPublishedDate() != null) {
            return entry.getPublishedDate();
        }

        if (entry.getUpdatedDate() != null) {
            return entry.getUpdatedDate();
        }

        return null;
    }
}
