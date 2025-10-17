package it.tino.blog.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
class BlogArticleDtoMapper {

    BlogArticleDetailDto toDetailDto(BlogArticle article) {
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

    List<BlogArticleDetailDto> toListDto(Collection<BlogArticle> articles) {
        Set<BlogArticle> sortedArticles = new TreeSet<>(articles);
        List<BlogArticleDetailDto> dtoList = new ArrayList<>();

        for (BlogArticle article : sortedArticles) {
            BlogArticleDetailDto dto = toDetailDto(article);
            dtoList.add(dto);
        }

        return dtoList;
    }

    BlogArticle toDomain(SaveBlogArticleDto dto) {
        BlogArticle article = new BlogArticle();
        article.setTitle(dto.getTitle());
        article.setSlug(dto.getSlug());
        article.setShortDescription(dto.getShortDescription());
        article.setContent(dto.getContent());

        return article;
    }
}
