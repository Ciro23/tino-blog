package it.tino.blog.blogarticle;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
class BlogArticleSpringDataSource implements BlogArticleRepository {

    private final ArticleDao articleDao;

    public BlogArticleSpringDataSource(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public BlogArticle save(BlogArticle article) {
        SpringBlogArticle entity = domainToDb(article);
        return dbToDomain(articleDao.save(entity));
    }

    @Override
    public List<BlogArticle> findAll() {
        return dbToDomain(articleDao.findAll());
    }

    @Override
    public List<BlogArticle> findWithLimit(int numberOfArticlesToLoad) {
        PageRequest pageable = PageRequest.of(
            0,
            numberOfArticlesToLoad,
            Sort.by("creationDateTime")
                    .descending()
        );
        Page<SpringBlogArticle> limitedNumberOfArticles = articleDao.findAll(pageable);
        return dbToDomain(limitedNumberOfArticles.getContent());
    }

    @Override
    public Optional<BlogArticle> findById(UUID id) {
        return articleDao.findById(id)
                .map(this::dbToDomain);
    }

    @Override
    public Optional<BlogArticle> findBySlug(String slug) {
        return articleDao.findBySlug(slug)
                .map(this::dbToDomain);
    }

    @Override
    public boolean deleteById(UUID id) {
        if (articleDao.existsById(id)) {
            articleDao.deleteById(id);
            return true;
        }
        return false;
    }

    private SpringBlogArticle domainToDb(BlogArticle article) {
        SpringBlogArticle a = new SpringBlogArticle();
        a.setId(article.getId());
        a.setTitle(article.getTitle());
        a.setSlug(article.getSlug());
        a.setShortDescription(article.getShortDescription());
        a.setContent(article.getContent());
        a.setCreationDateTime(article.getCreationDateTime());
        return a;
    }

    private List<BlogArticle> dbToDomain(Collection<SpringBlogArticle> articles) {
        return articles.stream()
                .map(this::dbToDomain)
                .collect(Collectors.toList());
    }

    private BlogArticle dbToDomain(SpringBlogArticle article) {
        BlogArticle a = new BlogArticle();
        a.setId(article.getId());
        a.setTitle(article.getTitle());
        a.setSlug(article.getSlug());
        a.setShortDescription(article.getShortDescription());
        a.setContent(article.getContent());
        a.setCreationDateTime(article.getCreationDateTime());
        return a;
    }
}
