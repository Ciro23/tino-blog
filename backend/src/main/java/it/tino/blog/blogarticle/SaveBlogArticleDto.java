package it.tino.blog.blogarticle;

import com.fasterxml.jackson.annotation.JsonProperty;

record SaveBlogArticleDto(
    @JsonProperty("title") String title,
    @JsonProperty("slug") String slug,
    @JsonProperty("shortDescription") String shortDescription,
    @JsonProperty("content") String content
) {}
