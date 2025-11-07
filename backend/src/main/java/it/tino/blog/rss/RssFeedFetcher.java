package it.tino.blog.rss;

import java.util.Collection;
import java.util.List;

public interface RssFeedFetcher {

    /**
     * Downloads all RSS articles for the specified RSS feed URL from the
     * internet.
     */
    RssFeed fetchFeed(String rssFeedUrl);

    /**
     * Downloads all RSS articles for all the specified RSS feed URLs from the
     * internet. Each feed is fetchd asynchronously!<br>
     * If the fetch of any of the RSS feeds fails, this method will still try to
     * fetch the others.
     */
    List<RssFeed> fetchFeeds(Collection<String> rssFeedUrls);
}
