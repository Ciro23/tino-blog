package it.tino.blog.rss;

import lombok.Data;

import java.util.UUID;

@Data
public class RssFeed implements Comparable<RssFeed> {

    private UUID id;
    private String url = "";
    private String description = "";

    
    /**
     * The articles' preview also display a short description
     * to describe the content. However, some feeds tend to abuse the
     * description, by making it extra long and not useful at all,
     * so it may be necessary to never display it.
     */
    private boolean showArticlesDescription = true;

    @Override
    public int compareTo(RssFeed that) {
        int descriptionComparison = this.description.compareTo(that.description);
        if (descriptionComparison != 0) {
            return descriptionComparison;
        }

        return this.url.compareTo(that.url);
    }
}
