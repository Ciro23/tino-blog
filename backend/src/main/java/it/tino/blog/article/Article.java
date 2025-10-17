package it.tino.blog.article;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.tino.blog.util.Urls;

public class Article implements Comparable<Article> {

    private String title = "";
    private String slug = "";
    private String shortDescription = "";
    private String content = "";
    private Instant creationDateTime;

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
        this.slug = Urls.makeStringUrlCompatible(slug);
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

    @JsonProperty("minutesToRead")
    public double getMinutesToRead() {
        if (content.isBlank()) {
            return 0;
        }

        int wordCount = content.trim()
                .split("\\s+").length;
        int averageWordPerMinute = 200;
        return Math.ceil((double) wordCount / averageWordPerMinute);
    }

    @Override
    public int compareTo(Article that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }

        return this.title.compareToIgnoreCase(that.title);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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

        Article other = (Article) obj;
        return Objects.equals(title, other.title) && Objects.equals(slug, other.slug)
                && Objects.equals(shortDescription, other.shortDescription)
                && Objects.equals(content, other.content)
                && Objects.equals(creationDateTime, other.creationDateTime);
    }
}
