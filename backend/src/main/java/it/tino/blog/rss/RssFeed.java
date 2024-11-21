package it.tino.blog.rss;

import lombok.Data;

import java.util.UUID;

@Data
public class RssFeed implements Comparable<RssFeed> {

    private UUID id;
    private String url = "";
    private String description = "";

    @Override
    public int compareTo(RssFeed that) {
        int descriptionComparison = this.description.compareTo(that.description);
        if (descriptionComparison != 0) {
            return descriptionComparison;
        }

        return this.url.compareTo(that.url);
    }
}
