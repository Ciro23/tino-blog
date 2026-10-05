package it.tino.blog.rss;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * RSS articles are published by other websites, but displayed on this one, so
 * their HTML must be adapted before being served to our users.
 */
class RssContentCleaner {

    private static final List<String> URL_ATTRIBUTES = List.of("src", "poster", "href");

    /**
     * Cleans up the HTML content of an RSS article:
     * <ul>
     * <li>Relative URLs are converted into absolute ones, using the base
     * URL;</li>
     * <li>Inline styles are removed.</li>
     * <li>Videos lose their fixed size and play inline on mobile.</li>
     * <li>Links open in a new tab.</li>
     * </ul>
     * @param html The HTML fragment to clean up.
     * @param baseUrl The absolute URL the relative URLs are relative to. If
     *        it's not a valid absolute URL, relative URLs are left untouched.
     * @return The cleaned up HTML.
     */
    public static String clean(String html, String baseUrl) {
        if (html.isBlank()) {
            return html;
        }

        Document document = Jsoup.parseBodyFragment(html, baseUrl);
        document.outputSettings()
                .prettyPrint(false);

        if (isAbsoluteUrl(baseUrl)) {
            resolveRelativeUrls(document, baseUrl);
        }

        stripInlineStyles(document);
        stripVideoSizeAttributes(document);
        fixVideoFullscreenOnMobile(document);
        openLinksInNewTab(document);

        return document.body()
                .html();
    }

    /**
     * A relative URL like "/images/a.png" would otherwise point to this website
     * instead of the original one.<br>
     * In-page anchors (e.g. "#section") are left untouched.
     */
    private static void resolveRelativeUrls(Document document, String baseUrl) {
        for (String attribute : URL_ATTRIBUTES) {
            for (Element element : document.select("[" + attribute + "]")) {
                resolveAttribute(element, attribute);
            }
        }

        for (Element element : document.select("[srcset]")) {
            String srcset = resolveSrcset(element.attr("srcset"), baseUrl);
            element.attr("srcset", srcset);
        }
    }

    private static void resolveAttribute(Element element, String attribute) {
        String value = element.attr(attribute)
                .strip();
        if (value.isEmpty() || isAnchor(value)) {
            return;
        }

        // Empty when the URL can't be resolved (e.g. "data:" URLs), so the
        // original value is kept.
        String absoluteUrl = element.absUrl(attribute);
        if (!absoluteUrl.isEmpty()) {
            element.attr(attribute, absoluteUrl);
        }
    }

    /**
     * Srcset is a list of "url descriptor" candidates, separated by commas. For
     * example: "a.png 1x, b.png 2x".
     */
    private static String resolveSrcset(String srcset, String baseUrl) {
        List<String> candidates = new ArrayList<>();
        for (String candidate : srcset.split(",")) {
            String[] parts = candidate.strip()
                    .split("\\s+", 2);
            if (parts[0].isEmpty()) {
                continue;
            }

            String absoluteUrl = StringUtil.resolve(baseUrl, parts[0]);
            if (absoluteUrl.isEmpty()) {
                absoluteUrl = parts[0];
            }

            candidates.add(parts.length > 1 ? absoluteUrl + " " + parts[1] : absoluteUrl);
        }

        return String.join(", ", candidates);
    }

    /**
     * RSS articles should not dare trying to override my style.
     */
    private static void stripInlineStyles(Document document) {
        document.select("[style]")
                .removeAttr("style");
        document.select("style")
                .remove();
    }

    private static void stripVideoSizeAttributes(Document document) {
        document.select("video")
                .removeAttr("width")
                .removeAttr("height");
    }

    private static void fixVideoFullscreenOnMobile(Document document) {
        document.select("video")
                .attr("playsinline", "")
                .attr("webkit-playsinline", "");
    }

    /**
     * Don't let the user exits this beautiful website!
     */
    private static void openLinksInNewTab(Document document) {
        for (Element link : document.select("a[href]")) {
            if (isAnchor(link.attr("href"))) {
                continue;
            }

            link.attr("target", "_blank");
            link.attr("rel", "noopener");
        }
    }

    private static boolean isAnchor(String url) {
        return url.strip()
                .startsWith("#");
    }

    private static boolean isAbsoluteUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            return URI.create(url)
                    .isAbsolute();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
