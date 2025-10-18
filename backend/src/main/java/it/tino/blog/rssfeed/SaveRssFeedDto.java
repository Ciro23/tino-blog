package it.tino.blog.rssfeed;

class SaveRssFeedDto {

    private String url = "";
    private String description = "";
    private boolean showArticlesDescription = true;

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
