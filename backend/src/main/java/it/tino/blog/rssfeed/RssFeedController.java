package it.tino.blog.rssfeed;

import java.util.List;
import java.util.Optional;
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

import it.tino.blog.rssarticle.RssArticleService;

@RestController
@RequestMapping("rss/feeds")
public class RssFeedController {

    private final RssFeedRepository rssFeedRepository;
    private final RssArticleService rssArticleService;
    private final RssFeedDtoMapper rssFeedDtoMapper;

    public RssFeedController(
        RssFeedRepository rssFeedRepository,
        RssArticleService cachedRssFeedReader,
        RssFeedDtoMapper rssFeedDtoMapper
    ) {
        this.rssFeedRepository = rssFeedRepository;
        this.rssArticleService = cachedRssFeedReader;
        this.rssFeedDtoMapper = rssFeedDtoMapper;
    }

    @GetMapping
    public List<RssFeedDetailDto> getRssFeeds() {
        List<RssFeed> feeds = rssFeedRepository.findAll();
        return rssFeedDtoMapper.toListDto(feeds);
    }

    @GetMapping("{id}")
    public ResponseEntity<RssFeedDetailDto> getRssFeed(@PathVariable UUID id) {
        Optional<RssFeed> optionalFeed = rssFeedRepository.findById(id);
        if (optionalFeed.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RssFeed feed = optionalFeed.get();
        RssFeedDetailDto feedDto = rssFeedDtoMapper.toDetailDto(feed);

        return new ResponseEntity<>(feedDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RssFeedDetailDto> createRssFeed(@RequestBody SaveRssFeedDto saveFeedDto) {
        RssFeed feed = rssFeedDtoMapper.toDomain(saveFeedDto);
        RssFeed savedFeed = rssFeedRepository.save(feed);

        RssFeedDetailDto savedFeedDto = rssFeedDtoMapper.toDetailDto(savedFeed);
        return new ResponseEntity<>(savedFeedDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RssFeedDetailDto> updateRssFeed(
        @PathVariable UUID id,
        @RequestBody SaveRssFeedDto saveFeedDto
    ) {
        RssFeed feed = rssFeedDtoMapper.toDomain(saveFeedDto);
        RssFeed savedFeed = rssFeedRepository.findById(id)
                .map(a -> {
                    a.setDescription(saveFeedDto.getDescription());
                    a.setUrl(saveFeedDto.getUrl());
                    a.setShowArticlesDescription(saveFeedDto.isShowArticlesDescription());

                    return rssFeedRepository.save(a);
                })
                .orElseGet(() -> {
                    feed.setId(id);
                    return rssFeedRepository.save(feed);
                });

        rssArticleService.reloadFeedCache(savedFeed);

        RssFeedDetailDto savedFeedDto = rssFeedDtoMapper.toDetailDto(savedFeed);
        return new ResponseEntity<>(savedFeedDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRssFeed(@PathVariable UUID id) {
        if (rssFeedRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
