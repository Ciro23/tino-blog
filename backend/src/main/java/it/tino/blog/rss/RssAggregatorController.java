package it.tino.blog.rss;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@RestController
@RequestMapping("rss")
@RequiredArgsConstructor
public class RssAggregatorController {

    private final RssFeedRepository rssFeedRepository;
    private final RssArticleRepository rssArticleRepository;
    private final CachedRssFeedReader cachedRssFeedReader;

    @GetMapping("feeds")
    public Set<RssFeed> getRssFeeds() {
        return new TreeSet<>(rssFeedRepository.findAll());
    }

    @GetMapping("feeds/{id}")
    public ResponseEntity<RssFeed> getRssFeed(@PathVariable UUID id) {
        return rssFeedRepository
                .findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RssFeed> createRssFeed(@RequestBody RssFeed rssFeed) {
        RssFeed savedRssFeed = rssFeedRepository.save(rssFeed);
        return new ResponseEntity<>(savedRssFeed, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RssFeed> updateRssFeed(@PathVariable UUID id, @RequestBody RssFeed rssFeed) {
        RssFeed savedFeed = rssFeedRepository.findById(id)
                .map(a -> {
                    a.setDescription(rssFeed.getDescription());
                    a.setUrl(rssFeed.getUrl());
                    a.setShowArticlesDescription(rssFeed.isShowArticlesDescription());

                    return rssFeedRepository.save(a);
                })
                .orElseGet(() -> rssFeedRepository.save(rssFeed));

        cachedRssFeedReader.evictCache(savedFeed.getUrl());
        return new ResponseEntity<>(savedFeed, HttpStatus.OK);
    }

    @DeleteMapping("feeds/{id}")
    public ResponseEntity<Void> deleteRssFeed(@PathVariable UUID id) {
        if (rssFeedRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("articles/reload")
    public ResponseEntity<Void> reloadRssFeedsCache() {
        cachedRssFeedReader.evictAllCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("articles/{slug}")
    public ResponseEntity<RssArticle> getRssArticle(@PathVariable String slug) {
        Optional<RssArticle> optionalRssArticle = rssArticleRepository.findBySlug(slug);
        return optionalRssArticle
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("articles")
    public Set<RssArticle> getRssArticles() {
        return rssArticleRepository.findAll();
    }
}
