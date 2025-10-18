package it.tino.blog.blogarticle;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
public class BlogArticleController {

    private final BlogArticleRepository articleRepository;
    private final BlogArticleDtoMapper blogArticleDtoMapper;

    public BlogArticleController(
        BlogArticleRepository articleRepository,
        BlogArticleDtoMapper blogArticleDtoMapper
    ) {
        this.articleRepository = articleRepository;
        this.blogArticleDtoMapper = blogArticleDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<BlogArticleSummaryDto>> getArticles(
        @RequestParam(name = "limit", required = false) Integer limit
    ) {
        List<BlogArticle> articles;
        if (limit == null || limit == 0) {
            articles = articleRepository.findAll();
        } else {
            articles = articleRepository.findWithLimit(limit);
        }

        List<BlogArticleSummaryDto> articlesDto = blogArticleDtoMapper.toListDto(articles);
        return new ResponseEntity<>(articlesDto, HttpStatus.OK);
    }

    /**
     * Search an article from both its UUID or slug.<br>
     * Most of the time the requests will use the slug, but UUID is still
     * supported to keep compatibility with previous version, since initially
     * search was only available by UUID.
     * @param identifier Contains the UUID of the article, or its slug.
     * @return The corresponding article.
     * @see <a href="https://en.wikipedia.org/wiki/Clean_URL#Slug">Slug |
     *      Wikipedia</a>
     */
    @GetMapping("{identifier}")
    public ResponseEntity<BlogArticleDetailDto> getArticle(@PathVariable String identifier) {
        Optional<BlogArticle> optionalBlogArticle;
        try {
            UUID uuid = UUID.fromString(identifier);
            optionalBlogArticle = articleRepository.findById(uuid);
        } catch (IllegalArgumentException e) {
            optionalBlogArticle = articleRepository.findBySlug(identifier);
        }

        if (optionalBlogArticle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BlogArticle article = optionalBlogArticle.get();
        BlogArticleDetailDto articleDto = blogArticleDtoMapper.toDetailDto(article);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BlogArticleDetailDto> createArticle(
        @RequestBody SaveBlogArticleDto articleCreateDto
    ) {
        BlogArticle article = blogArticleDtoMapper.toDomain(articleCreateDto);
        BlogArticle savedArticle = articleRepository.save(article);

        BlogArticleDetailDto savedArticleDto = blogArticleDtoMapper.toDetailDto(savedArticle);
        return new ResponseEntity<>(savedArticleDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BlogArticleDetailDto> updateArticle(
        @PathVariable UUID id,
        @RequestBody SaveBlogArticleDto createArticleDto
    ) {
        BlogArticle article = blogArticleDtoMapper.toDomain(createArticleDto);
        return articleRepository.findById(id)
                .map(a -> {
                    a.setTitle(article.getTitle());
                    a.setCreationDateTime(a.getCreationDateTime());
                    a.setShortDescription(article.getShortDescription());
                    a.setContent(article.getContent());

                    BlogArticle savedArticle = articleRepository.save(a);
                    BlogArticleDetailDto savedArticleDto = blogArticleDtoMapper
                            .toDetailDto(savedArticle);

                    return new ResponseEntity<>(savedArticleDto, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    article.setId(id);

                    BlogArticle savedArticle = articleRepository.save(article);
                    BlogArticleDetailDto savedArticleDto = blogArticleDtoMapper
                            .toDetailDto(savedArticle);

                    return new ResponseEntity<>(savedArticleDto, HttpStatus.CREATED);
                });
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID id) {
        if (articleRepository.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
