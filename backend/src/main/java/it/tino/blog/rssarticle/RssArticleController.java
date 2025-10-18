package it.tino.blog.rssarticle;

import java.util.List;
import java.util.Optional;

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
    private final RssArticleDtoMapper rssArticleDtoMapper;

    public RssArticleController(
        RssArticleService rssArticleService,
        RssArticleDtoMapper rssArticleDtoMapper
    ) {
        this.rssArticleService = rssArticleService;
        this.rssArticleDtoMapper = rssArticleDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<RssArticleSummaryDto>> getArticles() {
        List<RssArticle> articles = rssArticleService.getAll();
        List<RssArticleSummaryDto> articlesDto = rssArticleDtoMapper.toListDto(articles);

        return new ResponseEntity<>(articlesDto, HttpStatus.OK);
    }

    @GetMapping("{slug}")
    public ResponseEntity<RssArticleDetailDto> getArticle(@PathVariable String slug) {
        Optional<RssArticle> optionalArticle = rssArticleService.getBySlug(slug);
        if (optionalArticle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RssArticle article = optionalArticle.get();
        RssArticleDetailDto articleDto = rssArticleDtoMapper.toDetailDto(article);

        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @PostMapping("reload")
    public ResponseEntity<Void> reloadRssFeedsCache() {
        rssArticleService.reloadCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
