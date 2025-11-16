package it.tino.blog.rssfeed;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RssFeedDetailDto(
    @JsonProperty("id") UUID id,
    @JsonProperty("url") String url,
    @JsonProperty("description") String description,
    @JsonProperty("showArticlesDescription") boolean showArticlesDescription
) {}
