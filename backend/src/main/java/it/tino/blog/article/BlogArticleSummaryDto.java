package it.tino.blog.article;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

class BlogArticleSummaryDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title = "";

    @JsonProperty("slug")
    private String slug = "";

    @JsonProperty("shortDescription")
    private String shortDescription = "";

    @JsonProperty("creationDateTime")
    private Instant creationDateTime;

    @JsonProperty("minutesToRead")
    private int minutesToRead;

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

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public int getMinutesToRead() {
        return minutesToRead;
    }

    public void setMinutesToRead(int minutesToRead) {
        this.minutesToRead = minutesToRead;
    }
}
