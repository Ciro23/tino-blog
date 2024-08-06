package it.tino.blog.article;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findAll();
    List<Article> findWithLimit(int numberOfArticlesToLoad);
    Optional<Article> findById(UUID id);

    boolean deleteById(UUID id);
}
