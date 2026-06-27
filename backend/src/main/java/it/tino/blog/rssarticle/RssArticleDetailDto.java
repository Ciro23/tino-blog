package it.tino.blog.rssarticle;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.tino.blog.rssfeed.RssFeedDetailDto;

record RssArticleDetailDto(
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("content") String content,
    @JsonProperty("createdAt") String createdAt,
    @JsonProperty("feed") RssFeedDetailDto feed,
    @JsonProperty("minutesToRead") int minutesToRead
) {}
