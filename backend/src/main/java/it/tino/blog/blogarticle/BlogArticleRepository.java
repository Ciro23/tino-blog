package it.tino.blog.blogarticle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogArticleRepository {

    BlogArticle save(BlogArticle article);

    List<BlogArticle> findAll();

    List<BlogArticle> findWithLimit(int numberOfArticlesToLoad);

    Optional<BlogArticle> findById(UUID id);

    Optional<BlogArticle> findBySlug(String slug);

    boolean deleteById(UUID id);
}
