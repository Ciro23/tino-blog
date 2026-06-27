package it.tino.blog.blogarticle;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

record BlogArticleSummaryDto(
    @JsonProperty("id") UUID id,
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("createdAt") String createdAt,
    @JsonProperty("minutesToRead") int minutesToRead
) {}
