package it.tino.blog.rssarticle;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

class RssArticleDetailDto {

    @JsonProperty("title")
    private String title = "";

    @JsonProperty("slug")
    private String slug = "";

    @JsonProperty("shortDescription")
    private String shortDescription = "";

    @JsonProperty("content")
    private String content = "";

    @JsonProperty("creationDateTime")
    private Instant creationDateTime;

    @JsonProperty("category")
    private String category;

    @JsonProperty("categoryUrl")
    private String categoryUrl;

    @JsonProperty("minutesToRead")
    private int minutesToRead;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public int getMinutesToRead() {
        return minutesToRead;
    }

    public void setMinutesToRead(int minutesToRead) {
        this.minutesToRead = minutesToRead;
    }
}
