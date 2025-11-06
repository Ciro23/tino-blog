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

@RestController
@RequestMapping("rss/feeds")
public class RssFeedController {

    private final RssFeedRepository rssFeedRepository;
    private final RssFeedDtoMapper rssFeedDtoMapper;

    public RssFeedController(
        RssFeedRepository rssFeedRepository,
        RssFeedDtoMapper rssFeedDtoMapper
    ) {
        this.rssFeedRepository = rssFeedRepository;
        this.rssFeedDtoMapper = rssFeedDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<RssFeedDetailDto>> getFeeds() {
        List<RssFeed> feeds = rssFeedRepository.findAll();
        List<RssFeedDetailDto> feedsDto = rssFeedDtoMapper.toListDto(feeds);

        return new ResponseEntity<>(feedsDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RssFeedDetailDto> getFeed(@PathVariable UUID id) {
        Optional<RssFeed> optionalFeed = rssFeedRepository.findById(id);
        if (optionalFeed.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RssFeed feed = optionalFeed.get();
        RssFeedDetailDto feedDto = rssFeedDtoMapper.toDetailDto(feed);

        return new ResponseEntity<>(feedDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RssFeedDetailDto> createFeed(@RequestBody SaveRssFeedDto saveFeedDto) {
        RssFeed feed = rssFeedDtoMapper.toDomain(saveFeedDto);
        RssFeed savedFeed = rssFeedRepository.save(feed);

        RssFeedDetailDto savedFeedDto = rssFeedDtoMapper.toDetailDto(savedFeed);
        return new ResponseEntity<>(savedFeedDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<RssFeedDetailDto> updateFeed(
        @PathVariable UUID id,
        @RequestBody SaveRssFeedDto saveFeedDto
    ) {
        RssFeed feed = rssFeedDtoMapper.toDomain(saveFeedDto);
        return rssFeedRepository.findById(id)
                .map(a -> {
                    a.setDescription(saveFeedDto.description());
                    a.setUrl(saveFeedDto.url());
                    a.setShowArticlesDescription(saveFeedDto.showArticlesDescription());

                    RssFeed savedFeed = rssFeedRepository.save(a);
                    RssFeedDetailDto savedFeedDto = rssFeedDtoMapper.toDetailDto(savedFeed);

                    return new ResponseEntity<>(savedFeedDto, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    feed.setId(id);
                    RssFeed savedFeed = rssFeedRepository.save(feed);
                    RssFeedDetailDto savedFeedDto = rssFeedDtoMapper.toDetailDto(savedFeed);

                    return new ResponseEntity<>(savedFeedDto, HttpStatus.CREATED);
                });
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable UUID id) {
        if (rssFeedRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
