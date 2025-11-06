package it.tino.blog.rssarticle;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

record RssArticleSummaryDto(
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("creationDateTime") Instant creationDateTime,
    @JsonProperty("category") String category,
    @JsonProperty("categoryUrl") String categoryUrl,
    @JsonProperty("minutesToRead") int minutesToRead
) {}
