package it.tino.blog.blogarticle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import it.tino.blog.shared.PageRequest;
import it.tino.blog.shared.PageResult;

public interface BlogArticleRepository {

    BlogArticle save(BlogArticle article);

    List<BlogArticle> findAll();

    PageResult<BlogArticle> findPage(PageRequest pageRequest);

    Optional<BlogArticle> findById(UUID id);

    Optional<BlogArticle> findBySlug(String slug);

    boolean deleteById(UUID id);
}
