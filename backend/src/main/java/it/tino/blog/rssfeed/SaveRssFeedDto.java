package it.tino.blog.rssfeed;

import com.fasterxml.jackson.annotation.JsonProperty;

record SaveRssFeedDto(
    @JsonProperty("url") String url,
    @JsonProperty("description") String description,
    @JsonProperty("showArticlesDescription") boolean showArticlesDescription
) {}
