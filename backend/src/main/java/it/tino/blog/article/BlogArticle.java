package it.tino.blog.article;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlogArticle extends Article {

    private UUID id;

    @Override
    public int compareTo(Article that) {
        int result = super.compareTo(that);
        if (result == 0 && that instanceof BlogArticle) {
            return this.id.compareTo(((BlogArticle) that).id);
        }

        return result;
    }
}
