package it.tino.blog.article;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class Article implements Comparable<Article> {

    private UUID id;
    private String title = "";
    private String shortDescription = "";
    private String content = "";
    private Instant creationDateTime;

    @Override
    public int compareTo(Article that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int titleComparison = this.title.compareTo(that.title);
        if (titleComparison != 0) {
            return titleComparison;
        }

        return this.id.compareTo(that.id);
    }
}
