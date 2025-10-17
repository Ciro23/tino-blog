package it.tino.blog.article;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "articles", schema = "public")
class SpringArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title = "";

    @Column(nullable = false, updatable = false, unique = true)
    private String slug = "";

    @Column(nullable = false)
    private String shortDescription = "";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content = "";

    @Column(nullable = false)
    @CreationTimestamp
    private Instant creationDateTime;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((slug == null) ? 0 : slug.hashCode());
        result = prime * result + ((shortDescription == null) ? 0 : shortDescription.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((creationDateTime == null) ? 0 : creationDateTime.hashCode());
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

        SpringArticle other = (SpringArticle) obj;
        return Objects.equals(id, other.id) && Objects.equals(title, other.title)
                && Objects.equals(slug, other.slug)
                && Objects.equals(shortDescription, other.shortDescription)
                && Objects.equals(content, other.content)
                && Objects.equals(creationDateTime, other.creationDateTime);
    }
}
