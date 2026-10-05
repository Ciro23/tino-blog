package it.tino.blog.rss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RssContentCleanerTest {

    private static final String BASE_URL = "https://example.com/blog/post/";

    @Test
    public void shouldResolveRootRelativeUrl() {
        String actual = RssContentCleaner.clean("<img src=\"/images/a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://example.com/images/a.png\">", actual);
    }

    @Test
    public void shouldResolvePathRelativeUrl() {
        String actual = RssContentCleaner.clean("<img src=\"a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://example.com/blog/post/a.png\">", actual);
    }

    @Test
    public void shouldResolveParentRelativeUrl() {
        String actual = RssContentCleaner.clean("<img src=\"../a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://example.com/blog/a.png\">", actual);
    }

    @Test
    public void shouldResolveProtocolRelativeUrl() {
        String actual = RssContentCleaner.clean("<img src=\"//cdn.com/a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://cdn.com/a.png\">", actual);
    }

    @Test
    public void shouldResolveAgainstBaseWithoutPath() {
        String actual = RssContentCleaner.clean("<img src=\"a.png\">", "https://example.com");
        Assertions.assertEquals("<img src=\"https://example.com/a.png\">", actual);
    }

    @Test
    public void shouldNotChangeAbsoluteUrl() {
        String html = "<img src=\"https://other.com/a.png\">";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldNotChangeDataUrl() {
        String html = "<img src=\"data:image/png;base64,iVBORw0KGgo=\">";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldNotChangeAnchor() {
        String html = "<a href=\"#section\">Section</a>";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldResolveRelativeLinkAndOpenItInNewTab() {
        String actual = RssContentCleaner.clean("<a href=\"/other-post\">Other</a>", BASE_URL);
        Assertions.assertEquals(
            "<a href=\"https://example.com/other-post\" target=\"_blank\" rel=\"noopener\">Other</a>",
            actual
        );
    }

    @Test
    public void shouldOpenAbsoluteLinkInNewTab() {
        String actual = RssContentCleaner.clean("<a href=\"https://other.com/page\">Link</a>", BASE_URL);
        Assertions.assertEquals(
            "<a href=\"https://other.com/page\" target=\"_blank\" rel=\"noopener\">Link</a>",
            actual
        );
    }

    @Test
    public void shouldNotBreakTagsStartingWithA() {
        String html = "<p><abbr>RSS</abbr></p><audio src=\"https://example.com/a.mp3\"></audio>";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldResolveSrcset() {
        String html = "<img srcset=\"a.png 1x, /b.png 2x\">";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(
            "<img srcset=\"https://example.com/blog/post/a.png 1x, https://example.com/b.png 2x\">",
            actual
        );
    }

    @Test
    public void shouldResolveVideoPosterAndSource() {
        String html = "<video poster=\"p.jpg\"><source src=\"/v.mp4\"></video>";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(
            "<video poster=\"https://example.com/blog/post/p.jpg\" playsinline=\"\" webkit-playsinline=\"\">"
                    + "<source src=\"https://example.com/v.mp4\"></video>",
            actual
        );
    }

    @Test
    public void shouldStripInlineStyles() {
        String html = "<style>p { color: red; }</style><p style=\"color: red\">Text</p>";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals("<p>Text</p>", actual);
    }

    @Test
    public void shouldStripVideoSizeAndPlayInline() {
        String html = "<video width=\"640\" height=\"360\" controls></video>";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals("<video controls playsinline=\"\" webkit-playsinline=\"\"></video>", actual);
    }

    @Test
    public void shouldOnlyCleanUpWithInvalidBaseUrl() {
        String html = "<img src=\"/a.png\" style=\"width: 10px\">";
        Assertions.assertEquals("<img src=\"/a.png\">", RssContentCleaner.clean(html, ""));
        Assertions.assertEquals("<img src=\"/a.png\">", RssContentCleaner.clean(html, "not a url"));
    }

    @Test
    public void shouldKeepRestOfHtmlUnchanged() {
        String html = "<p>Language &amp; <b>tooling</b>.</p>\n<pre>  code\n</pre>";
        String actual = RssContentCleaner.clean(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }
}
