package it.tino.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping
    public ResponseEntity<Set<BlogArticle>> getArticles(
            @RequestParam(name = "limit", required = false) Integer limit
    ) {
        List<BlogArticle> articles;
        if (limit == null || limit == 0) {
            articles = articleRepository.findAll();
        } else {
            articles = articleRepository.findWithLimit(limit);
        }

        return new ResponseEntity<>(new TreeSet<>(articles), HttpStatus.OK);
    }

    /**
     * Search an article from both its UUID or slug.<br>
     * Most of the time the requests will use the slug, but UUID is still
     * support to keep compatibility previous version, since initially
     * the only search available was by UUID.
     * @param identifier Contains the UUID of the article, or its slug.
     * @return The corresponding article.
     * @see <a href="https://en.wikipedia.org/wiki/Clean_URL#Slug">Slug | Wikipedia</a>
     */
    @GetMapping("{identifier}")
    public ResponseEntity<BlogArticle> getArticle(@PathVariable String identifier) {
        Optional<BlogArticle> optionalBlogArticle;
        try {
            UUID uuid = UUID.fromString(identifier);
            optionalBlogArticle = articleRepository.findById(uuid);
        } catch (IllegalArgumentException e) {
            optionalBlogArticle = articleRepository.findBySlug(identifier);
        }

        return optionalBlogArticle
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BlogArticle> createArticle(@RequestBody BlogArticle article) {
        BlogArticle savedArticle = articleRepository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<BlogArticle> updateArticle(@PathVariable UUID id, @RequestBody BlogArticle article) {
        return articleRepository.findById(id)
                .map(a -> {
                    a.setTitle(article.getTitle());
                    a.setCreationDateTime(article.getCreationDateTime());
                    a.setShortDescription(article.getShortDescription());
                    a.setContent(article.getContent());

                    BlogArticle savedArticle = articleRepository.save(a);
                    return new ResponseEntity<>(savedArticle, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    BlogArticle savedArticle = articleRepository.save(article);
                    return new ResponseEntity<>(savedArticle, HttpStatus.OK);
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
