package it.tino.blog.rssarticle;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rss/articles")
public class RssArticleController {

    private final RssArticleService rssArticleService;

    public RssArticleController(RssArticleService rssArticleService) {
        this.rssArticleService = rssArticleService;
    }

    @GetMapping
    public Set<RssArticle> getRssArticles() {
        return rssArticleService.getAll();
    }

    @GetMapping("{slug}")
    public ResponseEntity<RssArticle> getRssArticle(@PathVariable String slug) {
        Optional<RssArticle> optionalRssArticle = rssArticleService.getBySlug(slug);
        return optionalRssArticle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("reload")
    public ResponseEntity<Void> reloadRssFeedsCache() {
        rssArticleService.reloadCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
