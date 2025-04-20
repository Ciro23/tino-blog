package it.tino.blog.article;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository {

    BlogArticle save(BlogArticle article);

    List<BlogArticle> findAll();

    List<BlogArticle> findWithLimit(int numberOfArticlesToLoad);

    Optional<BlogArticle> findById(UUID id);

    Optional<BlogArticle> findBySlug(String slug);

    boolean deleteById(UUID id);
}
