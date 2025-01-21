package it.tino.blog.article;

import lombok.Data;

import java.time.Instant;

@Data
public class Article implements Comparable<Article> {

    private String title = "";
    private String shortDescription = "";

    private String category;
    private String categoryUrl;

    private String content = "";
    private Instant creationDateTime;

    @Override
    public int compareTo(Article that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }

        return this.title.compareTo(that.title);
    }
}
