package it.tino.blog.rss;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@RestController
@RequestMapping("rss")
@RequiredArgsConstructor
public class RssAggregatorController {

    private final RssFeedRepository rssFeedRepository;
    private final RssAggregator rssAggregator;

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
    @CacheEvict(value = "rss-articles", allEntries = true)
    public ResponseEntity<RssFeed> createRssFeed(@RequestBody RssFeed rssFeed) {
        RssFeed savedRssFeed = rssFeedRepository.save(rssFeed);
        return new ResponseEntity<>(savedRssFeed, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @CacheEvict(value = "rss-articles", allEntries = true)
    public ResponseEntity<RssFeed> updateRssFeed(@PathVariable UUID id, @RequestBody RssFeed rssFeed) {
        return rssFeedRepository.findById(id)
                .map(a -> {
                    a.setDescription(rssFeed.getDescription());
                    a.setUrl(rssFeed.getUrl());

                    RssFeed savedArticle = rssFeedRepository.save(a);
                    return new ResponseEntity<>(savedArticle, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    RssFeed savedArticle = rssFeedRepository.save(rssFeed);
                    return new ResponseEntity<>(savedArticle, HttpStatus.OK);
                });
    }

    @DeleteMapping("feeds/{id}")
    @CacheEvict(value = "rss-articles", allEntries = true)
    public ResponseEntity<Void> deleteRssFeed(@PathVariable UUID id) {
        if (rssFeedRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("articles")
    @Cacheable(value = "rss-articles")
    public Set<RssArticle> getCachedRssArticles() {
        return getRssArticles();
    }

    @GetMapping("articles/reload")
    @CacheEvict(value = "rss-articles", allEntries = true)
    public ResponseEntity<Void> reloadRssFeedsCache() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Set<RssArticle> getRssArticles() {
        List<RssFeed> rssFeeds = rssFeedRepository.findAll();
        return rssAggregator.readRssFeeds(rssFeeds);
    }
}
