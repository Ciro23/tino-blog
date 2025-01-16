package it.tino.blog.rss;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.tino.blog.article.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RssArticle extends Article {

    private String id;

    @JsonProperty("category")
    private String originFeedDescription;

    @JsonProperty("categoryUrl")
    private String originFeedUrl;

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        id = title.toLowerCase().replaceAll("\\W", "-");
    }

    @Override
    public void setContent(String content) {
        // Don't let the user exits this beautiful website!
        String parsedContent = content.replaceAll("<a", "<a target=\"_blank\" ");
        super.setContent(parsedContent);
    }
}
