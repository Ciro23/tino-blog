package it.tino.blog.rssarticle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.stereotype.Component;

import it.tino.blog.rssfeed.RssFeedDetailDto;

@Component
class RssArticleDtoMapper {

    public RssArticleDetailDto toDetailDto(RssArticle article, RssFeedDetailDto feedDto) {
        return new RssArticleDetailDto(
            article.getTitle(),
            article.getSlug(),
            article.getShortDescription(),
            article.getContent(),
            article.getCreationDateTime(),
            feedDto,
            article.getMinutesToRead()
        );
    }

    public List<RssArticleSummaryDto> toListDto(
        Collection<RssArticle> articles,
        Map<UUID, String> feedTitlesById
    ) {
        Set<RssArticle> sortedArticles = new TreeSet<>(articles);
        List<RssArticleSummaryDto> dtoList = new ArrayList<>();

        for (RssArticle article : sortedArticles) {
            RssArticleSummaryDto dto = new RssArticleSummaryDto(
                article.getTitle(),
                article.getSlug(),
                article.getShortDescription(),
                article.getCreationDateTime(),
                feedTitlesById.get(article.getRssFeedId()),
                article.getMinutesToRead()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }
}
