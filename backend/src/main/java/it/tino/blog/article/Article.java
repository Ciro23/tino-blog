package it.tino.blog.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.tino.blog.util.Urls;
import lombok.Data;

import java.time.Instant;

@Data
public class Article implements Comparable<Article> {

    private String title = "";
    private String slug = "";
    private String shortDescription = "";

    private String category;
    private String categoryUrl;

    private String content = "";
    private Instant creationDateTime;

    public void setSlug(String slug) {
        this.slug = Urls.makeStringUrlCompatible(slug);
    }

    @JsonProperty("minutesToRead")
    public double getMinutesToRead() {
        if (content.isBlank()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        int averageWordPerMinute = 200;
        return Math.ceil((double) wordCount / averageWordPerMinute);
    }

    @Override
    public int compareTo(Article that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }

        return this.title.compareTo(that.title);
    }
}
