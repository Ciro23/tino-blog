package it.tino.blog.article;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class Article implements Comparable<Article> {

    private UUID id;
    private String title = "";
    private String shortDescription = "";
    private String content = "";
    private Instant creationDateTime;

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault());
        return formatter.format(creationDateTime);
    }

    @Override
    public int compareTo(Article that) {
        int dateComparison = that.creationDateTime.compareTo(this.creationDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return this.id.compareTo(that.id);
    }
}
