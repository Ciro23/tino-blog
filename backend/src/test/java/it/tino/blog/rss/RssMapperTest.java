package it.tino.blog.rss;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;

class RssMapperTest {

    private final RssMapper mapper = new RssMapper();

    @Test
    void summaryOnlyAtomEntryUsesSummaryAsContentAndDescription() throws Exception {
        String xml = """
            <?xml version="1.0" encoding="utf-8"?>
            <feed xmlns="http://www.w3.org/2005/Atom">
              <title type="text">The Dart Blog</title>
              <id>https://dart.dev/blog/feed.xml</id>
              <updated>2026-09-15T00:00:00.000Z</updated>
              <entry>
                <title type="text">Announcing Dart 3.13</title>
                <link rel="alternate" type="text/html" href="https://dart.dev/blog/announcing-dart-3-13"/>
                <id>https://dart.dev/blog/announcing-dart-3-13</id>
                <updated>2026-08-12T00:00:00.000Z</updated>
                <published>2026-08-12T00:00:00.000Z</published>
                <summary type="text">Language &amp; tooling.</summary>
              </entry>
            </feed>
            """;

        RssEntry entry = parse(xml);

        assertEquals("Language & tooling.", entry.content());
        assertEquals("Language & tooling.", entry.description());
        assertEquals("https://dart.dev/blog/announcing-dart-3-13", entry.link());
    }

    @Test
    void rssEntryWithHtmlDescriptionKeepsItAsContentOnly() throws Exception {
        String xml = """
            <?xml version="1.0" encoding="utf-8"?>
            <rss version="2.0">
              <channel>
                <title>Some blog</title>
                <link>https://example.com</link>
                <description>Blog</description>
                <item>
                  <title>Post</title>
                  <link>https://example.com/post</link>
                  <pubDate>Mon, 01 Sep 2025 00:00:00 GMT</pubDate>
                  <description>&lt;p&gt;Full &lt;b&gt;article&lt;/b&gt;&lt;/p&gt;</description>
                </item>
              </channel>
            </rss>
            """;

        RssEntry entry = parse(xml);

        assertEquals("<p>Full <b>article</b></p>", entry.content());
        assertEquals("", entry.description());
        assertEquals("https://example.com/post", entry.link());
    }

    @Test
    void entryWithContentUsesSummaryAsDescription() throws Exception {
        String xml = """
            <?xml version="1.0" encoding="utf-8"?>
            <feed xmlns="http://www.w3.org/2005/Atom">
              <title>Blog</title>
              <id>https://example.com/feed</id>
              <updated>2026-09-15T00:00:00Z</updated>
              <entry>
                <title>Post</title>
                <link href="https://example.com/post"/>
                <id>https://example.com/post</id>
                <updated>2026-09-15T00:00:00Z</updated>
                <summary>Short</summary>
                <content type="html">&lt;p&gt;Long&lt;/p&gt;</content>
              </entry>
            </feed>
            """;

        RssEntry entry = parse(xml);

        assertEquals("<p>Long</p>", entry.content());
        assertEquals("Short", entry.description());
    }

    private RssEntry parse(String xml) throws Exception {
        SyndFeed feed = new SyndFeedInput().build(new StringReader(xml.strip()));
        // Mirrors SimpleRssFeedFetcher.validateEntry.
        feed.getEntries()
                .forEach(e -> {
                    if (e.getPublishedDate() == null) {
                        e.setPublishedDate(e.getUpdatedDate());
                    }
                });
        return mapper.feedToDto("url", feed)
                .entries()
                .getFirst();
    }
}
