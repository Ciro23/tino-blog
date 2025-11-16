package it.tino.blog.rssarticle;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.tino.blog.rssfeed.RssFeedDetailDto;

record RssArticleDetailDto(
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("content") String content,
    @JsonProperty("creationDateTime") Instant creationDateTime,
    @JsonProperty("feed") RssFeedDetailDto feed,
    @JsonProperty("minutesToRead") int minutesToRead
) {}
