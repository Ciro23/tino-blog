package it.tino.blog.rssarticle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
class RssArticleDtoMapper {

    public RssArticleDetailDto toDetailDto(RssArticle article) {
        return new RssArticleDetailDto(
            article.getTitle(),
            article.getSlug(),
            article.getShortDescription(),
            article.getContent(),
            article.getCreationDateTime(),
            article.getCategory(),
            article.getCategoryUrl(),
            article.getMinutesToRead()
        );
    }

    public List<RssArticleSummaryDto> toListDto(Collection<RssArticle> articles) {
        Set<RssArticle> sortedArticles = new TreeSet<>(articles);
        List<RssArticleSummaryDto> dtoList = new ArrayList<>();

        for (RssArticle article : sortedArticles) {
            RssArticleSummaryDto dto = new RssArticleSummaryDto(
                article.getTitle(),
                article.getSlug(),
                article.getShortDescription(),
                article.getCreationDateTime(),
                article.getCategory(),
                article.getCategoryUrl(),
                article.getMinutesToRead()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }
}
