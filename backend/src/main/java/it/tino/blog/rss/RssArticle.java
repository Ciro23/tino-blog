package it.tino.blog.rss;

import java.util.Objects;

import it.tino.blog.article.Article;
import it.tino.blog.util.Urls;

public class RssArticle extends Article {

    private String category;
    private String categoryUrl;

    @Override
    public String getSlug() {
        return Urls.makeStringUrlCompatible(getTitle());
    }

    @Override
    public void setContent(String content) {
        // Don't let the user exits this beautiful website!
        String parsedContent = content.replaceAll("<a", "<a target=\"_blank\" ");
        super.setContent(parsedContent);
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((categoryUrl == null) ? 0 : categoryUrl.hashCode());
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

        if (!super.equals(obj)) {
            return false;
        }

        RssArticle other = (RssArticle) obj;
        return Objects.equals(category, other.category)
                && Objects.equals(categoryUrl, other.categoryUrl);
    }
}
