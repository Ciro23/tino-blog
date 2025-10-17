package it.tino.blog.rss;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rss/feeds")
public class RssFeedController {

    private final RssFeedRepository rssFeedRepository;
    private final CachedRssFeedReader cachedRssFeedReader;

    public RssFeedController(
        RssFeedRepository rssFeedRepository,
        CachedRssFeedReader cachedRssFeedReader
    ) {
        this.rssFeedRepository = rssFeedRepository;
        this.cachedRssFeedReader = cachedRssFeedReader;
    }

    @GetMapping
    public Set<RssFeed> getRssFeeds() {
        return new TreeSet<>(rssFeedRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<RssFeed> getRssFeed(@PathVariable UUID id) {
        return rssFeedRepository.findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RssFeed> createRssFeed(@RequestBody RssFeed rssFeed) {
        RssFeed savedRssFeed = rssFeedRepository.save(rssFeed);
        return new ResponseEntity<>(savedRssFeed, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RssFeed> updateRssFeed(
        @PathVariable UUID id,
        @RequestBody RssFeed rssFeed
    ) {
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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRssFeed(@PathVariable UUID id) {
        if (rssFeedRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
