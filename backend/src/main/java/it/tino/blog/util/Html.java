package it.tino.blog.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Html {

    private static final List<String> URL_ATTRIBUTES = List.of("src", "poster", "href");

    /**
     * Converts every relative URL inside the HTML into an absolute one, using
     * the specified base URL.<br>
     * Content coming from other websites (e.g. RSS feeds) is displayed on this
     * website, so a relative URL like "/images/a.png" would otherwise point to
     * this website instead of the original one.<br>
     * In-page anchors (e.g. "#section") are left untouched.
     * @param html The HTML fragment to fix.
     * @param baseUrl The absolute URL the relative URLs are relative to.
     * @return The HTML with absolute URLs, or the same HTML if the base URL is
     *         not a valid absolute URL.
     */
    public static String resolveRelativeUrls(String html, String baseUrl) {
        if (html.isBlank() || !isAbsoluteUrl(baseUrl)) {
            return html;
        }

        Document document = Jsoup.parseBodyFragment(html, baseUrl);
        document.outputSettings()
                .prettyPrint(false);

        for (String attribute : URL_ATTRIBUTES) {
            for (Element element : document.select("[" + attribute + "]")) {
                resolveAttribute(element, attribute);
            }
        }

        for (Element element : document.select("[srcset]")) {
            String srcset = resolveSrcset(element.attr("srcset"), baseUrl);
            element.attr("srcset", srcset);
        }

        return document.body()
                .html();
    }

    private static void resolveAttribute(Element element, String attribute) {
        String value = element.attr(attribute)
                .strip();
        if (value.isEmpty() || value.startsWith("#")) {
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
