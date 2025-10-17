package it.tino.blog.rss;

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

    private final RssArticleRepository rssArticleRepository;
    private final CachedRssFeedReader cachedRssFeedReader;

    public RssArticleController(
        RssArticleRepository rssArticleRepository,
        CachedRssFeedReader cachedRssFeedReader
    ) {
        this.rssArticleRepository = rssArticleRepository;
        this.cachedRssFeedReader = cachedRssFeedReader;
    }

    @GetMapping
    public Set<RssArticle> getRssArticles() {
        return rssArticleRepository.findAll();
    }

    @GetMapping("{slug}")
    public ResponseEntity<RssArticle> getRssArticle(@PathVariable String slug) {
        Optional<RssArticle> optionalRssArticle = rssArticleRepository.findBySlug(slug);
        return optionalRssArticle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("reload")
    public ResponseEntity<Void> reloadRssFeedsCache() {
        cachedRssFeedReader.evictAllCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
