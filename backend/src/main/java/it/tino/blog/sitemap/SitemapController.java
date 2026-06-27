package it.tino.blog.sitemap;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitemapController {

    private final SitemapGenerator sitemapGenerator;

    public SitemapController(SitemapGenerator sitemapGenerator) {
        this.sitemapGenerator = sitemapGenerator;
    }

    @GetMapping(value = "/sitemap", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getSitemap() {
        String sitemapXml = sitemapGenerator.generateSitemap();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(sitemapXml);
    }
}
