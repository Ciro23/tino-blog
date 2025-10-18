package it.tino.blog.blogarticle;

import java.util.Objects;
import java.util.UUID;

import it.tino.blog.article.Article;

public class BlogArticle extends Article {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public int compareTo(Article that) {
        int result = super.compareTo(that);
        if (result == 0 && that instanceof BlogArticle) {
            return this.id.compareTo(((BlogArticle) that).id);
        }

        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        BlogArticle other = (BlogArticle) obj;
        return Objects.equals(id, other.id);
    }
}
