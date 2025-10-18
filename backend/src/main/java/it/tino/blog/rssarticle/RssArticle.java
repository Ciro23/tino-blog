package it.tino.blog.rssarticle;

import java.time.Instant;
import java.util.Objects;

import it.tino.blog.article.Article;
import it.tino.blog.rssfeed.RssFeed;
import it.tino.blog.util.Urls;

/**
 * The RSS article is an article fetched from a {@link RssFeed}.<br>
 * It's published by other people over the internet, but also readable directly
 * from this application.
 */
public class RssArticle implements Article, Comparable<RssArticle> {

    private String title = "";
    private String slug = "";
    private String shortDescription = "";
    private String content = "";
    private Instant creationDateTime;
    private String category;
    private String categoryUrl;

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
        // Don't let the user exits this beautiful website!
        String parsedContent = content.replaceAll("<a", "<a target=\"_blank\" ");
        this.content = parsedContent;
    }

    @Override
    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    @Override
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

    @Override
    public int compareTo(RssArticle that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int titleComparison = this.title.compareToIgnoreCase(that.title);
        if (titleComparison != 0) {
            return titleComparison;
        }

        return this.slug.compareTo(((RssArticle) that).slug);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : slug.hashCode());
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

        RssArticle other = (RssArticle) obj;
        return Objects.equals(slug, other.slug);
    }
}
