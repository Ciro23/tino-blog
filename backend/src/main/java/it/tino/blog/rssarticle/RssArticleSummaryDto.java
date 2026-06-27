package it.tino.blog.rssarticle;

import com.fasterxml.jackson.annotation.JsonProperty;

record RssArticleSummaryDto(
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("createdAt") String createdAt,
    @JsonProperty("feedTitle") String feedTitle,
    @JsonProperty("minutesToRead") int minutesToRead
) {}
