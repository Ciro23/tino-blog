package it.tino.blog.article;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ArticleSpringDataSource implements ArticleRepository {

    private final ArticleDao articleDao;

    @Override
    public List<Article> findAll() {
        return dbToDomain(articleDao.findAll());
    }

    @Override
    public Optional<Article> findById(UUID id) {
        return articleDao
                .findById(id)
                .map(this::dbToDomain);
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
        a.setContent(article.getContent());
        return a;
    }
}
