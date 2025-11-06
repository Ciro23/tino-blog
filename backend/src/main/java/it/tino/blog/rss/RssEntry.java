package it.tino.blog.rss;

import java.time.Instant;

import org.jspecify.annotations.Nullable;

public record RssEntry(
    String title,
    String description,
    String content,
    Instant publishedDate,
    @Nullable Instant updatedDate
) {}
