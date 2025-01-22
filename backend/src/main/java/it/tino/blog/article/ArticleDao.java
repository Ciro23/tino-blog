package it.tino.blog.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArticleDao extends JpaRepository<SpringArticle, UUID> {

    Optional<SpringArticle> findBySlug(String slug);
}
