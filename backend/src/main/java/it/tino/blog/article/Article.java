package it.tino.blog.article;

import java.time.Instant;

public interface Article {

    String getTitle();

    void setTitle(String title);

    String getSlug();

    void setSlug(String slug);

    String getShortDescription();

    void setShortDescription(String shortDescription);

    String getContent();

    void setContent(String content);

    Instant getCreationDateTime();

    void setCreationDateTime(Instant creationDateTime);

    default int getMinutesToRead() {
        if (getContent().isBlank()) {
            return 0;
        }

        int wordCount = getContent().trim()
                .split("\\s+").length;
        int averageWordPerMinute = 200;
        return (int) Math.ceil((double) wordCount / averageWordPerMinute);
    }
}
