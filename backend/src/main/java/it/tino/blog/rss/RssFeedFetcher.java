package it.tino.blog.rss;

public interface RssFeedFetcher {

    /**
     * Downloads all RSS articles for the specified RSS feed URL from the
     * internet.
     */
    RssFeed fetch(String rssFeedUrl);
}
