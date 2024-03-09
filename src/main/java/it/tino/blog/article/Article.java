package it.tino.blog.article;

import lombok.Data;

import java.util.UUID;

@Data
public class Article {

    private UUID id;
    private String title;
    private String content;
}
