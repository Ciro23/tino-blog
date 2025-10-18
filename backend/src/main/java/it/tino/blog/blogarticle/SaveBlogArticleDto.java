package it.tino.blog.blogarticle;

import com.fasterxml.jackson.annotation.JsonProperty;

class SaveBlogArticleDto {

    @JsonProperty("title")
    private String title = "";

    @JsonProperty("slug")
    private String slug = "";

    @JsonProperty("shortDescription")
    private String shortDescription = "";

    @JsonProperty("content")
    private String content = "";

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
}
