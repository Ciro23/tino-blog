package it.tino.blog.rss;

import it.tino.blog.article.Article;
import it.tino.blog.util.Urls;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RssArticle extends Article {

    private String id;

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        id = Urls.makeStringUrlCompatible(title);
    }

    @Override
    public void setContent(String content) {
        // Don't let the user exits this beautiful website!
        String parsedContent = content.replaceAll("<a", "<a target=\"_blank\" ");
        super.setContent(parsedContent);
    }
}
