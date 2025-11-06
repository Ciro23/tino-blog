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
        return new BlogArticleDetailDto(
            article.getId(),
            article.getTitle(),
            article.getSlug(),
            article.getShortDescription(),
            article.getContent(),
            article.getCreationDateTime(),
            article.getMinutesToRead()
        );
    }

    public List<BlogArticleSummaryDto> toListDto(Collection<BlogArticle> articles) {
        Set<BlogArticle> sortedArticles = new TreeSet<>(articles);
        List<BlogArticleSummaryDto> dtoList = new ArrayList<>();

        for (BlogArticle article : sortedArticles) {
            BlogArticleSummaryDto dto = new BlogArticleSummaryDto(
                article.getId(),
                article.getTitle(),
                article.getSlug(),
                article.getShortDescription(),
                article.getCreationDateTime(),
                article.getMinutesToRead()
            );
            dtoList.add(dto);
        }

        return dtoList;
    }

    public BlogArticle toDomain(SaveBlogArticleDto dto) {
        BlogArticle article = new BlogArticle();
        article.setTitle(dto.title());
        article.setSlug(dto.slug());
        article.setShortDescription(dto.shortDescription());
        article.setContent(dto.content());

        return article;
    }
}
