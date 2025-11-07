package it.tino.blog.rss;

import java.util.List;

public record RssFeed(
    String url,
    List<RssEntry> entries
) {}
