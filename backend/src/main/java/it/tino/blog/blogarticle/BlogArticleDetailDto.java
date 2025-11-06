package it.tino.blog.blogarticle;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

record BlogArticleDetailDto(
    @JsonProperty("id") UUID id,
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("content") String content,
    @JsonProperty("creationDateTime") Instant creationDateTime,
    @JsonProperty("minutesToRead") int minutesToRead
) {}
