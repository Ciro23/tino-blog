package it.tino.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(
            @RequestParam(name = "limit", required = false) Integer limit
    ) {
        List<Article> articles;
        if (limit == null || limit == 0) {
            articles = articleRepository.findAll();
        } else {
            articles = articleRepository.findWithLimit(limit);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Article> getArticle(@PathVariable UUID id) {
        return articleRepository
                .findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article savedArticle = articleRepository.save(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable UUID id, @RequestBody Article article) {
        return articleRepository.findById(id)
                .map(a -> {
                    a.setTitle(article.getTitle());
                    a.setCreationDateTime(article.getCreationDateTime());
                    a.setShortDescription(article.getShortDescription());
                    a.setContent(article.getContent());

                    Article savedArticle = articleRepository.save(a);
                    return new ResponseEntity<>(savedArticle, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    Article savedArticle = articleRepository.save(article);
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
