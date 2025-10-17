package it.tino.blog.rss;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rss_feeds", schema = "public")
public class SpringRssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String url = "";

    @Column(nullable = false)
    private String description = "";

    // "columnDefinition" was added to allow Hibernate
    // to migrate existing rows automatically.
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean showArticlesDescription = true;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShowArticlesDescription() {
        return showArticlesDescription;
    }

    public void setShowArticlesDescription(boolean showArticlesDescription) {
        this.showArticlesDescription = showArticlesDescription;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (showArticlesDescription ? 1231 : 1237);
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

        SpringRssFeed other = (SpringRssFeed) obj;
        return showArticlesDescription == other.showArticlesDescription
                && Objects.equals(id, other.id) && Objects.equals(url, other.url)
                && Objects.equals(description, other.description);
    }
}
