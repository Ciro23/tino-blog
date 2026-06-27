package it.tino.blog.sitemap;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.tino.blog.blogarticle.BlogArticle;
import it.tino.blog.blogarticle.BlogArticleRepository;

@RestController
public class SitemapController {

    private final BlogArticleRepository articleRepository;

    private final String appUrl = "https://tinoblog.net/";
    private final String articlesUrl = appUrl + "articles/";
    private final String rssArticlesUrl = appUrl + "rss/";

    public SitemapController(BlogArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * The sitemap helps Google index this website pages.<br>
     * It's generated automatically so that I can just forget about it.
     */
    @GetMapping(value = "/sitemap", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getSitemap() {
        List<BlogArticle> articles = articleRepository.findAll();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // Homepage.
        xml.append("  <url>\n");
        xml.append("    <loc>" + appUrl + "</loc>\n");
        xml.append("    <priority>1.0</priority>\n");
        xml.append("  </url>\n");

        // All blog articles.
        // RSS articles should not be indexed, as they are not published
        // directly by this application, and they are just temporary, sometimes.
        for (BlogArticle article : articles) {
            xml.append("  <url>\n");
            xml.append("    <loc>" + articlesUrl)
                    .append(article.getSlug())
                    .append("</loc>\n");
            xml.append("    <lastmod>")
                    .append(article.getCreationDateTime())
                    .append("</lastmod>\n");
            xml.append("    <priority>0.8</priority>\n");
            xml.append("  </url>\n");
        }

        // RSS Homepage.
        // The RSS home can be included just fine. It's different than indexing
        // all RSS articles individually.
        xml.append("  <url>\n");
        xml.append("    <loc>" + rssArticlesUrl + "</loc>\n");
        xml.append("    <priority>0.5</priority>\n");
        xml.append("  </url>\n");

        xml.append("</urlset>");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(xml.toString());
    }
}
