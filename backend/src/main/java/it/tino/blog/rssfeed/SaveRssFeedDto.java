package it.tino.blog.rssfeed;

import com.fasterxml.jackson.annotation.JsonProperty;

record SaveRssFeedDto(
    @JsonProperty("url") String url,
    @JsonProperty("title") String title,
    @JsonProperty("showArticlesDescription") boolean showArticlesDescription
) {}
