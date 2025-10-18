package it.tino.blog.blogarticle;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import it.tino.blog.article.Article;
import it.tino.blog.util.Urls;

/**
 * The blog article is the original content published by this application.
 */
public class BlogArticle implements Article, Comparable<BlogArticle> {

    private UUID id;
    private String title = "";
    private String slug = "";
    private String shortDescription = "";
    private String content = "";
    private Instant creationDateTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public void setSlug(String slug) {
        this.slug = Urls.makeStringUrlCompatible(slug);
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    @Override
    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public int compareTo(BlogArticle that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int titleComparison = this.title.compareToIgnoreCase(that.title);
        if (titleComparison != 0) {
            return titleComparison;
        }

        return this.id.compareTo(((BlogArticle) that).id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BlogArticle other = (BlogArticle) obj;
        return Objects.equals(id, other.id);
    }
}
