package it.tino.blog.rssfeed;

import java.util.UUID;

class RssFeedDetailDto {

    private UUID id;
    private String url = "";
    private String description = "";
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
}
