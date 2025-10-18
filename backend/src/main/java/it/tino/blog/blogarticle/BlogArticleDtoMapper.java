package it.tino.blog.blogarticle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
class BlogArticleDtoMapper {

    public BlogArticleDetailDto toDetailDto(BlogArticle article) {
        BlogArticleDetailDto dto = new BlogArticleDetailDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setSlug(article.getSlug());
        dto.setShortDescription(article.getShortDescription());
        dto.setContent(article.getContent());
        dto.setCreationDateTime(article.getCreationDateTime());
        dto.setMinutesToRead(article.getMinutesToRead());

        return dto;
    }

    public List<BlogArticleSummaryDto> toListDto(Collection<BlogArticle> articles) {
        Set<BlogArticle> sortedArticles = new TreeSet<>(articles);
        List<BlogArticleSummaryDto> dtoList = new ArrayList<>();

        for (BlogArticle article : sortedArticles) {
            BlogArticleSummaryDto dto = new BlogArticleSummaryDto();
            dto.setId(article.getId());
            dto.setTitle(article.getTitle());
            dto.setSlug(article.getSlug());
            dto.setShortDescription(article.getShortDescription());
            dto.setCreationDateTime(article.getCreationDateTime());
            dto.setMinutesToRead(article.getMinutesToRead());

            dtoList.add(dto);
        }

        return dtoList;
    }

    public BlogArticle toDomain(SaveBlogArticleDto dto) {
        BlogArticle article = new BlogArticle();
        article.setTitle(dto.getTitle());
        article.setSlug(dto.getSlug());
        article.setShortDescription(dto.getShortDescription());
        article.setContent(dto.getContent());

        return article;
    }
}
