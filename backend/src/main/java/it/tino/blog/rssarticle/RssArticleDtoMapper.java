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
        RssArticleDetailDto dto = new RssArticleDetailDto();
        dto.setTitle(article.getTitle());
        dto.setSlug(article.getSlug());
        dto.setShortDescription(article.getShortDescription());
        dto.setContent(article.getContent());
        dto.setCreationDateTime(article.getCreationDateTime());
        dto.setCategory(article.getCategory());
        dto.setCategoryUrl(article.getCategoryUrl());
        dto.setMinutesToRead(article.getMinutesToRead());

        return dto;
    }

    public List<RssArticleSummaryDto> toListDto(Collection<RssArticle> articles) {
        Set<RssArticle> sortedArticles = new TreeSet<>(articles);
        List<RssArticleSummaryDto> dtoList = new ArrayList<>();

        for (RssArticle article : sortedArticles) {
            RssArticleSummaryDto dto = new RssArticleSummaryDto();
            dto.setTitle(article.getTitle());
            dto.setSlug(article.getSlug());
            dto.setShortDescription(article.getShortDescription());
            dto.setCreationDateTime(article.getCreationDateTime());
            dto.setCategory(article.getCategory());
            dto.setCategoryUrl(article.getCategoryUrl());
            dto.setMinutesToRead(article.getMinutesToRead());

            dtoList.add(dto);
        }

        return dtoList;
    }
}
