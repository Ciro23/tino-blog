package it.tino.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ArticleSpringDataSource implements ArticleRepository {

    private final ArticleDao articleDao;

    @Override
    public Article save(Article article) {
        SpringArticle entity = domainToDb(article);
        return dbToDomain(articleDao.save(entity));
    }

    @Override
    public List<Article> findAll() {
        return dbToDomain(articleDao.findAll());
    }

    @Override
    public List<Article> findWithLimit(int numberOfArticlesToLoad) {
        Page<SpringArticle> limitedNumberOfArticles = articleDao.findAll(PageRequest.of(0, numberOfArticlesToLoad));
        return dbToDomain(limitedNumberOfArticles.getContent());
    }

    @Override
    public Optional<Article> findById(UUID id) {
        return articleDao
                .findById(id)
                .map(this::dbToDomain);
    }

    @Override
    public void deleteById(UUID id) {
        articleDao.deleteById(id);
    }

    private SpringArticle domainToDb(Article article) {
        SpringArticle a = new SpringArticle();
        a.setId(article.getId());
        a.setTitle(article.getTitle());
        a.setShortDescription(article.getShortDescription());
        a.setContent(article.getContent());
        a.setCreationDateTime(article.getCreationDateTime());
        return a;
    }

    private List<Article> dbToDomain(Collection<SpringArticle> articles) {
        return articles.stream()
                .map(this::dbToDomain)
                .collect(Collectors.toList());
    }

    private Article dbToDomain(SpringArticle article) {
        Article a = new Article();
        a.setId(article.getId());
        a.setTitle(article.getTitle());
        a.setShortDescription(article.getShortDescription());
        a.setContent(article.getContent());
        a.setCreationDateTime(article.getCreationDateTime());
        return a;
    }
}
