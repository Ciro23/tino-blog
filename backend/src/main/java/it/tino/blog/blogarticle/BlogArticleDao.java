package it.tino.blog.blogarticle;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface ArticleDao extends JpaRepository<SpringBlogArticle, UUID> {

    Optional<SpringBlogArticle> findBySlug(String slug);
}
