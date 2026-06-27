package it.tino.blog.sitemap;

import java.util.List;

import org.springframework.stereotype.Service;

import it.tino.blog.blogarticle.BlogArticle;
import it.tino.blog.blogarticle.BlogArticleRepository;

@Service
class SitemapGenerator {

    private final BlogArticleRepository articleRepository;

    private final String appUrl = "https://tinoblog.net/";
    private final String blogArticlesUrl = appUrl + "articles/";
    private final String rssArticlesUrl = appUrl + "rss/";

    public SitemapGenerator(BlogArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * The sitemap explicitly declares all visitable page of a website.<br>
     * It helps search engines index a website (they actually suck without a
     * sitemap).
     * @see <a href=
     *      "https://developers.google.com/search/docs/crawling-indexing/sitemaps/build-sitemap">
     *      Build and submit a Google sitemap</a>
     */
    public String generateSitemap() {
        List<BlogArticle> articles = articleRepository.findAll();

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        // Homepage.
        xml.append("<url>");
        xml.append("<loc>");
        xml.append(appUrl);
        xml.append("</loc>");
        xml.append("<priority>1.0</priority>");
        xml.append("</url>");

        // Blog articles homepage.
        xml.append("  <url>\n");
        xml.append("    <loc>" + blogArticlesUrl + "</loc>\n");
        xml.append("    <priority>0.6</priority>\n");
        xml.append("  </url>\n");

        // All blog articles.
        // RSS articles should not be indexed, as they are not published
        // directly by this application, and they are just temporary, sometimes.
        for (BlogArticle article : articles) {
            xml.append("<url>");
            xml.append("<loc>");
            xml.append(blogArticlesUrl);
            xml.append(article.getSlug());
            xml.append("</loc>");

            xml.append("<lastmod>");
            xml.append(article.getCreationDateTime());
            xml.append("</lastmod>");
            xml.append("<priority>0.8</priority>");
            xml.append("</url>");
        }

        // RSS homepage.
        // The RSS home can be included just fine. It's different than indexing
        // all RSS articles individually.
        xml.append("<url>");
        xml.append("<loc>");
        xml.append(rssArticlesUrl);
        xml.append("</loc>");
        xml.append("<priority>0.5</priority>");
        xml.append("</url>");

        xml.append("</urlset>");

        return xml.toString();

    }
}
