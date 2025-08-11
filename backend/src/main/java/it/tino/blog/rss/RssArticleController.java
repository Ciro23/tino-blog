package it.tino.blog.rss;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("rss/articles")
@RequiredArgsConstructor
public class RssArticleController {

    private final RssArticleRepository rssArticleRepository;
    private final CachedRssFeedReader cachedRssFeedReader;

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
