package it.tino.blog.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HtmlTest {

    private static final String BASE_URL = "https://example.com/blog/post/";

    @Test
    public void shouldResolveRootRelativeUrl() {
        String actual = Html.resolveRelativeUrls("<img src=\"/images/a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://example.com/images/a.png\">", actual);
    }

    @Test
    public void shouldResolvePathRelativeUrl() {
        String actual = Html.resolveRelativeUrls("<img src=\"a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://example.com/blog/post/a.png\">", actual);
    }

    @Test
    public void shouldResolveParentRelativeUrl() {
        String actual = Html.resolveRelativeUrls("<img src=\"../a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://example.com/blog/a.png\">", actual);
    }

    @Test
    public void shouldResolveProtocolRelativeUrl() {
        String actual = Html.resolveRelativeUrls("<img src=\"//cdn.com/a.png\">", BASE_URL);
        Assertions.assertEquals("<img src=\"https://cdn.com/a.png\">", actual);
    }

    @Test
    public void shouldResolveAgainstBaseWithoutPath() {
        String actual = Html.resolveRelativeUrls("<img src=\"a.png\">", "https://example.com");
        Assertions.assertEquals("<img src=\"https://example.com/a.png\">", actual);
    }

    @Test
    public void shouldNotChangeAbsoluteUrl() {
        String html = "<a href=\"https://other.com/page\">Link</a>";
        String actual = Html.resolveRelativeUrls(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldNotChangeDataUrl() {
        String html = "<img src=\"data:image/png;base64,iVBORw0KGgo=\">";
        String actual = Html.resolveRelativeUrls(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldNotChangeAnchor() {
        String html = "<a href=\"#section\">Section</a>";
        String actual = Html.resolveRelativeUrls(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }

    @Test
    public void shouldResolveRelativeLink() {
        String actual = Html.resolveRelativeUrls("<a href=\"/other-post\">Other</a>", BASE_URL);
        Assertions.assertEquals("<a href=\"https://example.com/other-post\">Other</a>", actual);
    }

    @Test
    public void shouldResolveSrcset() {
        String html = "<img srcset=\"a.png 1x, /b.png 2x\">";
        String actual = Html.resolveRelativeUrls(html, BASE_URL);
        Assertions.assertEquals(
            "<img srcset=\"https://example.com/blog/post/a.png 1x, https://example.com/b.png 2x\">",
            actual
        );
    }

    @Test
    public void shouldResolveVideoPosterAndSource() {
        String html = "<video poster=\"p.jpg\"><source src=\"/v.mp4\"></video>";
        String actual = Html.resolveRelativeUrls(html, BASE_URL);
        Assertions.assertEquals(
            "<video poster=\"https://example.com/blog/post/p.jpg\">"
                    + "<source src=\"https://example.com/v.mp4\"></video>",
            actual
        );
    }

    @Test
    public void shouldNotChangeHtmlWithInvalidBaseUrl() {
        String html = "<img src=\"/a.png\">";
        Assertions.assertEquals(html, Html.resolveRelativeUrls(html, ""));
        Assertions.assertEquals(html, Html.resolveRelativeUrls(html, "not a url"));
    }

    @Test
    public void shouldKeepRestOfHtmlUnchanged() {
        String html = "<p>Language &amp; <b>tooling</b>.</p>\n<pre>  code\n</pre>";
        String actual = Html.resolveRelativeUrls(html, BASE_URL);
        Assertions.assertEquals(html, actual);
    }
}
