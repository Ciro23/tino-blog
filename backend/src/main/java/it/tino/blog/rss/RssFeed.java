package it.tino.blog.rss;

import java.util.Objects;
import java.util.UUID;

public class RssFeed implements Comparable<RssFeed> {

    private UUID id;
    private String url = "";
    private String description = "";

    /**
     * The articles' preview also display a short description to describe the
     * content. However, some feeds tend to abuse the description, by making it
     * extra long and not useful at all, so it may be necessary to never display
     * it.
     */
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
    public int compareTo(RssFeed that) {
        int descriptionComparison = this.description.compareTo(that.description);
        if (descriptionComparison != 0) {
            return descriptionComparison;
        }

        return this.url.compareTo(that.url);
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

        RssFeed other = (RssFeed) obj;
        return showArticlesDescription == other.showArticlesDescription
                && Objects.equals(id, other.id) && Objects.equals(url, other.url)
                && Objects.equals(description, other.description);
    }
}
