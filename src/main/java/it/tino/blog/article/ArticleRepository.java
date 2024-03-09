package it.tino.blog.article;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository {

    List<Article> findAll();
    Optional<Article> findById(UUID id);
}
