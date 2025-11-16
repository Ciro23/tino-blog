package it.tino.blog.rssarticle;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.tino.blog.rssfeed.RssFeed;
import it.tino.blog.rssfeed.RssFeedDetailDto;
import it.tino.blog.rssfeed.RssFeedDtoMapper;
import it.tino.blog.rssfeed.RssFeedRepository;

@RestController
@RequestMapping("rss/articles")
public class RssArticleController {

    private final RssArticleService rssArticleService;
    private final RssArticleDtoMapper rssArticleDtoMapper;

    private final RssFeedRepository rssFeedRepository;
    private final RssFeedDtoMapper rssFeedDtoMapper;

    public RssArticleController(
        RssArticleService rssArticleService,
        RssArticleDtoMapper rssArticleDtoMapper,
        RssFeedRepository rssFeedRepository,
        RssFeedDtoMapper rssFeedDtoMapper
    ) {
        this.rssArticleService = rssArticleService;
        this.rssArticleDtoMapper = rssArticleDtoMapper;
        this.rssFeedRepository = rssFeedRepository;
        this.rssFeedDtoMapper = rssFeedDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<RssArticleSummaryDto>> getArticles() {
        List<RssArticle> articles = rssArticleService.getAll();
        List<UUID> feedIds = articles.stream()
                .map(RssArticle::getRssFeedId)
                .toList();

        List<RssFeed> feeds = rssFeedRepository.findByIdIn(feedIds);
        Map<UUID, String> feedTitlesById = feeds.stream()
                .collect(Collectors.toMap(RssFeed::getId, RssFeed::getDescription));

        List<RssArticleSummaryDto> articlesDto = rssArticleDtoMapper
                .toListDto(articles, feedTitlesById);

        return new ResponseEntity<>(articlesDto, HttpStatus.OK);
    }

    @GetMapping("{slug}")
    public ResponseEntity<RssArticleDetailDto> getArticle(@PathVariable String slug) {
        Optional<RssArticle> optionalArticle = rssArticleService.getBySlug(slug);
        if (optionalArticle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RssArticle article = optionalArticle.get();

        RssFeed feed = rssFeedRepository.findById(article.getRssFeedId())
                .get();
        RssFeedDetailDto feedDto = rssFeedDtoMapper.toDetailDto(feed);

        RssArticleDetailDto articleDto = rssArticleDtoMapper.toDetailDto(article, feedDto);

        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @PostMapping("reload")
    public ResponseEntity<Void> reloadRssFeedsCache() {
        rssArticleService.reloadCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
