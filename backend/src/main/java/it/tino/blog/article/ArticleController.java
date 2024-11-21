package it.tino.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

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

    @GetMapping("{id}")
    public ResponseEntity<BlogArticle> getArticle(@PathVariable UUID id) {
        return articleRepository
                .findById(id)
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
