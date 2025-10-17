package it.tino.blog.article;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface ArticleDao extends JpaRepository<SpringArticle, UUID> {

    Optional<SpringArticle> findBySlug(String slug);
}
